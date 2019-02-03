import React from "react";
import TelemetryItem from "./telemetryItem.jsx";

const Telemetry = props => {
  const { telemetry } = props;
  const { telemetryItems } = telemetry;

  const telemetryStyles = { fontFamily: "courier", fontSize: "18px" };

  return (
    <div className="card">
      <div className="card-body">
        <div className="card-title">
          <span className="text">Telemetry</span>
        </div>
        <table className="table table-striped table-hover table-sm" style={telemetryStyles}>
        <thead>
          <td>Variable</td>
          <td>Value</td>
        </thead>
        <tbody>
        {mapTelemetryItems(telemetryItems)}
        </tbody>
        </table>
      </div>
    </div>
  );
};

const mapTelemetryItems = telemetryItems => {
  let jsx = [];
  for (const itemName in telemetryItems) {
    jsx.push(
      <TelemetryItem
        key={itemName}
        label={itemName}
        message={telemetryItems[itemName]}
      />
    );
  }
  return jsx;
};

export default Telemetry;
