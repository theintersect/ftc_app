import React from "react";

const TelemetryItem = props => {
  return (
    <li className="list-group-item">
      {formatLogItem(props.label, props.message)}
    </li>
  );
};

const formatLogItem = (label, message) => {
  return `[${label}]: ${message}`;
};

export default TelemetryItem;
