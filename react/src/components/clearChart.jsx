import React from "react";

const ClearChart = props => {
  const { clearChart, chartId } = props;
  return (
    <button
      onClick={() => clearChart(chartId)}
      className="btn btn-outline-danger btn-sm float-sm-right"
    >
      Clear
    </button>
  );
};

export default ClearChart;
