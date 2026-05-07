import "./styles/App.css";
import RoutenKarte from "./components/routenkarte/RoutenKarte";
import Profil from "./components/profil/Profil";
import Willkommen from "./components/profil/Willkommen";
import { useState } from "react";
import type { HalleResponseDTO } from "./api/model";
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Userpage from "./components/profil/Userpage";
import HalleEditor from "./components/editor/HalleEditor";
import Header from "./components/Header";
import { HalleContext } from "./constants/context";
import HallenSuche from "./components/hallensuche/HallenSuche";

function App() {
  const [selectedHalle, setSelectedHalle] = useState<HalleResponseDTO | null>(() => {
    const savedHalle = sessionStorage.getItem("Halle");
    return savedHalle ? JSON.parse(savedHalle) : null;
  });

  return (
    <HalleContext.Provider value={{ selectedHalle, setSelectedHalle }}>
      <BrowserRouter>
        <a href="#main-content" className="sr-only">
          Zum Hauptinhalt springen
        </a>
        <Header setSelectedHalle={setSelectedHalle} />
        <main id="main-content">
          <Routes>
            <Route path="/" element={<Navigate to="/hallensuche" replace />} />
            <Route path="/hallensuche" element={<HallenSuche />} />
            <Route path="/editor" element={<HalleEditor />} />
            <Route path="/willkommen" element={<Willkommen />} />
            <Route path="/profil" element={<Profil />} />
            <Route
              path="/routenkarte"
              element={selectedHalle ? <RoutenKarte /> : <Navigate to="/hallensuche" replace />}
            />
            <Route
              path="/routenkarte/edit"
              element={<HalleEditor selectedHalle={selectedHalle} />}
            />
            <Route path="/user/:username" element={<Userpage />} />
          </Routes>
        </main>
      </BrowserRouter>
    </HalleContext.Provider>
  );
}

export default App;
