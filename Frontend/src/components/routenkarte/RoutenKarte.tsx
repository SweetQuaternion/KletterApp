import type {
  Halle,
  Route,
  User,
  Wand,
  WandParsed,
} from "../../constants/APIResponseTypes";
import Header from "./../Header";
import "../../styles/RoutenKarte.css";
import HallenInfoBox from "./HallenInfoBox";
import SVGMap from "./SVGMap";
import Knopfsis from "./Knopfsis";
import { useEffect, useState } from "react";
import WandInfoBox from "./WandInfoBox";

interface Props {
  selectedHalle: Halle;
  user: User | null;
}

const RoutenKarte = ({ selectedHalle, user }: Props) => {
  const [scale, setScale] = useState(1);
  const [wände, setWände] = useState<Wand[]>([]);
  const [wändeParsed, setWändeParsed] = useState<WandParsed[]>([]);
  const [routen, setRouten] = useState<Route[]>([]);
  const [selectedWand, setSelectedWand] = useState<number | null>(null);

  async function getWände() {
    const response = await fetch(
      "http://localhost:8080/api/waende?hallenId=" + selectedHalle.id,
    );
    const data = (await response.json()) as Wand[];
    if (!response.ok) {
      console.error("Fehler beim Laden der Wände:", response.statusText);
      return;
    }
    console.log("Fetched wände:", data);
    setWände(data);
  }

  async function getRouten() {
    const response = await fetch(
      "http://localhost:8080/api/routen?hallenId=" + selectedHalle.id,
    );
    const data = (await response.json()) as Route[];
    if (!response.ok) {
      console.error("Fehler beim Laden der Routen:", response.statusText);
      return;
    }
    console.log("Fetched routes:", data);
    setRouten(data);
  }

  function parseWände() {
    setWändeParsed(
      wände.map((wand) => {
        return {
          wandNr: wand.wandNr,
          startX: wand.startX,
          startY: wand.startY,
          endX: wand.endX,
          endY: wand.endY,
          centerX: (wand.startX + wand.endX) / 2,
          centerY: (wand.startY + wand.endY) / 2,
          offsetX: (wand.endY - wand.startY) * 0.1,
          offsetY: -(wand.endX - wand.startX) * 0.1,
          routen: routen.filter((route) => route.wandNr === wand.wandNr),
        } as WandParsed;
      }),
    );
    console.log("Parsed wände:", wändeParsed);
  }

  useEffect(() => {
    getWände();
    getRouten();
  }, []);

  useEffect(() => {
    if (wände.length > 0 && routen.length > 0) {
      parseWände();
    }
  }, [wände, routen]);

  return (
    <>
      <SVGMap
        scale={scale}
        setScale={setScale}
        wände={wändeParsed}
        routen={routen}
        setSelectedWand={setSelectedWand}
      />
      <Header user={user} />
      <HallenInfoBox selectedHalle={selectedHalle} />
      <Knopfsis scale={scale} setScale={setScale} />
      {selectedWand && (
        <WandInfoBox
          selectedWand={
            wände.find((wand) => wand.wandNr === selectedWand) || null
          }
          routen={routen.filter((route) => route.wandNr === selectedWand)}
        />
      )}
    </>
  );
};

export default RoutenKarte;
