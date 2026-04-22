import "./styles/App.css";
import HallenFinder from "./components/hallensuche/HallenFinder";
import RoutenKarte from "./components/routenkarte/RoutenKarte";
import Profil from "./components/profil/Profil";
import Willkommen from "./components/profil/Willkommen";
import { useState } from "react";
import type { HalleResponseDTO, UserResponseDTO } from "./api/model";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Userpage from "./components/profil/Userpage";
import HalleEditor from "./components/editor/HalleEditor";
import Header from "./components/Header";

interface Props {
  user: UserResponseDTO | null;
}

function App({ user }: Props) {
  const [selectedHalle, setSelectedHalle] = useState<HalleResponseDTO | null>(
    () => {
      const savedHalle = sessionStorage.getItem("Halle");
      return savedHalle ? JSON.parse(savedHalle) : null;
    },
  );

  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Navigate to="/hallenfinder" replace />} />
        <Route
          path="/hallenfinder"
          element={<HallenFinder setSelectedHalle={setSelectedHalle} />}
        />
        <Route path="/hallenfinder/neu" element={<HalleEditor user={user} />} />
        <Route path="/willkommen" element={<Willkommen />} />
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
        <Route
          path="/routenkarte/edit"
          element={<HalleEditor user={user} selectedHalle={selectedHalle} />}
        />
        <Route path="/user/:username" element={<Userpage user={user} />} />
      </Routes>
      <Header user={user} setSelectedHalle={setSelectedHalle} />
    </BrowserRouter>
  );
}

export default App;
