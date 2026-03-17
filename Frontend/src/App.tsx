import "./styles/App.css";
import HallenFinder from "./components/hallensuche/HallenFinder";
import RoutenKarte from "./components/routenkarte/RoutenKarte";
import Profil from "./components/profil/Profil";
import Willkommen from "./components/login/Willkommen";
import { useEffect, useState } from "react";
import type { Halle, User } from "./constants/APIResponseTypes";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";

interface Props {
  user: User | null;
}

function App({ user }: Props) {
  const [selectedHalle, setSelectedHalle] = useState<Halle | null>(() => {
    const savedHalle = localStorage.getItem("Halle");
    return savedHalle ? JSON.parse(savedHalle) : null;
  });

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
        <Route path="/profil" element={<Profil user={user} />} />
        <Route
          path="/routenkarte"
          element={
            selectedHalle ? (
              <RoutenKarte selectedHalle={selectedHalle} user={user} />
            ) : (
              <Navigate to="/hallenfinder" replace />
            )
          }
        />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
