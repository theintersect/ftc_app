export default class TelemetryObject {
  constructor(telemetryItems = {}) {
    this.telemetryItems = telemetryItems;
  }

  addData = (name, value) => {
    let newTelemetryItems = JSON.parse(JSON.stringify(this.telemetryItems));
    newTelemetryItems[name.toLocaleUpperCase()] = value;
    return new TelemetryObject(newTelemetryItems);
  };
}
