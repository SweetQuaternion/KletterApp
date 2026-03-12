import "./styles/App.css";
import HallenFinder from "./components/hallensuche/HallenFinder";
import RoutenKarte from "./components/routenkarte/RoutenKarte";
import Willkommen from "./components/login/Willkommen";
import { useState } from "react";
import type { Halle, User } from "./constants/APIResponseTypes";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

interface Props {
  user: User | null;
}

function App({ user }: Props) {
  const [selectedHalle, setSelectedHalle] = useState<Halle | null>(null);

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/hallenfinder" replace />} />
        <Route
          path="/hallenfinder"
          element={
            <HallenFinder user={user} setSelectedHalle={setSelectedHalle} />
          }
        />
        <Route path="/willkommen" element={<Willkommen user={user} />} />
        <Route
          path="/routenkarte"
          element={
            selectedHalle ? (
              <RoutenKarte selectedHalle={selectedHalle} user={user} />
            ) : null
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
