import { useQuery } from "@tanstack/react-query";
import "../../styles/Home.css";
import { getGetFlachwitzOfTheDayQueryOptions } from "../../api/flachwitz-controller/flachwitz-controller";
import type { HalleResponseDTO } from "../../api/model";
import AddNews from "./NeueNews";
import { isAdmin } from "../../utils/keycloak";
import AllgemeineNews from "./AllgemeineNews";
import HalleNews from "./HalleNews";

const Home = () => {
  const { data: witz } = useQuery(getGetFlachwitzOfTheDayQueryOptions());
  const heimathalle = JSON.parse(
    localStorage.getItem("Heimathalle") || "null",
  ) as HalleResponseDTO | null;

  return (
    <div className="home-container">
      <div className="home">
        <h2 className="home-title">Schön, dass du da bist!</h2>
        <div className="home-content">
          <AllgemeineNews />

          <HalleNews halle={heimathalle} />

          <div className="white-box feed">
            {heimathalle ? (
              <>
                <h3>Routenkarte: {heimathalle.name}</h3>
                <p>Hier gehts direkt zur Routenkarte deiner Heimathalle.</p>
              </>
            ) : (
              <>
                <h3>Routenkarte deiner Heimathalle</h3>
                <p>Du hast noch keine Heimathalle markiert.</p>
              </>
            )}
          </div>

          <div className="white-box feed">
            <h3>Mein Profil</h3>
            <p>Melde dich an oder so</p>
          </div>

          <div className="white-box feed">
            <h3>Meine Achievements</h3>
            <p>Du hast noch keine Achievements erreicht.</p>
          </div>

          <div className="white-box feed">
            <h3>Unser täglicher KI-generierter Flachwitz-Content</h3>
            <p>{witz?.setup}</p>
            <p>{witz?.punchline}</p>
          </div>
        </div>
      </div>
      {isAdmin() && <AddNews />}
    </div>
  );
};

export default Home;
