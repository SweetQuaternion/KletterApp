import type {
  HalleCreateDTO,
  HalleResponseDTO,
  UserResponseDTO,
  WandCreateDTO,
} from "../../../api/model";
import Header from "../../Header";
import NeueHalleBox from "./NeueHalleBox";
import "../../../styles/NeueHalle.css";
import Canvas from "./Canvas";
import WändeBox from "./WändeBox";
import { useState } from "react";
import { useMutation } from "@tanstack/react-query";
import { createHalleMutationOptions } from "../../../constants/queries";
import { createWandMutationOptions } from "../../../constants/queries";

interface Props {
  user: UserResponseDTO | null;
}

const HalleEditor = ({ user }: Props) => {
  const [halleData, setHalleData] = useState<HalleCreateDTO>({
    name: "",
    adresse: "",
    betreiber: undefined,
  });
  const [wände, setWände] = useState<WandCreateDTO[]>([]);
  const [selectedWand, setSelectedWand] = useState<number | null>(null);

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
    const halle: HalleResponseDTO = await saveHalle(halleData);
    if (halle.id) {
      setWände((currentWände) =>
        currentWände.map((wand) => ({ ...wand, hallenId: halle.id })),
      );
      saveWände({ hallenId: halle.id, data: wände });
    }
  }

  return (
    <>
      <Canvas wände={wände} setWände={setWände} selectedWand={selectedWand} />
      <Header user={user} />
      <div className="halle-details"></div>
      <div className="erklärung top right">
        <p>Klicke um eine Wand zu zeichnen</p>
        <p>Drücke ESC zum Abbrechen</p>
        <p>Drücke Shift zum Einrasten an Gitterlinien</p>
      </div>
      <div className="details-container">
        <NeueHalleBox setHalleData={setHalleData} />
        <WändeBox
          wände={wände}
          setWände={setWände}
          setSelectedWand={setSelectedWand}
        />
      </div>
      <div className="speichern-knopfsi">
        {isHalleSaved && isWändeSaved && (
          <p className="small">Halle erfolgreich gespeichert!</p>
        )}
        {isHalleError && (
          <p className="small">Fehler beim Speichern der Halle.</p>
        )}
        {isWändeError && (
          <p className="small">
            Halle erstellt. Fehler beim Speichern der Wände.
          </p>
        )}
        <button
          onClick={submit}
          disabled={
            isHallePending || isWändePending || (isHalleSaved && isWändeError)
          }
        >
          Halle speichern
        </button>
      </div>
    </>
  );
};

export default HalleEditor;
