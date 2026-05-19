import type { AscentResponseDTO } from "../api/model";

export type PeriodStats = {
  period: string;
  avg: number;
  max: number;
};

export function groupByMonth(ascents: AscentResponseDTO[]): PeriodStats[] {
  return Object.entries(
    ascents
      .filter(
        (a): a is AscentResponseDTO & { route: { schwierigkeit: number } } =>
          a.route.schwierigkeit != null,
      )
      .reduce<Record<string, number[]>>((acc, { datum, route }) => {
        const date = new Date(datum);
        const period = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}`;
        return { ...acc, [period]: [...(acc[period] ?? []), route.schwierigkeit] };
      }, {}),
  )
    .sort(([a], [b]) => a.localeCompare(b))
    .map(([period, difficulties]) => ({
      period,
      avg: difficulties.reduce((s, d) => s + d, 0) / difficulties.length,
      max: Math.max(...difficulties),
    }));
}

function getISOWeekPeriod(date: Date): string {
  const tmp = new Date(date);
  tmp.setHours(0, 0, 0, 0);
  tmp.setDate(tmp.getDate() + 4 - (tmp.getDay() || 7));
  const year = tmp.getFullYear();
  const week = String(
    Math.ceil(((tmp.getTime() - new Date(year, 0, 1).getTime()) / 86400000 + 1) / 7),
  ).padStart(2, "0");
  return `${year}-W${week}`;
}

export function groupByWeek(ascents: AscentResponseDTO[]): PeriodStats[] {
  return Object.entries(
    ascents
      .filter(
        (a): a is AscentResponseDTO & { route: { schwierigkeit: number } } =>
          a.route.schwierigkeit != null,
      )
      .reduce<Record<string, number[]>>((acc, { datum, route }) => {
        const period = getISOWeekPeriod(new Date(datum));
        return { ...acc, [period]: [...(acc[period] ?? []), route.schwierigkeit] };
      }, {}),
  )
    .sort(([a], [b]) => a.localeCompare(b))
    .map(([period, difficulties]) => ({
      period,
      avg: difficulties.reduce((s, d) => s + d, 0) / difficulties.length,
      max: Math.max(...difficulties),
    }));
}
