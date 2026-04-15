import "./styles/App.css";
import HallenFinder from "./components/hallensuche/HallenFinder";
import RoutenKarte from "./components/routenkarte/RoutenKarte";
import Profil from "./components/profil/Profil";
import Willkommen from "./components/login/Willkommen";
import { useState } from "react";
import type { HalleResponseDTO, UserResponseDTO } from "./api/model";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Userpage from "./components/profil/Userpage";
import HalleEditor from "./components/hallensuche/neueHalle/HalleEditor";

interface Props {
  user: UserResponseDTO | null;
}

function App({ user }: Props) {
  const [selectedHalle, setSelectedHalle] = useState<HalleResponseDTO | null>(
    () => {
      const savedHalle = localStorage.getItem("Halle");
      return savedHalle ? JSON.parse(savedHalle) : null;
    },
  );

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
        <Route path="/hallenfinder/neu" element={<HalleEditor user={user} />} />
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
        <Route path="/user/:username" element={<Userpage user={user} />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
