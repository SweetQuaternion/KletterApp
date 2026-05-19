import { useState } from "react";
import WandInfoBox from "./WandInfoBox";
import { isAdmin } from "../../utils/keycloak";
import type { RouteResponseDTO, WandResponseDTO } from "../../api/model";
import TouchTracker from "../touch/TouchTracker";

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
  const [offset, setOffset] = useState({ x: 0, y: 0 });

  function onWheel(e: React.WheelEvent<SVGSVGElement>) {
    setScale(scale + (e.deltaY > 0 ? -0.02 : 0.02));
  }

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
      <div className="routenkarte-container">
        <svg
          onWheel={onWheel}
          onClick={() => handleWandClick(null)}
          className="routenkarte"
          xmlns="http://www.w3.org/2000/svg"
          width="100%"
          height="100%"
        >
          <g
            transform={`
            translate(${500 + offset.x * scale}, ${500 + offset.y * scale}) 
            scale(${scale}) 
            translate(200, -100)
          `}
          >
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

                    {selectedWand && selectedWandBox && selectedWand.wandNr === wand.wandNr ? (
                      <foreignObject
                        x={selectedWandBox.boxX}
                        y={selectedWandBox.boxY}
                        width="370"
                        height={
                          (selectedWand.routen?.length || 0) * 40 + (isAdmin() ? 40 : 0) + 160
                        }
                      >
                        <div className="embedded">
                          <WandInfoBox
                            selectedWand={selectedWand}
                            routen={selectedWand.routen || []}
                            setSelectedRoute={setSelectedRoute}
                            setShowNeueRoute={setShowNeueRoute}
                          />
                        </div>
                      </foreignObject>
                    ) : null}
                  </g>
                ) : null}
              </g>
            ))}
          </g>
        </svg>
      </div>
    </TouchTracker>
  );
}

export default SVGMap;
