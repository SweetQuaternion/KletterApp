import type { HalleCreateDTO, HalleResponseDTO, WandResponseDTO } from "../../api/model";
import NeueHalleBox from "./NeueHalleBox";
import "../../styles/Editor.css";
import Canvas from "./Canvas";
import WändeBox from "./WändeBox";
import { useEffect, useState } from "react";
import { useMutation, useQuery } from "@tanstack/react-query";
import {
  createHalleMutationOptions,
  updateHalleMutationOptions,
  updateWandMutationOptions,
} from "../../utils/queries";
import { createWandMutationOptions } from "../../utils/queries";
import { isAdmin } from "../../utils/keycloak";
import { getGetWaendeByHallenIdQueryOptions } from "../../api/wand-controller/wand-controller";

interface Props {
  selectedHalle?: HalleResponseDTO | null;
}

const HalleEditor = ({ selectedHalle }: Props) => {
  const [halleData, setHalleData] = useState<HalleCreateDTO>({
    name: selectedHalle?.name || "",
    adresse: selectedHalle?.adresse || "",
    betreiber: selectedHalle?.betreiber || undefined,
  });

  const { data } = useQuery({
    ...getGetWaendeByHallenIdQueryOptions(selectedHalle?.id ?? -1),
    enabled: !!selectedHalle,
  });

  const [wändeHistory, setWändeHistory] = useState<WandResponseDTO[][]>([[]]);
  const [historyIndex, setHistoryIndex] = useState(0);
  const wände = wändeHistory[historyIndex] || [];
  const [hoveredWand, setHoveredWand] = useState<number | null>(null);
  const [selectedWand, setSelectedWand] = useState<number | null>(null);

  useEffect(() => {
    const loadedWände = data || [];
    setWändeHistory([loadedWände]);
    setHistoryIndex(0);
  }, [data, selectedHalle]);

  function setWände(nextWände: WandResponseDTO[]) {
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

  const {
    mutateAsync: updateHalle,
    isSuccess: isHalleUpdateSuccess,
    isError: isHalleUpdateError,
    isPending: isHalleUpdatePending,
  } = useMutation(updateHalleMutationOptions());

  const {
    mutateAsync: updateWände,
    isSuccess: isWändeUpdateSuccess,
    isError: isWändeUpdateError,
    isPending: isWändeUpdatePending,
  } = useMutation(updateWandMutationOptions());

  async function submit() {
    if (halleData.name.trim() === "" || halleData.adresse.trim() === "") {
      return;
    }
    if (selectedHalle) {
      // Updated bestehende Halle
      await updateHalle({ id: selectedHalle.id, halle: halleData });
      await updateWände({ hallenId: selectedHalle.id, data: wände });
    } else {
      // erstellt neue Halle
      const halle: HalleResponseDTO = await saveHalle(halleData);
      if (halle.id) {
        selectedHalle = halle;
        const updatedWände = wände.map((wand) => ({
          ...wand,
          hallenId: halle.id,
        }));
        setWände(updatedWände);
        saveWände({ hallenId: halle.id, data: updatedWände });
        setWändeHistory([wände]);
        setHistoryIndex(0);
      }
    }
  }

  return (
    <>
      {isAdmin() && (
        <>
          <Canvas
            selectedHalle={selectedHalle}
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
            {isHalleUpdateSuccess && isWändeUpdateSuccess && (
              <p className="small">Halle erfolgreich geupdated!</p>
            )}
            {isHalleError && <p className="small">Fehler beim Speichern der Halle.</p>}
            {isHalleUpdateError && <p className="small">Fehler beim Updaten der Halle.</p>}
            {(isWändeError || isWändeUpdateError) && (
              <p className="small">Halle erstellt. Fehler beim Speichern der Wände.</p>
            )}
            {(halleData.name.trim() === "" || halleData.adresse.trim() === "") && (
              <p className="small">Bitte fülle alle Pflichtfelder aus.</p>
            )}
            <button
              className="red-button"
              onClick={submit}
              disabled={
                isHallePending ||
                isWändePending ||
                isHalleUpdatePending ||
                isWändeUpdatePending ||
                (isHalleSaved && isWändeError)
              }
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
