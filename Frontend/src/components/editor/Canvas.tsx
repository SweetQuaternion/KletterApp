import { useEffect, useRef, useState } from "react";
import * as d3 from "d3";
import Knopfsis from "../routenkarte/Knopfsis";
import type { HalleResponseDTO, WandResponseDTO } from "../../api/model";

interface Props {
  selectedHalle?: HalleResponseDTO | null;
  wände: WandResponseDTO[];
  setWände: (wände: WandResponseDTO[]) => void;
  selectedWand: number | null;
  setSelectedWand: (index: number | null) => void;
  hoveredWand: number | null;
  setHoveredWand: (index: number | null) => void;
  undo: () => void;
  redo: () => void;
}

const Canvas = ({
  selectedHalle,
  wände,
  setWände,
  selectedWand,
  setSelectedWand,
  hoveredWand,
  setHoveredWand,
  undo,
  redo,
}: Props) => {
  const svgRef = useRef<SVGSVGElement | null>(null);
  const gRef = useRef<SVGGElement | null>(null);
  const zoomRef = useRef<d3.ZoomBehavior<SVGSVGElement, unknown> | null>(null);
  const [transform, setTransform] = useState(d3.zoomIdentity);

  const dragging = useRef(false);
  const dragStart = useRef({ x: 0, y: 0 });
  const [mousePos, setMousePos] = useState({ x: 0, y: 0 });
  const drawing = useRef(false);
  const editing = useRef({ isEditing: false, index: null, part: "" } as {
    isEditing: boolean;
    index: number | null;
    part: string;
  });
  const [wandStart, setWandStart] = useState({ x: 0, y: 0 });
  const shiftPressed = useRef(false);
  const selectedWandData = selectedWand !== null ? wände[selectedWand] : null;
  const editingWand = editing.current.index !== null ? wände[editing.current.index] : null;

  useEffect(() => {
    svgRef.current?.focus();
  }, []);

  // d3-zoom initialisieren
  useEffect(() => {
    const svgEl = svgRef.current;
    const gEl = gRef.current;
    if (!svgEl || !gEl) return;

    const zoom = d3
      .zoom<SVGSVGElement, unknown>()
      .scaleExtent([0.1, 10])
      .on("zoom", (event: d3.D3ZoomEvent<SVGSVGElement, unknown>) => {
        const t = event.transform;
        d3.select(gEl).attr("transform", `translate(${t.x}, ${t.y}) scale(${t.k})`);
        setTransform(t);
      });

    zoomRef.current = zoom;
    d3.select(svgEl).call(zoom);

    const { width, height } = svgEl.getBoundingClientRect();
    d3.select(svgEl).call(zoom.transform, d3.zoomIdentity.translate(width / 2, height / 2));

    return () => {
      d3.select(svgEl).on(".zoom", null);
    };
  }, []);

  // Scale-Knopf von außen
  useEffect(() => {
    const svgEl = svgRef.current;
    const zoom = zoomRef.current;
    if (!svgEl || !zoom) return;

    const current = d3.zoomTransform(svgEl);
    if (Math.abs(current.k - transform.k) < 0.001) return;

    d3.select(svgEl).call(zoom.scaleTo, transform.k);
  }, [transform.k]);

  function getMousePositionSVG(event: React.MouseEvent<SVGSVGElement>) {
    const svgEl = svgRef.current;
    if (!svgEl) return { x: 0, y: 0 };

    const rect = svgEl.getBoundingClientRect();
    const screenX = event.clientX - rect.left;
    const screenY = event.clientY - rect.top;

    // d3-transform invertieren: Screen → SVG-Koordinaten
    const [x, y] = transform.invert([screenX, screenY]);

    if (shiftPressed.current) {
      return {
        x: Math.round(x / 25) * 25,
        y: Math.round(y / 25) * 25,
      };
    }
    return { x, y };
  }

  function onMouseMove(e: React.MouseEvent<SVGSVGElement>) {
    const pos = getMousePositionSVG(e);
    setMousePos({ x: pos.x, y: pos.y });
  }

  function onPointerDown(e: React.PointerEvent<SVGSVGElement>) {
    svgRef.current?.focus();
    dragging.current = false;
    dragStart.current = { x: e.clientX, y: e.clientY };
  }

  function onPointerMove(e: React.PointerEvent<SVGSVGElement>) {
    const dx = e.clientX - dragStart.current.x;
    const dy = e.clientY - dragStart.current.y;
    if (Math.abs(dx) > 5 || Math.abs(dy) > 5) {
      dragging.current = true;
    }
  }

  function onPointerUp() {
    // dragging.current wird in onClick ausgelesen
  }

  function onClick(e: React.MouseEvent<SVGSVGElement>) {
    if (dragging.current) {
      dragging.current = false;
      return;
    }
    if (selectedWand !== null) {
      setSelectedWand(null);
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
          id: -1,
          hallenId: selectedHalle ? selectedHalle.id : -1,
          wandNr: Math.max(...wände.map((w) => w.wandNr), 0) + 1,
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
      setSelectedWand(null);
      drawing.current = false;
      editing.current = { isEditing: false, index: null, part: "" };
    }
    if (e.key === "Shift") {
      shiftPressed.current = true;
    }
    if (e.ctrlKey && e.key === "z") {
      e.preventDefault();
      undo();
    }
    if (e.ctrlKey && e.key === "y") {
      e.preventDefault();
      redo();
    }
  }

  function onKeyUp(e: React.KeyboardEvent<SVGSVGElement>) {
    if (e.key === "Shift") {
      shiftPressed.current = false;
    }
  }

  function applyWandEdit(part: "start" | "end") {
    if (selectedWand === null) return;

    const updatedWände = [...wände];
    const currentWand = updatedWände[selectedWand];
    if (!currentWand) return;

    if (part === "start") {
      currentWand.startX = Math.round(mousePos.x);
      currentWand.startY = Math.round(mousePos.y);
    } else {
      currentWand.endX = Math.round(mousePos.x);
      currentWand.endY = Math.round(mousePos.y);
    }

    setWände(updatedWände);
  }

  return (
    <div className="canvas-container">
      <Knopfsis
        scale={transform.k}
        setScale={(k) => {
          const svgEl = svgRef.current;
          const zoom = zoomRef.current;
          if (!svgEl || !zoom) return;
          d3.select(svgEl).call(zoom.scaleTo, k);
        }}
      />
      <svg
        ref={svgRef}
        onClick={onClick}
        onMouseMove={onMouseMove}
        onPointerDown={onPointerDown}
        onPointerMove={onPointerMove}
        onPointerUp={onPointerUp}
        onKeyDown={onKeyDown}
        onKeyUp={onKeyUp}
        width="100%"
        height="100%"
        style={{ display: "block" }}
        tabIndex={0}
      >
        <g ref={gRef}>
          <circle cx={mousePos.x} cy={mousePos.y} r="5" fill="var(--mokka)" />
          <text
            x={mousePos.x + 15}
            y={mousePos.y + 10}
            fill="var(--mokka)"
            fontSize={`${15 / transform.k}px`}
          >
            ({Math.round(mousePos.x)}, {Math.round(mousePos.y)})
          </text>

          <line className="axis" x1="-1000" y1="0" x2="1000" y2="0" />
          <line className="axis" x1="0" y1="-1000" x2="0" y2="1000" />
          {[-9, -8, -7, -6, -5, -4, -3, -2, -1, 1, 2, 3, 4, 5, 6, 7, 8, 9].map((i) => (
            <g key={`gitter-${i}`}>
              <line className="gitter" x1="-1000" y1={i * 100} x2="1000" y2={i * 100} />
              <line className="gitter" x1={i * 100} y1="-1000" x2={i * 100} y2="1000" />
            </g>
          ))}

          {wände.map((wand, index) => (
            <g
              key={index}
              onClick={(e) => {
                e.stopPropagation();
                setSelectedWand(index);
              }}
              onMouseOver={() => setHoveredWand(index)}
              onMouseOut={() => setHoveredWand(null)}
              style={{
                cursor: "pointer",
                visibility:
                  editing.current.isEditing && editing.current.index === index
                    ? "hidden"
                    : "visible",
              }}
            >
              {wand.wandNr !== -1 && (
                <g>
                  <line
                    x1={wand.startX}
                    y1={wand.startY}
                    x2={wand.endX}
                    y2={wand.endY}
                    strokeWidth={5}
                    className={hoveredWand === index || selectedWand === index ? "selected" : ""}
                  />
                  <circle
                    cx={(wand.startX + wand.endX) / 2 - (wand.endY - wand.startY) * 0.1}
                    cy={(wand.startY + wand.endY) / 2 + (wand.endX - wand.startX) * 0.1}
                    r={20}
                    fill={"white"}
                    style={{ cursor: "pointer" }}
                  />
                  <text
                    x={(wand.startX + wand.endX) / 2 - (wand.endY - wand.startY) * 0.1}
                    y={(wand.startY + wand.endY) / 2 + (wand.endX - wand.startX) * 0.1}
                    fontSize={16}
                    fill="black"
                    textAnchor="middle"
                    dominantBaseline="central"
                  >
                    {wand.wandNr}
                  </text>
                </g>
              )}
            </g>
          ))}

          {/* Anfangs- und Endknopfsis */}
          {selectedWandData && (
            <g>
              {/* Anfangsknopfsi */}
              <circle
                cx={
                  editing.current.isEditing &&
                  editing.current.index === selectedWand &&
                  editing.current.part === "start"
                    ? mousePos.x
                    : selectedWandData.startX
                }
                cy={
                  editing.current.isEditing &&
                  editing.current.index === selectedWand &&
                  editing.current.part === "start"
                    ? mousePos.y
                    : selectedWandData.startY
                }
                r={10}
                fill="var(--latte)"
                style={{ cursor: "pointer" }}
                onClick={(e) => {
                  e.stopPropagation();
                  if (!editing.current.isEditing) {
                    editing.current = { isEditing: true, index: selectedWand, part: "start" };
                  } else if (
                    editing.current.index === selectedWand &&
                    editing.current.part === "start"
                  ) {
                    applyWandEdit("start");
                    editing.current = { isEditing: false, index: null, part: "" };
                  } else {
                    editing.current = { isEditing: true, index: selectedWand, part: "start" };
                  }
                }}
              />
              {/* Endknopfsi */}
              <circle
                cx={
                  editing.current.isEditing &&
                  editing.current.index === selectedWand &&
                  editing.current.part === "end"
                    ? mousePos.x
                    : selectedWandData.endX
                }
                cy={
                  editing.current.isEditing &&
                  editing.current.index === selectedWand &&
                  editing.current.part === "end"
                    ? mousePos.y
                    : selectedWandData.endY
                }
                r={10}
                fill="var(--latte)"
                style={{ cursor: "pointer" }}
                onClick={(e) => {
                  e.stopPropagation();
                  if (!editing.current.isEditing) {
                    editing.current = { isEditing: true, index: selectedWand, part: "end" };
                  } else if (
                    editing.current.index === selectedWand &&
                    editing.current.part === "end"
                  ) {
                    applyWandEdit("end");
                    editing.current = { isEditing: false, index: null, part: "" };
                  } else {
                    editing.current = { isEditing: true, index: selectedWand, part: "end" };
                  }
                }}
              />
            </g>
          )}

          {drawing.current && (
            <line
              x1={wandStart.x}
              y1={wandStart.y}
              x2={mousePos.x}
              y2={mousePos.y}
              strokeWidth={5}
            />
          )}

          {/* Editing */}
          {editing.current.isEditing && editingWand && (
            <line
              x1={editing.current.part === "start" ? mousePos.x : editingWand.startX}
              y1={editing.current.part === "start" ? mousePos.y : editingWand.startY}
              x2={editing.current.part === "end" ? mousePos.x : editingWand.endX}
              y2={editing.current.part === "end" ? mousePos.y : editingWand.endY}
              strokeWidth={5}
              className="selected"
              pointerEvents="none"
            />
          )}
        </g>
      </svg>
    </div>
  );
};

export default Canvas;
