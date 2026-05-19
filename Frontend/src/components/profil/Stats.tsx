import {
  CartesianGrid,
  Line,
  LineChart,
  ResponsiveContainer,
  Tooltip,
  XAxis,
  YAxis,
} from "recharts";
import type { AscentResponseDTO } from "../../api/model";
import { groupByMonth, groupByWeek } from "../../utils/stats";
import "../../styles/Stats.css";
import { convertSchwierigkeitToString } from "../../utils/conversions";

interface Props {
  ascents: AscentResponseDTO[];
  timeFrame: "week" | "month";
}

function Stats({ ascents, timeFrame }: Props) {
  const data = timeFrame === "month" ? groupByMonth(ascents) : groupByWeek(ascents);
  const title = timeFrame === "month" ? "Monatsverlauf" : "Wochenverlauf";

  const formatPeriodLabel = (period: string) => {
    if (timeFrame === "month") {
      const [year, month] = period.split("-").map(Number);
      return new Intl.DateTimeFormat("de-DE", { month: "short", year: "numeric" }).format(
        new Date(year, month - 1, 1),
      );
    }

    const [year, week] = period.split("-W");
    return `KW ${week} ${year}`;
  };

  const formatValue = (value: unknown) => {
    if (typeof value === "number") {
      return convertSchwierigkeitToString(value);
    }
    return String(value);
  };

  return (
    <section className="stats-card">
      <div className="stats-header">
        <h3>{title}</h3>
      </div>

      {data.length > 0 ? (
        <div className="stats-chart-wrap">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={data} margin={{ top: 8, right: 8, bottom: 8, left: 0 }}>
              <CartesianGrid stroke="var(--latte)" vertical={false} />
              <XAxis
                dataKey="period"
                stroke="var(--mokka)"
                tickLine={false}
                axisLine={false}
                tickMargin={4}
                tickFormatter={formatPeriodLabel}
                interval="preserveStartEnd"
              />
              <YAxis
                stroke="var(--mokka)"
                tickLine={false}
                axisLine={false}
                width={36}
                tickMargin={8}
                allowDecimals
              />
              <Tooltip formatter={(value) => formatValue(value)} />
              <Line
                type="monotone"
                dataKey="avg"
                name="Durchschnitt"
                stroke="var(--pfirsichorange)"
                strokeWidth={4}
                dot={{ r: 4, strokeWidth: 1, fill: "var(--pfirsichorange)" }}
                activeDot={{
                  r: 6,
                  stroke: "var(--pfirsichorange)",
                  strokeWidth: 2,
                  fill: "var(--pfirsichorange)",
                }}
              />
              <Line
                type="monotone"
                dataKey="max"
                name="Maximum"
                stroke="var(--himmelsblau)"
                strokeWidth={4}
                dot={{ r: 4, strokeWidth: 1, fill: "var(--himmelsblau)" }}
                activeDot={{
                  r: 6,
                  stroke: "var(--himmelsblau)",
                  strokeWidth: 2,
                  fill: "var(--himmelsblau)",
                }}
              />
            </LineChart>
          </ResponsiveContainer>
        </div>
      ) : (
        <div className="stats-empty sans-serif">
          Noch keine Werte vorhanden. Sobald du Routen geklettert bist, erscheint hier dein Verlauf.
        </div>
      )}
    </section>
  );
}

export default Stats;
