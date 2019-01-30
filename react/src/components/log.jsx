import React from "react";

const Log = props => {
  const { log } = props;
  const { logItems } = log;

  const logStyles = { fontFamily: "Consolas", fontSize: "12px" };

  return (
    <div className="card">
      <div className="card-body">
        <div className="card-title">
          <span className="text">Log</span>
        </div>
        <textarea
          className="form-control"
          style={logStyles}
          rows={15}
          readOnly={true}
          value={getLogText(logItems)}
        />
      </div>
    </div>
  );
};

const getLogText = logItems => {
  const formatLogItem = logItem => {
    return `[${logItem.label}]: ${logItem.message}`;
  };
  let logText = "";
  logItems.forEach(item => {
    logText += formatLogItem(item) + "\n";
  });
  return logText;
};

export default Log;
