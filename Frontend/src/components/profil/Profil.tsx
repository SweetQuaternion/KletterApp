import "../../styles/App.css";
import "../../styles/Profil.css";
import Header from "../Header";
import defaultpic from "../../assets/default-pic.png";
import { useState } from "react";
import { useMutation, useQuery } from "@tanstack/react-query";
import {
  createAscentQueryOptions,
  createUserSyncMutation,
} from "../../constants/queries.ts";
import type { User } from "../../api/model";
import { pointsToLevel } from "../../constants/levels.ts";

interface Props {
  user: User | null;
}

const Profil = ({ user }: Props) => {
  const [isEditing, setIsEditing] = useState(false);
  const [username, setUsername] = useState(user?.name || "");
  const [bildUrl, setBildUrl] = useState(user?.bildUrl || "");
  const [bio, setBio] = useState(user?.bio || "");

  const { data: ascents } = useQuery(createAscentQueryOptions(null, user));

  const { mutate, isPending, isSuccess, isError } = useMutation(
    createUserSyncMutation(),
  );

  const handleBildUpload = () => {
    setBildUrl("");
  };

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
                  data-placeholder="Hier könnte deine Bio stehen..."
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
              <p>Level {pointsToLevel(ascents || [])}</p>
            </div>
            <div className="highlight-feld">
              <div className="mini-dot"></div>
              <p>
                {ascents?.length || 0} Route{ascents?.length !== 1 ? "n" : ""}{" "}
                geklettert
              </p>
            </div>
          </div>
          <div className="flex-row small-gap bottom">
            <button onClick={() => setIsEditing(!isEditing)}>
              {isEditing ? "Abbrechen" : "Bearbeiten"}
            </button>
            {isEditing && (
              <button
                onClick={() =>
                  mutate({
                    keycloakId: user?.keycloakId || "",
                    name: username,
                    bildUrl,
                    bio,
                  })
                }
              >
                Speichern
              </button>
            )}
            {isPending && <p className="status">speichern...</p>}
            {isSuccess && <p className="status success">gespeichert!</p>}
            {isError && <p className="status error">Fehler beim Speichern</p>}
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
