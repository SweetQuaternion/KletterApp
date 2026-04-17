import { useEffect, useRef, useState } from "react";
import Knopfsis from "../../routenkarte/Knopfsis";
import type { WandCreateDTO } from "../../../api/model";
import TouchTracker from "../../touch/TouchTracker";

interface Props {
  wände: WandCreateDTO[];
  setWände: (wände: WandCreateDTO[]) => void;
  selectedWand: number | null;
}

const Canvas = ({ wände, setWände, selectedWand }: Props) => {
  const [scale, setScale] = useState(1);
  const [offset, setOffset] = useState({ x: 0, y: 0 });
  const svgRef = useRef<SVGSVGElement | null>(null);
  const dragging = useRef(false);
  const dragStart = useRef({ x: 0, y: 0, offsetX: 0, offsetY: 0 });
  const [mousePos, setMousePos] = useState({ x: 0, y: 0 });
  const drawing = useRef(false);
  const [wandStart, setWandStart] = useState({ x: 0, y: 0 });
  const shiftPressed = useRef(false);

  useEffect(() => {
    svgRef.current?.focus();
  }, []);

  function onWheel(e: React.WheelEvent<SVGSVGElement>) {
    setScale(scale + (e.deltaY > 0 ? -0.02 : 0.02));
  }

  function getMousePositionSVG(event: React.MouseEvent<SVGSVGElement>) {
    var point = event.currentTarget.createSVGPoint();
    point.x = event.clientX;
    point.y = event.clientY;
    point = point.matrixTransform(
      event.currentTarget?.getScreenCTM()?.inverse(),
    );
    point.x = (point.x - 1000) / scale - offset.x;
    point.y = (point.y - 1000) / scale - offset.y;

    if (shiftPressed.current) {
      point.x = Math.round(point.x / 25) * 25;
      point.y = Math.round(point.y / 25) * 25;
    }
    return { x: point.x, y: point.y };
  }

  function onMouseMove(e: React.MouseEvent<SVGSVGElement>) {
    const pos = getMousePositionSVG(e);
    setMousePos({ x: pos.x, y: pos.y });
  }

  function onPointerDown(e: React.PointerEvent<SVGSVGElement>) {
    svgRef.current?.focus();
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

  function onClick(e: React.MouseEvent<SVGSVGElement>) {
    if (
      Math.abs(dragStart.current.x - e.clientX) > 5 ||
      Math.abs(dragStart.current.y - e.clientY) > 5
    ) {
      return;
    }
    const pos = getMousePositionSVG(e);
    if (!drawing.current) {
      drawing.current = true;
      setWandStart(pos);
    } else {
      setWände([
        ...wände,
        {
          hallenId: 0,
          // name: `Wand ${wände.length + 1}`,
          startX: Math.round(wandStart.x),
          startY: Math.round(wandStart.y),
          endX: Math.round(pos.x),
          endY: Math.round(pos.y),
          position: "indoor",
        },
      ]);
      drawing.current = false;
    }
  }

  function onKeyDown(e: React.KeyboardEvent<SVGSVGElement>) {
    if (e.key === "Escape") {
      drawing.current = false;
    }
    if (e.key === "Shift") {
      shiftPressed.current = true;
    }
    if (e.ctrlKey && e.key === "z") {
      setWände(wände.slice(0, -1));
    }
  }

  function onKeyUp(e: React.KeyboardEvent<SVGSVGElement>) {
    if (e.key === "Shift") {
      shiftPressed.current = false;
    }
  }

  return (
    <TouchTracker
      onTouchMove={(moveDelta) => {
        offset.x += moveDelta.dx / scale;
        offset.y += moveDelta.dy / scale;
        setOffset({ ...offset });
      }}
      onTouchZoom={(zoomDelta) => {
        let newScale = scale * zoomDelta;
        if (newScale < 0.1) newScale = 0.1;
        if (newScale > 10) newScale = 10;
        setScale(newScale);
      }}
    >
      <div className="canvas-container">
        <Knopfsis scale={scale} setScale={setScale} />
        <svg
          ref={svgRef}
          onClick={onClick}
          onMouseMove={onMouseMove}
          onPointerDown={onPointerDown}
          onPointerMove={onPointerMove}
          onPointerUp={onPointerUp}
          onWheel={onWheel}
          onKeyDown={onKeyDown}
          onKeyUp={onKeyUp}
          width={scale * 2000}
          height={scale * 2000}
          viewBox="0 0 2000 2000"
          preserveAspectRatio="xMidYMid meet"
          style={{ display: "block" }}
          tabIndex={0}
        >
          <g
            transform={`translate(1000,1000) scale(${scale}) translate(${offset.x}, ${offset.y})`}
          >
            <circle cx={mousePos.x} cy={mousePos.y} r="5" fill="var(--mokka)" />
            <text
              x={mousePos.x + 15}
              y={mousePos.y + 10}
              fill="var(--mokka)"
              fontSize={`${15 / scale}px`}
            >
              ({Math.round(mousePos.x)}, {Math.round(mousePos.y)})
            </text>

            <line className="axis" x1="-1000" y1="0" x2="1000" y2="0" />
            <line className="axis" x1="0" y1="-1000" x2="0" y2="1000" />
            {[
              -9, -8, -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9,
            ].map((i) => (
              <g key={`gitter-${i}`}>
                <line
                  key={`horizontal-${i}`}
                  className="gitter"
                  x1="-1000"
                  y1={i * 100}
                  x2="1000"
                  y2={i * 100}
                />
                <line
                  key={`vertical-${i}`}
                  className="gitter"
                  x1={i * 100}
                  y1="-1000"
                  x2={i * 100}
                  y2="1000"
                />
              </g>
            ))}
            {wände.map((wand, index) => (
              <g key={index}>
                <line
                  key={`wand-${index}`}
                  x1={wand.startX}
                  y1={wand.startY}
                  x2={wand.endX}
                  y2={wand.endY}
                  stroke="black"
                  strokeWidth={5}
                  className={selectedWand === index ? "selected" : ""}
                />
                <circle
                  cx={
                    (wand.startX + wand.endX) / 2 -
                    (wand.endY - wand.startY) * 0.1
                  }
                  cy={
                    (wand.startY + wand.endY) / 2 +
                    (wand.endX - wand.startX) * 0.1
                  }
                  r={20}
                  fill={"white"}
                  style={{ cursor: "pointer" }}
                />
                <text
                  x={
                    (wand.startX + wand.endX) / 2 -
                    (wand.endY - wand.startY) * 0.1
                  }
                  y={
                    (wand.startY + wand.endY) / 2 +
                    (wand.endX - wand.startX) * 0.1
                  }
                  fontSize={16}
                  fill="black"
                  textAnchor="middle"
                  dominantBaseline="central"
                >
                  {index + 1}
                </text>
              </g>
            ))}
            {drawing.current && (
              <line
                x1={wandStart.x}
                y1={wandStart.y}
                x2={mousePos.x}
                y2={mousePos.y}
                stroke="black"
                strokeWidth={5}
              />
            )}
          </g>
        </svg>
      </div>
    </TouchTracker>
  );
};

export default Canvas;
