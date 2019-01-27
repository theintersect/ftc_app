function initSocket() {
  socket = new WebSocket("ws://192.168.49.1:8887");

  socket.onopen = () => {
    console.log("Connection established!");
  };

  socket.onmessage = message => {
    parseMessage(message);
    // let contents = message.data.split(":");
    // if (contents[0] === "[PID]") {
    //   chart.addDataFromString(contents[1]);
    // } else if (contents[0] === "[LOG]") {
    //   log.log(contents[1]);
    // } else if (contents[0] === "[TELEMETRY]") {
    //   telemetry.addItemFromString(contents[1]);
    // }
  };
}

function parseMessage(message) {
  let contents = JSON.parse(message);
  let body = contents.body;
  console.log(contents);
  switch (contents.event.toLocaleLowerCase()) {
    case "pid":
      chart.addDataFromJSON(body);
      break;
    case "log":
      // TODO make legit
      log.log(body.message);
      break;
    case "telemetry":
      telemetry.addItemFromJSON(body);
      break;
  }
}
