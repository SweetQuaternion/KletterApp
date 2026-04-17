import { useRef, useCallback, type CSSProperties, type ReactNode } from "react";

interface TouchMoveDelta {
  dx: number;
  dy: number;
}

interface TouchTrackerProps {
  onTouchMove?: (delta: TouchMoveDelta) => void;
  onTouchZoom?: (zoomDelta: number) => void;
  children?: ReactNode;
  style?: CSSProperties;
  className?: string;
}

/**
 * TouchTracker
 *
 * Tracks pointer events on a div and calls command-pattern callbacks:
 *   - onTouchMove({ dx, dy })   – pixel delta of the centroid since the last event (single or multi-touch pan)
 *   - onTouchZoom(zoomDelta)    – multiplicative zoom delta (>1 = pinch-out/zoom-in, <1 = pinch-in/zoom-out)
 *
 * Single-pointer:  only onTouchMove fires (clicks pass through unchanged).
 * Two-pointer:     both onTouchMove (centroid pan) and onTouchZoom (pinch) fire.
 * Clicks:          pointer events with no movement are not suppressed; click events bubble normally.
 *
 * Based on the MDN Pinch Zoom Gestures reference:
 * https://developer.mozilla.org/en-US/docs/Web/API/Pointer_events/Pinch_zoom_gestures
 */
export default function TouchTracker({
  onTouchMove,
  onTouchZoom,
  children,
  style,
  className,
}: TouchTrackerProps) {
  // Active pointer cache: Map<pointerId, PointerEvent>
  const pointersRef = useRef<Map<number, PointerEvent>>(new Map());

  // Last centroid position for delta calculation
  const lastCentroidRef = useRef<{ x: number; y: number } | null>(null);

  // Last distance between two pointers for pinch delta calculation
  const lastDistanceRef = useRef<number | null>(null);

  /** Compute the centroid of all active pointers */
  const getCentroid = (
    pointers: Map<number, PointerEvent>,
  ): { x: number; y: number } => {
    let x = 0;
    let y = 0;
    for (const e of pointers.values()) {
      x += e.clientX;
      y += e.clientY;
    }
    const count = pointers.size;
    return { x: x / count, y: y / count };
  };

  /** Compute distance between exactly two pointers */
  const getTwoPointerDistance = (
    pointers: Map<number, PointerEvent>,
  ): number => {
    const [a, b] = [...pointers.values()];
    const dx = a.clientX - b.clientX;
    const dy = a.clientY - b.clientY;
    return Math.hypot(dx, dy);
  };

  const onPointerDown = useCallback((e: React.PointerEvent<HTMLDivElement>) => {
    pointersRef.current.set(e.pointerId, e.nativeEvent);

    // Reset reference points whenever the finger count changes
    lastCentroidRef.current = getCentroid(pointersRef.current);

    if (pointersRef.current.size === 2) {
      lastDistanceRef.current = getTwoPointerDistance(pointersRef.current);
    }
  }, []);

  const onPointerMove = useCallback(
    (e: React.PointerEvent<HTMLDivElement>) => {
      if (!pointersRef.current.has(e.pointerId)) return;

      // Update cached event
      pointersRef.current.set(e.pointerId, e.nativeEvent);

      const pointers = pointersRef.current;
      const centroid = getCentroid(pointers);

      // --- Pan delta (always reported) ---
      if (lastCentroidRef.current) {
        const dx = centroid.x - lastCentroidRef.current.x;
        const dy = centroid.y - lastCentroidRef.current.y;

        if ((dx !== 0 || dy !== 0) && onTouchMove) {
          onTouchMove({ dx, dy });
        }
      }
      lastCentroidRef.current = centroid;

      // --- Pinch / zoom delta (two-pointer only) ---
      if (pointers.size === 2 && lastDistanceRef.current !== null) {
        const distance = getTwoPointerDistance(pointers);
        const zoomDelta = distance / lastDistanceRef.current;

        if (zoomDelta !== 1 && onTouchZoom) {
          onTouchZoom(zoomDelta);
        }
        lastDistanceRef.current = distance;
      }
    },
    [onTouchMove, onTouchZoom],
  );

  const onPointerUp = useCallback((e: React.PointerEvent<HTMLDivElement>) => {
    pointersRef.current.delete(e.pointerId);

    // Reset reference points after a finger lifts
    if (pointersRef.current.size > 0) {
      lastCentroidRef.current = getCentroid(pointersRef.current);
      if (pointersRef.current.size === 2) {
        lastDistanceRef.current = getTwoPointerDistance(pointersRef.current);
      } else {
        lastDistanceRef.current = null;
      }
    } else {
      lastCentroidRef.current = null;
      lastDistanceRef.current = null;
    }
  }, []);

  const onPointerCancel = useCallback(
    (e: React.PointerEvent<HTMLDivElement>) => {
      pointersRef.current.delete(e.pointerId);
      if (pointersRef.current.size === 0) {
        lastCentroidRef.current = null;
        lastDistanceRef.current = null;
      }
    },
    [],
  );

  return (
    <div
      style={{ touchAction: "none", ...style }}
      className={className}
      onPointerDown={onPointerDown}
      onPointerMove={onPointerMove}
      onPointerUp={onPointerUp}
      onPointerCancel={onPointerCancel}
    >
      {children}
    </div>
  );
}
