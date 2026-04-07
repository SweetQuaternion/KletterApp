export const convertSchwierigkeitToString = (
  schwierigkeit: number | undefined,
): string => {
  if (schwierigkeit === 0) return "";
  if (schwierigkeit === undefined) return "?";
  if (schwierigkeit % 1 === 0) return schwierigkeit.toString();
  let base = Math.floor(schwierigkeit);
  const modifier = schwierigkeit % 1 < 0.5 ? "+" : "-";
  if (modifier === "-") base++;
  return `${base}${modifier}`;
};

export const convertSchwierigkeitToNumber = (
  schwierigkeit: string,
): number | null => {
  if (schwierigkeit === "") return null;
  const match = schwierigkeit.match(/^(\d+)([+-]?)$/);
  if (!match) throw new Error("Ungültiges Format");
  const base = parseInt(match[1], 10);
  const modifier = match[2];
  if (modifier === "+") return base + 0.3;
  if (modifier === "-") return base - 0.3;
  return base;
};
