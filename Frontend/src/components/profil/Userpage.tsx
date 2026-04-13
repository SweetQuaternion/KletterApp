import "../../styles/App.css";
import "../../styles/Profil.css";
import Header from "../Header";
import type { UserResponseDTO } from "../../api/model";
import { useParams } from "react-router";
import { useQuery } from "@tanstack/react-query";
import {
  createAvatarQueryOptions,
  createProfileQueryOptions,
} from "../../constants/queries";
import { pointsToLevel } from "../../constants/levels";

interface Props {
  user: UserResponseDTO | null;
}

const Profil = ({ user }: Props) => {
  let params = useParams();

  const { data } = useQuery(createProfileQueryOptions(params.username || ""));

  const { data: avatar } = useQuery(
    createAvatarQueryOptions(data?.keycloakId || ""),
  );

  return (
    <>
      <Header user={user} />
      {user && data && (
        <div className="white-box large profile">
          <div className="flex-row wide-gap">
            <div className="profile-picture-container">
              <img src={avatar ? URL.createObjectURL(avatar) : undefined} />
            </div>
            <div className="flex-column">
              <h2>{params.username}</h2>
              <p className="sans-serif">{data.bio}</p>
            </div>
          </div>
          <div className="flex-row small-gap">
            <div className="highlight-feld">
              <div className="mini-dot"></div>
              <p>Level {pointsToLevel(data.punkte || 0)}</p>
            </div>
            <div className="highlight-feld">
              <div className="mini-dot"></div>
              <p>
                {data.ascentCount || 0} Route{data.ascentCount !== 1 ? "n" : ""}{" "}
                geklettert
              </p>
            </div>
          </div>
        </div>
      )}
      {!data && (
        <div className="white-box large profile">
          <h2>Hey sorry</h2>
          <p>
            Das Profil, das du suchst, existiert nicht. Vielleicht vertippt?
          </p>
        </div>
      )}
      {!user && (
        <div className="white-box large">
          <h2>Hey sorry</h2>
          <p>Du bist nicht angemeldet. Mach das mal!</p>
        </div>
      )}
    </>
  );
};

export default Profil;
