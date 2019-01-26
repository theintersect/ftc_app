function initSocket() {
  socket = new WebSocket("ws://192.168.49.1:8887");

  socket.onopen = () => {
    console.log("Connection established!");
  };

  socket.onmessage = message => {
    let values = message.data.split(":");

    if (values[0] === "[PID]") {
      chart.addDataFromString(values[1]);
    } else if (values[0] === "[LOG]") {
      log.log(values[1]);
    } else if (values[0] === "[TELEMETRY]") {
      telemetry.addItemFromString(values[1]);
    }
  };
}
