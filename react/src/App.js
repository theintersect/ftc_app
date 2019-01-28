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
      pidChart: new ChartObject("PID Chart", "timestamp", ["p", "i", "d", "output"]),
      errorChart: new ChartObject("Error Chart", "timestamp", ["error"])
    };
    this.initSocket();
  }

  render() {
    return (
      <React.Fragment>
        <NavBar />
        <main className="container-fluid py-md-3">
          <div className="row h-100">
            {/* <div className="col">
              <Log log={this.state.log} />
            </div>
            <div className="col">
              <Telemetry telemetry={this.state.telemetry} />
            </div> */}
            <div className="col">
              <Chart 
              clearChart={this.clearChart}
              chart={this.state.pidChart} />
            </div>
            <div className="col">
              <Chart 
              clearChart={this.clearChart}             
              chart={this.state.errorChart} />
            </div>
          </div>
        </main>
      </React.Fragment>
    );
  }

  clearChart = () => {
    console.log("clearChart")
    const pidChart = this.state.pidChart.clear();
    const errorChart = this.state.errorChart.clear();
    this.setState({pidChart: pidChart, errorChart: errorChart});
  }

  initSocket = () => {
    const url = "ws://192.168.49.1:8887";
    let socket = null;
    try {
      console.log("Connecting socket...");
      socket = new WebSocket(url);
    } catch (e) {
      console.log(e);

    }
    socket.onerror = (error) => {
      if(true){
        console.log('ws coonnection failed')
        setTimeout(() => {
          console.log("retying..")
          this.initSocket();
        }, 1000);  
      }
  
    }
    socket.onopen = () => {
      console.log("Connection established!");
    };

    socket.onmessage = message => {
      const contents = JSON.parse(message.data);
      console.log(contents);
      const event = contents.event;
      const body = contents.body;

      switch (event) {
        case "pid":
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
