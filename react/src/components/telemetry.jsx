import React from "react";
import TelemetryItem from "./telemetryItem.jsx";

const Telemetry = props => {
  const { telemetry } = props;
  const { telemetryItems } = telemetry;

  const telemetryStyles = { fontFamily: "Consolas", fontSize: "12px" };

  return (
    <div className="card">
      <div className="card-body">
        <div className="card-title">
          <span className="text">Telemetry</span>
        </div>
        <ul className="list-group" style={telemetryStyles}>
          {mapTelemetryItems(telemetryItems)}
        </ul>
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
