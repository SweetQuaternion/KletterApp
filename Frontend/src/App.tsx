import "./styles/App.css";
import HallenFinder from "./components/hallensuche/HallenFinder";
import RoutenKarte from "./components/routenkarte/RoutenKarte";
import Signup from "./components/login/Signup";
import { useState } from "react";
import type { Halle, User } from "./constants/APIResponseTypes";

import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import Login from "./components/login/Login";
import Willkommen from "./components/login/Willkommen";

function App() {
  const [user, setUser] = useState<User | null>(null);
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
        <Route
          path="/signup"
          element={<Signup user={user} setUser={setUser} />}
        />
        <Route
          path="/login"
          element={<Login user={user} setUser={setUser} />}
        />
        <Route
          path="/willkommen"
          element={<Willkommen user={user} setUser={setUser} />}
        />
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
