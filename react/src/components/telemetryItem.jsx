import React from "react";
const telemetryStyles = { fontFamily: "courier", fontSize: "18px" };

const TelemetryItem = props => {
  return (
    <tr style={telemetryStyles}>
      <td>
        {props.label}
      </td>
      <td>
        {props.message}
      </td>
    </tr>
    // <li className="list-group-item" style={telemetryStyles}>
    //   {formatLogItem(props.label, props.message)}
    // </li>
  );
};

const formatLogItem = (label, message) => {
  return `[${label}]: ${message}`;
};

export default TelemetryItem;
