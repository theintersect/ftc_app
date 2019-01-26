const WebSocket = require('ws')
const logger = require('signale')

logger.config({
  displayTimestamp: true,
  displayDate: true
})

let client

client = new WebSocket('ws://192.168.49.1:8887')

logger.success(`Connected to websocket server: ${client.url}`)

client.on('message', (message) => {
  try {
    logger.success(JSON.stringify(JSON.parse(message), null, 3))
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
