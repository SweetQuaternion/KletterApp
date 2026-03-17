import { useRef, useState } from "react";

interface Props {
  scale: number;
  setScale: (scale: number) => void;
}

function TestMap({ scale, setScale }: Props) {
  const [offset, setOffset] = useState({ x: 0, y: 0 });
  const dragging = useRef(false);
  const dragStart = useRef({ x: 0, y: 0, offsetX: 0, offsetY: 0 });

  function onPointerDown(e: React.PointerEvent<SVGSVGElement>) {
    dragging.current = true;
    dragStart.current = {
      x: e.clientX,
      y: e.clientY,
      offsetX: offset.x,
      offsetY: offset.y,
    };
  }

  function onPointerMove(e: React.PointerEvent<SVGSVGElement>) {
    if (!dragging.current) return;
    const dx = e.clientX - dragStart.current.x;
    const dy = e.clientY - dragStart.current.y;
    setOffset({
      x: dragStart.current.offsetX + dx / scale,
      y: dragStart.current.offsetY + dy / scale,
    });
  }

  function onPointerUp() {
    dragging.current = false;
  }

  function onWheel(e: React.WheelEvent<SVGSVGElement>) {
    setScale(scale + (e.deltaY > 0 ? -0.02 : 0.02));
  }

  return (
    <div className="routenkarte-container">
      <svg
        onPointerDown={onPointerDown}
        onPointerMove={onPointerMove}
        onPointerUp={onPointerUp}
        onWheel={onWheel}
        className="routenkarte"
        xmlns="http://www.w3.org/2000/svg"
        width="100%"
        height="100%"
      >
        <g
          transform={`
            translate(${500 + offset.x * scale}, ${500 + offset.y * scale}) 
            scale(${scale}) 
            translate(-500, -500)
          `}
        >
          {/* <line x1="10" y1="10" x2="190" y2="190" /> */}
          <rect x="-500" y="-500" width="50" height="50" fill="tomato" />
          <rect x="450" y="-500" width="50" height="50" fill="green" />
          <rect x="-500" y="450" width="50" height="50" fill="steelblue" />
          <rect x="450" y="450" width="50" height="50" fill="purple" />
        </g>
      </svg>
    </div>
  );
}

export default TestMap;
