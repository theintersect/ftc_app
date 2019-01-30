import React, { Component } from "react";

import NavBar from "./components/navbar.jsx";
import Log from "./components/log.jsx";
import Telemetry from "./components/telemetry.jsx";
import Chart from "./components/chart.jsx";

import LogObject from "./objects/log.js";
import TelemetryObject from "./objects/telemetry.js";
import ChartObject from "./objects/chart.js";

export default class App extends Component {
  constructor() {
    super();
    this.state = {
      log: new LogObject(),
      telemetry: new TelemetryObject(),
      pidChart: new ChartObject("PID Chart", "timestamp", [
        "p",
        "i",
        "d",
        "output"
      ]),
      errorChart: new ChartObject("Error Chart", "timestamp", ["error"]),
      connected: false
    };
    this.initSocket();
  }

  render() {
    return (
      <React.Fragment>
        <NavBar connected={this.state.connected} />
        <div className="content">
          <div className="container-fluid">
            <div className="row">
              <div className="col col-lg-12 col-sm-12">
                <Chart
                  id="pidChart"
                  clearChart={this.clearChart}
                  chart={this.state.pidChart}
                />
              </div>
              <div className="col col-lg-12 col-sm-12">
                <Chart
                  id="errorChart"
                  clearChart={this.clearChart}
                  chart={this.state.errorChart}
                />
              </div>
            </div>
            <div className="row">
              <div className="col col-lg-6 col-sm-12">
                <Log log={this.state.log} />
              </div>
              <div className="col col-lg-6 col-sm-12">
                <Telemetry telemetry={this.state.telemetry} />
              </div>
            </div>
          </div>
        </div>
      </React.Fragment>
    );
  }

  clearChart = chartId => {
    const chart = this.state[chartId].clear();
    this.setState({ [chartId]: chart });
  };

  initSocket = () => {
    const url = "ws://192.168.49.1:8887";
    // const url = "ws://localhost:8080";
    let socket = null;
    console.log("Connecting socket...");
    socket = new WebSocket(url);

    socket.onopen = () => {
      console.log("Connection established!");
      this.setState({ connected: true });
    };

    socket.onclose = () => {
      console.log("Socket closed.");
      this.setState({ connected: false });
      setTimeout(() => {
        this.initSocket();
      }, 1000);
    };

    socket.onmessage = message => {
      const contents = JSON.parse(message.data);
      console.log(contents);
      const event = contents.event;
      const body = contents.body;

      switch (event) {
        case "pid":
          setTimeout(() => {
            const pidChart = this.state.pidChart.addData(body.ts, [
              body.p,
              body.i,
              body.d,
              body.output
            ]);
            const errorChart = this.state.errorChart.addData(body.ts, [
              body.error
            ]);
            this.setState({ pidChart: pidChart, errorChart: errorChart });

          }, 0)
          break;
        case "telemetry":
          const telemetry = this.state.telemetry.addData(body.name, body.value);
          this.setState({ telemetry: telemetry });
          break;
        case "log":
          const log = this.state.log.log(body.label, body.message);
          this.setState({ log: log });
          break;
        default:
          console.log(`UNRECOGNIZED EVENT: ${event}`);
      }
    };
  };
}
