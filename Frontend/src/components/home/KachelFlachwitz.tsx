import { useQuery } from "@tanstack/react-query";
import { getGetFlachwitzOfTheDayQueryOptions } from "../../api/flachwitz-controller/flachwitz-controller";

function KachelFlachwitz() {
  const { data: witz } = useQuery(getGetFlachwitzOfTheDayQueryOptions());

  return (
    <div className="white-box feed">
      <h3>Unser täglicher KI-generierter Flachwitz-Content</h3>
      <p>{witz?.setup}</p>
      <p>{witz?.punchline}</p>
      <img
        className="kachel-img"
        src="/images/flachwitze-kachel.webp"
        alt="Img: Lachender Klettergriff"
      ></img>
    </div>
  );
}

export default KachelFlachwitz;
