import React from "react";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  Tooltip,
  XAxis,
  YAxis,
  CartesianGrid,
  Legend
} from "recharts";
import ClearChart from "./clearChart.jsx";

const Chart = props => {
  const { id, clearChart, chart } = props;
  const { title, xLabel, dataTypes, data } = chart;

  const colors = [
    "#00796B",
    "#FFC300",
    "#00BCD4",
    "#C70039",
    "#900C3F",
    "#581845"
  ];

  return (
    <div className="card">
      <div className="card-body">
        <div className="card-title">
          <span className="text">{title}</span>
          <ClearChart chartId={id} clearChart={clearChart} />
        </div>
        <div className="fill-card">
          <ResponsiveContainer width="100%" height={450}>
            <LineChart data={data}>
              <Tooltip />
              <XAxis dataKey={xLabel} tickLine={false} />
              <YAxis width={40} axisLine={false} tickLine={false} />
              <CartesianGrid strokeDasharray="3 3" />
              <Legend iconSize={8} iconType="circle" />
              {dataTypes.map((dt, idx) => {
                return (
                  <Line
                    key={dt}
                    type="monotone"
                    dataKey={dt}
                    stroke={colors[idx]}
                    strokeWidth={2}
                    dot={false}
                  />
                );
              })}
            </LineChart>
          </ResponsiveContainer>
        </div>
      </div>
    </div>
  );
};

export default Chart;
