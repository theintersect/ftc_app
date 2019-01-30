import React from "react";

const ConnectionLabel = props => {
  const { connected } = props;
  return (
    <span className={getBadgeClasses(connected)}>
      {getBadgeText(connected)}
    </span>
  );
};

const getBadgeText = connected => {
  if (connected) {
    return "Connected";
  } else {
    return "Connecting...";
  }
};

const getBadgeClasses = connected => {
  let classes = "badge badge-pill ml-auto badge-";
  if (connected) {
    return classes + "success";
  } else {
    return classes + "danger";
  }
};

export default ConnectionLabel;
