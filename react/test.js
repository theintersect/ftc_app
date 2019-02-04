const WebSocket = require('ws');

const wss = new WebSocket.Server({ port: 8080 });

wss.on('connection', function connection(ws) {
  ws.on('message', function incoming(message) {
    console.log('received: %s', message);
  });

  setInterval(() => {
    ws.send(JSON.stringify(

        {
            event:"telemetry",
            body:{
            "x":52,
            "y":124,
            "isRunning":true,
            "frankisgay":true,
            "antonStatus":"not gay"
            }
            
        }


    ));
    console.log('sent')
},2000)
});

