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

export const pointsToLevel = (points: number): number => {
  if (points < 10) return 0;
  if (points < 50) return 1;
  if (points < 100) return 2;
  if (points < 300) return 3;
  if (points < 600) return 4;
  if (points < 1000) return 5;
  if (points < 1500) return 6;
  if (points < 2000) return 7;
  return Math.floor((points - 2000) / 1000) + 8;
};
