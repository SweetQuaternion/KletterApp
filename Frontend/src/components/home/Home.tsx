import "../../styles/Home.css";
import type { HalleResponseDTO } from "../../api/model";
import AddNews from "./NeueNews";
import { isAdmin } from "../../utils/keycloak";
import KachelAllgemeineNews from "./KachelAllgemeineNews";
import KachelHalleNews from "./KachelHalleNews";
import KachelRoutenkarte from "./KachelRoutenkarte";
import KachelProfil from "./KachelProfil";
import KachelAchievements from "./KachelAchievements";
import KachelFlachwitz from "./KachelFlachwitz";

const Home = () => {
  const heimathalle = JSON.parse(
    localStorage.getItem("Heimathalle") || "null",
  ) as HalleResponseDTO | null;

  return (
    <div className="home-container">
      <div className="home">
        <h2 className="home-title">Schön, dass du da bist!</h2>
        <div className="home-content">
          <KachelAllgemeineNews />

          <KachelHalleNews heimathalle={heimathalle} />

          <KachelRoutenkarte heimathalle={heimathalle} />

          <KachelProfil />

          <KachelAchievements />

          <KachelFlachwitz />
        </div>
      </div>
      {isAdmin() && <AddNews />}
    </div>
  );
};

export default Home;
