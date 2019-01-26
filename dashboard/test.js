const WebSocket = require('ws')
const logger = require('signale')

logger.config({
  displayTimestamp: true,
  displayDate: true
})

let client
client = new WebSocket('ws://192.168.49.1:8887')
logger.success(`Connected to websocket server: ${client.url}`)

/*
All messages will be in this sample format:

{
    "event": "pid/telemetry/whatever",
    "ts": 1548488635655,
    "body":{ //whatever stuff the event entails }
}

EX:
{
    "event": "ping",
    "ts": 1548488635655,
    "body":{}
}

{
    "event": "pid",
    "ts": 1548488635655,
    "body":{
        "ts":1548488635655,
        "error":-10,
        "de":0,
        "dt":-0.006,
        "p":-0.1,
        "i":-0.115759,
        "d":0,
        "output":-0.215759
    }
}

// read the telemetry
{
    "event": "telemetry",
    "ts": 1548488635655,
    "body":{
        "variable": "heading",
        "value": 123.234
    }
}
// set settings
{
    "event": "settings",
    "ts": 1548488635655,
    "body":{
        "variable": "heading",
        "value": 123.234
    }
}

*/

client.on('message', (message) => {
  try {
    let message = JSON.parse(message)
    let {event, ts, body} = message
    logger.log(event)
    logger.log(`Latency: ${new Date().getMilliseconds - ts*1000}ms`)
    logger.success(JSON.stringify(body, null, 3))
  } catch (e) {
    logger.success(message)
  }
})

client.on('error', error => {
  logger.error(error)
  if (error.toString().includes('ECONNREFUSED')) {
    client = new WebSocket('ws://192.168.49.1:8887')
  }
})

function send (message) {
  client.send(message)
  logger.info(`Outgoing: ${message}`)
}
// setInterval(() => {
//   send('ping')
// }, 2000)
