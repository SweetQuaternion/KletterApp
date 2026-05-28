import "../../styles/App.css";
import "../../styles/Profil.css";
import { useContext, useEffect, useState } from "react";
import { useMutation, useQuery } from "@tanstack/react-query";
import {
  createAddAscentMutationOptions,
  createAvatarMutationOptions,
  createAvatarQueryOptions,
  createUserRoutenStatusMutationOptions,
  createUserSyncMutation,
} from "../../utils/queries.ts";
import type { AscentCreateDTO, UserCreateDTO, UserRoutenStatus } from "../../api/model";
import { pointsToLevel } from "../../utils/levels.ts";
import { UserContext } from "../../utils/context.ts";
import { useOnline } from "../../utils/useOnline.ts";
import getDB from "../../utils/db.ts";
import Stats from "./Stats.tsx";
import { getFindAscentsQueryOptions } from "../../api/ascent-controller/ascent-controller.ts";

const Profil = () => {
  const user = useContext(UserContext);
  const avatarInputId = "avatar-upload-input";
  const [isEditing, setIsEditing] = useState(false);
  const [username, setUsername] = useState(user?.name || "");
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [bio, setBio] = useState(user?.bio || "");
  const isOnline = useOnline();

  const { data: avatar } = useQuery(createAvatarQueryOptions(user?.keycloakId || ""));

  const { data: ascents } = useQuery(
    getFindAscentsQueryOptions({ userId: user?.keycloakId || "" }),
  );

  const { mutateAsync: addAscent } = useMutation(createAddAscentMutationOptions());
  const { mutateAsync: addUserRoutenStatus } = useMutation(createUserRoutenStatusMutationOptions());

  const {
    mutate: syncUser,
    isPending,
    isSuccess,
    isError,
    error,
  } = useMutation(createUserSyncMutation());

  const { mutate: uploadAvatar } = useMutation(createAvatarMutationOptions());

  const handleFileSelect = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0] || null; // Get the first selected file
    setSelectedFile(file);
  };

  const handleSubmit = () => {
    syncUser({
      keycloakId: user?.keycloakId || "",
      name: username,
      bio,
    } as UserCreateDTO);
    if (selectedFile) {
      uploadAvatar(selectedFile);
    }
    setIsEditing(false);
  };

  const getAscentsPending = async () => {
    const db = await getDB();
    return await db.getAll("ascentsPending");
  };

  const getUserRoutenStatusPending = async () => {
    const db = await getDB();
    return await db.getAll("userRoutenStatusPending");
  };

  const [dataPending, setDataPending] = useState(false);

  const isDataPending = async () => {
    const ascentsPending = await getAscentsPending();
    const userRoutenStatusPending = await getUserRoutenStatusPending();
    return (
      (ascentsPending && ascentsPending.length > 0) ||
      (userRoutenStatusPending && userRoutenStatusPending.length > 0)
    );
  };

  useEffect(() => {
    isDataPending().then(setDataPending);
  }, []);

  const handleDataSync = async () => {
    const db = await getDB();
    const ascentsPending = await getAscentsPending();
    for (const pending of ascentsPending) {
      const ascent = pending as AscentCreateDTO;
      ascent.userId = user?.keycloakId || "";
      const ascentResponse = await addAscent(ascent);
      if (ascentResponse) {
        await db.delete("ascentsPending", pending.id);
      }
    }

    const userRoutenStatusPending = await getUserRoutenStatusPending();
    for (const pending of userRoutenStatusPending) {
      const status = pending as UserRoutenStatus;
      status.userId = user?.keycloakId || "";
      const statusResponse = await addUserRoutenStatus(status);
      if (statusResponse) {
        await db.delete("userRoutenStatusPending", `${pending.routenId}`);
      }
    }
    setDataPending(false);
  };

  if (!user) {
    return (
      <div className="profil-container">
        <div className="white-box large">
          <h2>Hey sorry.</h2>
          <p>Du bist nicht angemeldet. Mach das mal!</p>
        </div>
      </div>
    );
  }

  return (
    <>
      <div className="profil-container">
        <section className="white-box large profile">
          <div className="top-section">
            {!isEditing && (
              <div className="flex-row wide-gap">
                <div className="profile-picture-container">
                  {(selectedFile && (
                    <img
                      src={URL.createObjectURL(selectedFile)}
                      className="editable"
                      alt="Profilbild"
                    />
                  )) ||
                    (avatar ? (
                      <img
                        src={URL.createObjectURL(avatar)}
                        className="editable"
                        alt="Profilbild"
                      />
                    ) : (
                      <img
                        src="/images/default-pic.png"
                        className="editable"
                        alt="Standard-Profilbild"
                      />
                    ))}
                </div>
                <div className="flex-column">
                  <h2>{username}</h2>
                  <p className="sans-serif">{bio}</p>
                </div>
              </div>
            )}

            {isEditing && (
              <div className="flex-row wide-gap">
                <div className="profile-picture-container avatar-edit">
                  {(selectedFile && (
                    <img
                      src={URL.createObjectURL(selectedFile)}
                      className="editable"
                      alt="Profilbild"
                    />
                  )) ||
                    (avatar ? (
                      <img
                        src={URL.createObjectURL(avatar)}
                        className="editable"
                        alt="Profilbild"
                      />
                    ) : (
                      <img
                        src="/images/default-pic.png"
                        className="editable"
                        alt="Standard-Profilbild"
                      />
                    ))}
                  <label htmlFor={avatarInputId} className="avatar-overlay-button">
                    Bild auswählen
                  </label>
                  <input
                    id={avatarInputId}
                    className="avatar-file-input"
                    type="file"
                    onChange={handleFileSelect}
                    accept="image/*"
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

            <div className="flex-row small-gap level-container">
              <div className="highlight-feld">
                <div className="mini-dot"></div>
                <p>Level {pointsToLevel(user.punkte || 0)}</p>
              </div>
              <div className="highlight-feld">
                <div className="mini-dot"></div>
                <p>
                  {user.ascentCount || 0} Route
                  {user.ascentCount !== 1 ? "n" : ""} geklettert
                </p>
              </div>
              {isOnline && dataPending && (
                <button className="red-button" onClick={handleDataSync}>
                  Offline-Daten synchronisieren
                </button>
              )}
            </div>

            <Stats ascents={ascents || []} />
          </div>

          <div className="flex-row small-gap bottom-section">
            <button className="red-button" onClick={() => setIsEditing(!isEditing)}>
              {isEditing ? "Abbrechen" : "Profil bearbeiten"}
            </button>
            {isEditing && (
              <button className="red-button" onClick={handleSubmit}>
                Speichern
              </button>
            )}
            {isPending && <p className="status">speichern...</p>}
            {isSuccess && <p className="status success">gespeichert!</p>}
            {isError && <p className="status error">Fehler beim Speichern: {error?.message}</p>}
          </div>
        </section>
      </div>
    </>
  );
};

export default Profil;
