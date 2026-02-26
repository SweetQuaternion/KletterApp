import { useState } from "react";

function TestMap() {
  const width = window.innerWidth;
  const height = window.innerHeight;
  const [scale, setScale] = useState(1);
  const [offset, setOffset] = useState({ x: 0, y: 0 });

  const handleClick = (region: string) => {
    alert(`Du hast ${region} geklickt!`);
  };

  return (
    <svg
      viewBox={` ${offset.x} ${offset.y} ${width * scale} ${height * scale}`}
      xmlns="http://www.w3.org/2000/svg"
      width="1000"
      height="1000"
    >
      <rect
        x="100"
        y="100"
        width="80"
        height="80"
        fill="steelblue"
        style={{ cursor: "pointer" }}
        onClick={() => handleClick("Rechteck")}
      />
      <circle
        cx="150"
        cy="50"
        r="40"
        fill="tomato"
        style={{ cursor: "pointer" }}
        onClick={() => handleClick("Kreis")}
      />
    </svg>
  );
}

export default TestMap;
