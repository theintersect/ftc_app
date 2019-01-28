const WebSocket = require("ws");

const server = new WebSocket.Server({ port: 8080 });
server.on("connection", socket => {
  for (let ts = 0; ts < 10; ts++) {
    const data = {"event":"pid","ts":1548570843355,"body":{"ts":1548570843354,"error":-10,"de":0,"dt":-0.005,"p":-0.1,"i":-0.7363999999999907,"d":0,"output":-0.8363999999999907}} 
    socket.send(JSON.stringify(data));
  }
});