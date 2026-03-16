import type { User } from "../../constants/APIResponseTypes";
import "../../styles/App.css";
import "../../styles/Profil.css";
import Header from "../Header";
import defaultpic from "../../assets/default-pic.png";
import { useState } from "react";
import { keycloak } from "../../keycloak.ts";

interface Props {
  user: User | null;
}

const Profil = ({ user }: Props) => {
  const [isEditing, setIsEditing] = useState(false);
  const [username, setUsername] = useState(user?.name || "");
  const [bildUrl, setBildUrl] = useState(user?.bildUrl || "");
  const [bio, setBio] = useState(user?.bio || "");
  const [status, setStatus] = useState(""); // speichern... / gespeichert / Fehler / nichts

  const handleBildUpload = () => {
    setBildUrl("");
  };

  async function handleSave() {
    setStatus("speichern...");
    try {
      const response = await fetch(
        `${keycloak.authServerUrl}/realms/${keycloak.realm}/account`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${keycloak.token}`,
          },
          body: JSON.stringify({
            username: username,
            email: keycloak.tokenParsed?.email,
            firstName: keycloak.tokenParsed?.given_name,
            lastName: keycloak.tokenParsed?.family_name,
          }),
        },
      );
      if (!response.ok) {
        throw new Error(await response.text());
      }
      const userResponse = await fetch("http://localhost:8080/api/users", {
        method: "PATCH",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${keycloak.token}`,
        },
        body: JSON.stringify({
          keycloakId: keycloak.tokenParsed?.sub,
          name: username,
          bildUrl: bildUrl,
          bio: bio,
        }),
      });
      if (!userResponse.ok) {
        throw new Error(await userResponse.text());
      }
      setStatus("gespeichert");
    } catch (e) {
      console.error(e);
      setStatus("Fehler beim Speichern");
    }
  }

  return (
    <>
      <Header user={user} />
      {user && (
        <div className="white-box large profile">
          {!isEditing && (
            <div className="flex-row wide-gap">
              <div className="profile-picture-container">
                <img src={bildUrl || defaultpic} alt="Profilbild" />
              </div>
              <div className="flex-column">
                <h2>{username}</h2>
                <p className="sans-serif">{bio}</p>
              </div>
            </div>
          )}
          {isEditing && (
            <div className="flex-row wide-gap">
              <div className="profile-picture-container">
                <img
                  className="editable"
                  src={bildUrl || defaultpic}
                  alt="Profilbild"
                  onClick={handleBildUpload}
                />
              </div>
              <div className="flex-column">
                <h2
                  contentEditable="plaintext-only"
                  suppressContentEditableWarning
                  onBlur={(e) => setUsername(e.currentTarget.textContent)}
                >
                  {username}
                </h2>
                <p
                  className="sans-serif"
                  contentEditable="plaintext-only"
                  suppressContentEditableWarning
                  onBlur={(e) => setBio(e.currentTarget.textContent)}
                >
                  {bio}
                </p>
              </div>
            </div>
          )}
          <div className="flex-row small-gap">
            <div className="highlight-feld">
              <div className="mini-dot"></div>
              <p>Level 5</p>
            </div>
            <div className="highlight-feld">
              <div className="mini-dot"></div>
              <p>50 Routen geklettert</p>
            </div>
          </div>
          <div className="flex-row small-gap bottom">
            <button onClick={() => setIsEditing(!isEditing)}>
              {isEditing ? "Abbrechen" : "Bearbeiten"}
            </button>
            {isEditing && <button onClick={handleSave}>Speichern</button>}
            {isEditing && status && <p className="status">{status}</p>}
          </div>
        </div>
      )}
      {!user && (
        <div className="white-box large">
          <h2>Hey sorry.</h2>
          <p>Du bist nicht angemeldet. Mach das mal!</p>
        </div>
      )}
    </>
  );
};

export default Profil;
