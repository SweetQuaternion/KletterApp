import type { HalleCreateDTO, HalleResponseDTO, WandCreateDTO } from "../../api/model";
import NeueHalleBox from "./NeueHalleBox";
import "../../styles/Editor.css";
import Canvas from "./Canvas";
import WändeBox from "./WändeBox";
import { useEffect, useState } from "react";
import { useMutation, useQuery } from "@tanstack/react-query";
import { createHalleMutationOptions } from "../../constants/queries";
import { createWandMutationOptions } from "../../constants/queries";
import { isAdmin } from "../../constants/keycloak";
import { getGetWaendeByHallenIdQueryOptions } from "../../api/wand-controller/wand-controller";
import { updateHalle } from "../../api/hallen-controller/hallen-controller";

interface Props {
  selectedHalle?: HalleResponseDTO | null;
}

const HalleEditor = ({ selectedHalle }: Props) => {
  const [halleData, setHalleData] = useState<HalleCreateDTO>({
    name: "",
    adresse: "",
    betreiber: undefined,
  });

  const { data } = useQuery({
    ...getGetWaendeByHallenIdQueryOptions(selectedHalle?.id ?? -1),
    enabled: !!selectedHalle,
  });

  const [wändeHistory, setWändeHistory] = useState<WandCreateDTO[][]>([[]]);
  const [historyIndex, setHistoryIndex] = useState(0);
  const wände = wändeHistory[historyIndex] || [];
  const [hoveredWand, setHoveredWand] = useState<number | null>(null);
  const [selectedWand, setSelectedWand] = useState<number | null>(null);

  useEffect(() => {
    const loadedWände = data || [];
    setWändeHistory([loadedWände]);
    setHistoryIndex(0);
  }, [data, selectedHalle]);

  function setWände(nextWände: WandCreateDTO[]) {
    setWändeHistory((currentHistory) => {
      const truncatedHistory = currentHistory.slice(0, historyIndex + 1);
      return [...truncatedHistory, nextWände];
    });
    setHistoryIndex((currentIndex) => currentIndex + 1);
  }

  function undo() {
    setHistoryIndex((currentIndex) => Math.max(0, currentIndex - 1));
  }

  function redo() {
    setHistoryIndex((currentIndex) => Math.min(wändeHistory.length - 1, currentIndex + 1));
  }

  const {
    mutateAsync: saveHalle,
    isSuccess: isHalleSaved,
    isError: isHalleError,
    isPending: isHallePending,
  } = useMutation(createHalleMutationOptions());

  const {
    mutateAsync: saveWände,
    isSuccess: isWändeSaved,
    isError: isWändeError,
    isPending: isWändePending,
  } = useMutation(createWandMutationOptions());

  async function submit() {
    if (selectedHalle) {
      await updateHalle(halleData, { id: selectedHalle.id });
      // Hier noch die Wände updaten
      // await saveWände({ hallenId: selectedHalle.id, data: wände });
    } else {
      const halle: HalleResponseDTO = await saveHalle(halleData);
      if (halle.id) {
        selectedHalle = halle;
        const updatedWände = wände.map((wand) => ({
          ...wand,
          hallenId: halle.id,
        }));
        setWände(updatedWände);
        saveWände({ hallenId: halle.id, data: updatedWände });
      }
    }
  }

  return (
    <>
      {isAdmin() && (
        <>
          <Canvas
            wände={wände}
            setWände={setWände}
            selectedWand={selectedWand}
            setSelectedWand={setSelectedWand}
            hoveredWand={hoveredWand}
            setHoveredWand={setHoveredWand}
            undo={undo}
            redo={redo}
          />
          <div className="halle-details"></div>
          <div className="erklärung top right">
            <p>Klicke um eine Wand zu zeichnen</p>
            <p>Drücke ESC zum Abbrechen</p>
            <p>Drücke Shift zum Einrasten an Gitterlinien</p>
          </div>
          <div className="details-container">
            <NeueHalleBox selectedHalle={selectedHalle} setHalleData={setHalleData} />
            <WändeBox
              wände={wände}
              setWände={setWände}
              selectedWand={selectedWand}
              setSelectedWand={setSelectedWand}
              setHoveredWand={setHoveredWand}
              undo={undo}
              redo={redo}
            />
          </div>
          <div className="speichern-knopfsi">
            {isHalleSaved && isWändeSaved && (
              <p className="small">Halle erfolgreich gespeichert!</p>
            )}
            {isHalleError && <p className="small">Fehler beim Speichern der Halle.</p>}
            {isWändeError && (
              <p className="small">Halle erstellt. Fehler beim Speichern der Wände.</p>
            )}
            <button
              onClick={submit}
              disabled={isHallePending || isWändePending || (isHalleSaved && isWändeError)}
            >
              Halle speichern
            </button>
          </div>
        </>
      )}
      {!isAdmin() && (
        <div className="profil-container">
          <div className="white-box large">
            <h2>Hey sorry.</h2>
            <p>Du bist kein Admin. Du hast hier keinen Zugriff.</p>
          </div>
        </div>
      )}
    </>
  );
};

export default HalleEditor;
