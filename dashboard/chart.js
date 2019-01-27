class Chart {
  constructor() {
    this.time = [];
    // this.setpoint = [];
    this.actual = [];
    this.chart = c3.generate({
      data: {
        x: "time",
        columns: [
          ["time"].concat(this.time),
          // ["setpoint"].concat(this.setpoint),
          ["actual"].concat(this.actual)
        ]
      }
    });
    this.startTime = undefined;
  }

  addData(time, /*setpoint, */ actual) {
    this.time.push(time);
    // this.setpoint.push(setpoint);
    this.actual.push(actual);
  }

  updateChart() {
    this.chart.load({
      columns: [
        ["time"].concat(this.time),
        // ["setpoint"].concat(this.setpoint),
        ["actual"].concat(this.actual)
      ]
    });
  }

  addDataFromJSON(json) {
    if (this.startTime === undefined) {
      this.startTime = json.ts;
    }
    let time = json.ts - this.startTime;
    let error = json.error;
    console.log(`Time: ${time}, Error: ${error}`);
    this.addData(time, error);
    this.updateChart();
  }

  // addDataFromString(dataString) {
  //   if (this.startTime === undefined) {
  //     this.startTime = new Date().getTime();
  //   }
  //   let time = new Date().getTime();
  //   let values = dataString.toString().split(" ");
  //   this.addData(time - this.startTime, values[0], values[1]);
  //   this.updateChart();
  // }

  reset(update) {
    this.time = [];
    // this.setpoint = [];
    this.actual = [];
    this.startTime = undefined;
    if (update) {
      chart.update();
    }
  }
}
