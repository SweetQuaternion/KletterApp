import { useEffect, useRef, useState } from "react";
import * as d3 from "d3";
import WandInfoBox from "./WandInfoBox";
import { isAdmin } from "../../utils/keycloak";
import type { RouteResponseDTO, WandResponseDTO } from "../../api/model";

interface Props {
  scale: number;
  setScale: (scale: number) => void;
  wände: WandResponseDTO[];
  selectedWand: WandResponseDTO | null;
  setSelectedWand: (wand: WandResponseDTO | null) => void;
  setSelectedRoute: (route: RouteResponseDTO | null) => void;
  setShowNeueRoute: (show: boolean) => void;
  setEditingRoute: (route: RouteResponseDTO | null) => void;
}

function SVGMap({
  scale,
  setScale,
  wände,
  selectedWand,
  setSelectedWand,
  setSelectedRoute,
  setShowNeueRoute,
  setEditingRoute,
}: Props) {
  const svgRef = useRef<SVGSVGElement>(null);
  const gRef = useRef<SVGGElement>(null);
  const zoomRef = useRef<d3.ZoomBehavior<SVGSVGElement, unknown> | null>(null);

  const [transform, setTransform] = useState(d3.zoomIdentity);

  // d3-zoom einmal initialisieren
  useEffect(() => {
    const svgEl = svgRef.current;
    const gEl = gRef.current;
    if (!svgEl || !gEl) return;

    const zoom = d3
      .zoom<SVGSVGElement, unknown>()
      .scaleExtent([0.1, 10])
      .on("zoom", (event: d3.D3ZoomEvent<SVGSVGElement, unknown>) => {
        const { x, y, k } = event.transform;
        d3.select(gEl).attr("transform", `translate(${x}, ${y}) scale(${k})`);
        // Elternkomponente über neue Scale informieren (für den Scale-Knopf)
        setScale(k);
        setTransform(event.transform);
      });

    const { width, height } = svgEl.getBoundingClientRect();

    const initialTransform = d3.zoomIdentity.translate(width / 2, height / 2);

    zoomRef.current = zoom;
    d3.select(svgEl).call(zoom);
    d3.select(svgEl).call(zoom.transform, initialTransform);

    return () => {
      d3.select(svgEl).on(".zoom", null);
    };
  }, []);

  // Wenn die Elternkomponente scale von außen ändert (z.B. über Scale-Knopf),
  // d3-zoom programmatisch updaten – ohne das Zoom-Zentrum zu verschieben.
  useEffect(() => {
    const svgEl = svgRef.current;
    const zoom = zoomRef.current;
    if (!svgEl || !zoom) return;

    const current = d3.zoomTransform(svgEl);
    if (Math.abs(current.k - scale) < 0.001) return; // kein Loop

    d3.select(svgEl).call(zoom.scaleTo, scale);
  }, [scale]);

  // ja das Verhalten hier ist super unintuitiv, aber es funktioniert wegen des
  // Closure-Problems trotzdem. selectedWand ist immer ein Render hinterher und
  // deswegen funktioniert der Vergleich. Man sollte hier wohl useRef nehmen.
  const handleWandClick = (wand: WandResponseDTO | null) => {
    setSelectedWand(wand);
    if (selectedWand !== wand) {
      setSelectedRoute(null);
      setShowNeueRoute(false);
      setEditingRoute(null);
    }
    if (wand == null) setSelectedRoute(null);
  };

  const selectedWandBox = selectedWand
    ? {
        boxX:
          (selectedWand.startX + selectedWand.endX) / 2 -
          (selectedWand.endY - selectedWand.startY) * 0.05,
        boxY:
          (selectedWand.startY + selectedWand.endY) / 2 +
          (selectedWand.endX - selectedWand.startX) * 0.05,
      }
    : null;

  const [screenX, screenY] = selectedWandBox
    ? transform.apply([selectedWandBox.boxX, selectedWandBox.boxY])
    : [0, 0];

  return (
    <div className="routenkarte-container">
      <svg
        ref={svgRef}
        onClick={() => handleWandClick(null)}
        className="routenkarte"
        xmlns="http://www.w3.org/2000/svg"
        width="100%"
        height="100%"
      >
        <g ref={gRef}>
          {wände.map((wand) => (
            <g
              key={wand.wandNr}
              onClick={(e) => {
                e.stopPropagation();
                handleWandClick(wand);
              }}
            >
              <line
                x1={wand.startX}
                y1={wand.startY}
                x2={wand.endX}
                y2={wand.endY}
                stroke="black"
                strokeWidth={5}
                style={{ cursor: "pointer" }}
              />
              {(wand.routen?.length || 0) > 0 || isAdmin() ? (
                <g
                  role="button"
                  tabIndex={0}
                  onKeyDown={(e) => {
                    if (e.key === "Enter" || e.key === " ") handleWandClick(wand);
                  }}
                >
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
                    {wand.routen?.length || 0}
                  </text>
                </g>
              ) : null}
            </g>
          ))}
        </g>
      </svg>
      {selectedWand && selectedWandBox && (
        <div
          className="embedded"
          style={{ position: "absolute", left: screenX, top: screenY, pointerEvents: "auto" }}
        >
          <WandInfoBox
            selectedWand={selectedWand}
            routen={selectedWand.routen || []}
            setSelectedRoute={setSelectedRoute}
            setShowNeueRoute={setShowNeueRoute}
          />
        </div>
      )}
    </div>
  );
}

export default SVGMap;
