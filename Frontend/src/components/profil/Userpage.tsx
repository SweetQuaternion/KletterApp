import "../../styles/App.css";
import "../../styles/Profil.css";
import { useParams } from "react-router";
import { useQuery } from "@tanstack/react-query";
import { createAvatarQueryOptions, createProfileQueryOptions } from "../../constants/queries";
import { pointsToLevel } from "../../constants/levels";
import { UserContext } from "../../constants/context.ts";
import { useContext } from "react";

const Profil = () => {
  let params = useParams();
  const user = useContext(UserContext);
  const { data } = useQuery(createProfileQueryOptions(params.username || ""));

  const { data: avatar } = useQuery(createAvatarQueryOptions(data?.keycloakId || ""));

  return (
    <>
      <div className="profil-container">
        {user && data && (
          <section className="white-box large profile">
            <div className="flex-row wide-gap">
              <div className="profile-picture-container">
                <img
                  src={avatar ? URL.createObjectURL(avatar) : "/default-pic.png"}
                  alt="Profilbild"
                />
              </div>
              <div className="flex-column">
                <h2>{params.username}</h2>
                <p className="sans-serif">{data.bio}</p>
              </div>
            </div>
            <div className="flex-row small-gap level-container">
              <div className="highlight-feld">
                <div className="mini-dot"></div>
                <p>Level {pointsToLevel(data.punkte || 0)}</p>
              </div>
              <div className="highlight-feld">
                <div className="mini-dot"></div>
                <p>
                  {data.ascentCount || 0} Route
                  {data.ascentCount !== 1 ? "n" : ""} geklettert
                </p>
              </div>
            </div>
          </section>
        )}
        {!data && (
          <div className="white-box large">
            <h2>Hey sorry</h2>
            <p>Das Profil, das du suchst, existiert nicht. Vielleicht vertippt?</p>
          </div>
        )}
        {!user && (
          <section className="white-box large ">
            <h2>Hey sorry</h2>
            <p>Du bist nicht angemeldet. Mach das mal!</p>
          </section>
        )}
      </div>
    </>
  );
};

export default Profil;
