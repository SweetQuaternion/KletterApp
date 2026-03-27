import "../../styles/App.css";
import "../../styles/Profil.css";
import Header from "../Header";
import defaultpic from "../../assets/default-pic.png";
import type { User } from "../../api/model";
import { useParams } from "react-router";
import { useQuery } from "@tanstack/react-query";
import { createProfileQueryOptions } from "../../constants/queries";

interface Props {
  user: User | null;
}

const Profil = ({ user }: Props) => {
  let params = useParams();

  const { data } = useQuery(createProfileQueryOptions(params.username || ""));

  return (
    <>
      <Header user={user} />
      {user && data && (
        <div className="white-box large profile">
          <div className="flex-row wide-gap">
            <div className="profile-picture-container">
              <img src={data.bildUrl || defaultpic} />
            </div>
            <div className="flex-column">
              <h2>{params.username}</h2>
              <p className="sans-serif">{data.bio}</p>
            </div>
          </div>
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
