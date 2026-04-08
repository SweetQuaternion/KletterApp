// Punktesytem:

//  1. Level:    0 -   10 Punkte
//  2. Level:   11 -   49 Punkte
//  3. Level:   50 -   99 Punkte
//  4. Level:  100 -  299 Punkte
//  5. Level:  300 -  599 Punkte
//  6. Level:  600 -  999 Punkte
//  7. Level: 1000 - 1499 Punkte
//  8. Level: 1500 - 1999 Punkte
//  9. Level: 2000 - 2999 Punkte
// 10. Level: 3000 - 3999 Punkte
// 11. Level: 4000 - 4999 Punkte
// 12. Level: 5000+ Punkte etc.

// ab Level 9: 1000 Punkte pro Level

import { type AscentResponseDTO, AscentResponseDTOStyle } from "../api/model";

export const pointsToLevel = (ascents: AscentResponseDTO[]): number => {
  console.log(ascents);
  const points = ascentsToPoints(ascents);
  console.log(points);
  if (points < 10) return 1;
  if (points < 50) return 2;
  if (points < 100) return 3;
  if (points < 300) return 4;
  if (points < 600) return 5;
  if (points < 1000) return 6;
  if (points < 1500) return 7;
  if (points < 2000) return 8;
  if (points < 2000) return 9;
  return Math.floor((points - 2000) / 1000) + 9;
};

const ascentsToPoints = (ascents: AscentResponseDTO[]): number => {
  return ascents
    .map(
      (ascent) => ascent.route.schwierigkeit || 5 * styleToFactor(ascent.style),
    )
    .reduce((acc, points) => acc + points, 0);
};

const styleToFactor = (style: AscentResponseDTOStyle | undefined): number => {
  if (!style) return 1;
  if (style === AscentResponseDTOStyle.onsight) return 2.5;
  if (style === AscentResponseDTOStyle.flash) return 2;
  if (style === AscentResponseDTOStyle.redpoint) return 1.5;
  if (style === AscentResponseDTOStyle.pinkpoint) return 1;
  if (style === AscentResponseDTOStyle.toprope) return 0.5;
  if (style === AscentResponseDTOStyle.hangdog) return 0.5;
  if (style === AscentResponseDTOStyle.attempt) return 0.2;
  return 0;
};
