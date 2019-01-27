let socket = undefined;
let chart = new Chart();
let telemetry = new Telemetry();
let log = new Log();

$(document).ready(() => {
  initSocket();
});

$("#config").submit(event => {
  event.preventDefault();

  let formData = $("#config").serializeArray();
  config = [];
  for (let i = 0; i < formData.length; i++) {
    configStr = formData[i].value;
    if (configStr == "") {
      config.push(0);
    } else {
      config.push(parseFloat(configtr));
    }
  }
  console.log(config);
  socket.send("[UPID]:" + config.join(","));
});

$("#runPID").click(() => {
  chart.reset(false);
  socket.send("[RPID]");
});

$("#clearLog").click(() => {
  $("#log").html("");
});
