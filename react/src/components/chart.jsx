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

const Chart = props => {
  const { clearChart, chart } = props;
  const { title, xLabel, dataTypes, data } = chart;

  const colors = [
    "#000000",
    "#FFC300",
    "#00FF00",
    "#C70039",
    "#900C3F",
    "#581845"
  ];


  return (
    <div className="card">
      <div className="card-body">
        <div className="card-title">
          <h5>{title}</h5>        
          {/* <a onClick={clearChart} className="">Clear</a> */}
        </div>

        <div className="fill-card">
          <ResponsiveContainer width="100%" height="100%">
            <LineChart data={data}>
              <XAxis dataKey={xLabel} />
              <YAxis width={40} />
              <Tooltip />
              <CartesianGrid strokeDasharray="3 3" />
              <Legend />
              {dataTypes.map((dt, idx) => {
                return (
                  <Line
                    key={dt}
                    type="monotone"
                    dataKey={dt}
                    stroke={colors[idx]}
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
