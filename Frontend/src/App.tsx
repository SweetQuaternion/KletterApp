import "./styles/App.css";
import HallenFinder from "./components/HallenFinder";
import RoutenKarte from "./components/RoutenKarte";
import { useState } from "react";
import type { Halle } from "./constants/APIResponseTypes";

function App() {
  const [selectedHalle, setSelectedHalle] = useState<Halle | null>(null);
  console.log("Selected Halle ID:", selectedHalle);

  return (
    <>
      {!selectedHalle && <HallenFinder setSelectedHalle={setSelectedHalle} />}
      {selectedHalle && <RoutenKarte selectedHalle={selectedHalle} />}
    </>
  );
}

export default App;
