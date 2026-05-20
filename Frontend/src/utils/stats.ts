import type { AscentResponseDTO } from "../api/model";

export type PeriodStats = {
  period: string;
  avg: number | null;
  max: number | null;
};

function getLast12Months(): string[] {
  return Array.from({ length: 12 }, (_, i) => {
    const date = new Date();
    date.setMonth(date.getMonth() - i);
    return `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}`;
  }).reverse();
}

function getLast12Weeks(): string[] {
  return Array.from({ length: 12 }, (_, i) => {
    const date = new Date();
    date.setDate(date.getDate() - i * 7);
    return getISOWeekPeriod(date);
  }).reverse();
}

export function groupByMonth(ascents: AscentResponseDTO[]): PeriodStats[] {
  const grouped = ascents
    .filter(
      (a): a is AscentResponseDTO & { route: { schwierigkeit: number } } =>
        a.route.schwierigkeit != null,
    )
    .reduce<Record<string, number[]>>((acc, { datum, route }) => {
      const date = new Date(datum);
      const period = `${date.getFullYear()}-${String(date.getMonth() + 1).padStart(2, "0")}`;
      return { ...acc, [period]: [...(acc[period] ?? []), route.schwierigkeit] };
    }, {});

  return getLast12Months().map((period) => {
    const difficulties = grouped[period] ?? [];
    return {
      period,
      avg:
        difficulties.length > 0
          ? difficulties.reduce((s, d) => s + d, 0) / difficulties.length
          : null,
      max: difficulties.length > 0 ? Math.max(...difficulties) : null,
    };
  });
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
  const grouped = ascents
    .filter(
      (a): a is AscentResponseDTO & { route: { schwierigkeit: number } } =>
        a.route.schwierigkeit != null,
    )
    .reduce<Record<string, number[]>>((acc, { datum, route }) => {
      const period = getISOWeekPeriod(new Date(datum));
      return { ...acc, [period]: [...(acc[period] ?? []), route.schwierigkeit] };
    }, {});

  return getLast12Weeks().map((period) => {
    const difficulties = grouped[period] ?? [];
    return {
      period,
      avg:
        difficulties.length > 0
          ? difficulties.reduce((s, d) => s + d, 0) / difficulties.length
          : null,
      max: difficulties.length > 0 ? Math.max(...difficulties) : null,
    };
  });
}
