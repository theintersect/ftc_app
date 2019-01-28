import React from "react";
import LogItem from "./logItem.jsx";

const Telemetry = props => {
  const { telemetry } = props;
  const { telemetryItems } = telemetry;

  const telemetryStyles = { fontFamily: "Consolas", fontSize: "12px" };

  return (
    <div className="card">
      <div className="card-body">
        <h5 className="card-title">Telemetry</h5>
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
      <LogItem
        key={itemName}
        label={itemName}
        message={telemetryItems[itemName]}
      />
    );
  }
  return jsx;
};

export default Telemetry;
