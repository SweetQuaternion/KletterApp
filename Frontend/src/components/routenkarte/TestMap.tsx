// import { useState } from "react";

function TestMap() {
  // const width = window.innerWidth;
  // const height = window.innerHeight;
  // const [scale, setScale] = useState(1);
  // const [offset, setOffset] = useState({ x: 0, y: 0 });

  // const handleClick = (region: string) => {
  //   alert(`Du hast ${region} geklickt!`);
  // };

  // console.log(
  //   `Width = ${width}, Height = ${height}, Scale = ${scale}, Offset = (${offset.x}, ${offset.y})`,
  // );

  return (
    <svg
      // viewBox={` ${offset.x} ${offset.y} ${width * scale} ${height * scale}`}
      // viewBox={`${-width / (2 * scale) + offset.x} ${-height / (2 * scale) + offset.y} ${width / scale} ${height / scale}`}
      // viewBox={`-600 -500 1200 1000`} // min x, min y, width, height
      xmlns="http://www.w3.org/2000/svg"
      width="1000"
      height="1000"
    >
      <rect x="-500" y="-500" width="10" height="10" fill="tomato" />
      <rect x="490" y="-500" width="10" height="10" fill="green" />
      <rect x="-500" y="490" width="10" height="10" fill="steelblue" />
      <rect x="490" y="490" width="10" height="10" fill="purple" />
    </svg>
  );
}

export default TestMap;
