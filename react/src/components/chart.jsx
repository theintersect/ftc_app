import React from "react";
import {
  ResponsiveContainer,
  LineChart,
  Line,
  XAxis,
  YAxis,
  CartesianGrid,
  Legend
} from "recharts";

const Chart = props => {
  const { chart } = props;
  const { title, xLabel, dataTypes, data } = chart;

  const colors = [
    "#DAF7A6",
    "#FFC300",
    "#FF5733",
    "#C70039",
    "#900C3F",
    "#581845"
  ];

  return (
    <div className="card">
      <div className="card-body">
        <h5 className="card-title">{title}</h5>
        <div className="fill-card">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={data}>
              <XAxis dataKey={xLabel} />
              <YAxis width={40} />
              <CartesianGrid strokeDasharray="3 3" />
              <Legend />
              {dataTypes.map((dt, idx) => {
                return (
                  <Line
                    key={dt}
                    type="monotone"
                    dataKey={dt}
                    stroke={colors[idx]}
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
