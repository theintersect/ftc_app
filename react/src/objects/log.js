class LogObject {
  constructor(logItems = [], runningId = 0) {
    this.logItems = logItems;
    this.runningId = runningId;
  }

  log = (label, message) => {
    let newLogItems = [...this.logItems];
    newLogItems.push({
      id: this.runningId,
      label: label.toLocaleUpperCase(),
      message: message
    });
    return new LogObject(newLogItems, this.runningId + 1);
  };
}

export default LogObject;
