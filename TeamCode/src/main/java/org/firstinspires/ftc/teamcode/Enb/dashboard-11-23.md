# Designing an Autonomous Dashboard

Date: 11/23/18

Members present: Frank Zheng

Documented by: Frank Zheng

## Goal

Autonomous testing can be frustrating; constantly uploading code to the phone, not being able to systematically debug your program, and having to agressively tune hard-coded constants can add up to a painstaking process.

To combat this, we've previously implemented wireless deployment, logging, and combining our hard-coded values into a JSON file that can be uploaded to the phone instantly without a 2-minute compile time. So far, it's been a success, and our efficiency testing autonomous has greatly improved.

Yet, we decided to take it a step further: designing a dashboard to display all the information we need during autonomous testing within a browser window.

The dashboard should be able have:

- logs

- telemetry (specific values such as robot angle and speed)

- charting capability to visualize PID tests

- ability to edit values and deploy the updated values to the robot

## The Frontend

We decided to run the dashboard from within a browser, which would be much simpler than creating a separate application for it. Of course, we needed a frontend to display all the incoming information from the robot controller.

### The HTML and CSS

The HTML and CSS portion is relatively simple; just a navbar, two paragraphs for log and telemetry, and a form for the robot configuration. I used Bootstrap, a CSS library that made creating the GUI pretty easy.

Here is the final result:

![Error displaying image](11-23/frontend.PNG)

### Logging and Telemetry

Again, nothing much here. I just created `Log` and `Telemetry` classes, which had `log` and `addItem` functions for uploading the inner HTML of the paragraph elements. Here's my method for adding and an item to the telemetry display:

```js
addItem(name, value) {
    this.items[name] = String(value);
    this.updateTelemetry();
}
```

### Robot Configuration

For the robot configuration, I used an HTML form and jQuery to gather the form data and send it over a websocket. Here is the event listener I added for the form submission:

```js
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
  // Send over websocket
});
```

### Charting with C3

One of the most important capabilites we wanted to implement in the dashboard was live PID values. This way, we could see the robot's error from the setpoint while it is running a PID function, making debugging much more easy. The chart would show two lines, one representing the setpoint and one representing the actual value over time.

After experimenting with various charting libraries, I finally chose to use C3.js, which was simple and intuitive to use for time series data.

I created a simple class that would stored all the data in 3 arrays: `time`, `setpoint`, and `actual`. In addition, there is an `addData()` and `update()` function for the chart, so the chart can be updated over time.

```javascript
class Chart {
  constructor() {
    this.time = [];
    this.setpoint = [];
    this.actual = [];
    this.chart = c3.generate({...});
  }

  addData(time, setpoint, actual) {...}

  updateChart() {...}
}
```

I wrote some simple test code, feeding values into the chart. Here is the result:

![Error displaying image](11-23/chart.PNG)

Great, we're pretty much done with the GUI. Now for the hard part: the backend.

## The Backend

### Kotlin Websocket Server

Luckily, I already had the code for a Kotlin websocket server made with Javalin (written by Anton). I just had to integrate it into the frontend.

### Connecting from the Frontend

It took me a while to understand how websockets really work and get the server going, but it was pretty easy afterward as Javascript has native support for websockets. Below is a function I wrote to connect to a websocket and process incoming messages.

```javascript
function initSocket() {
  socket = new WebSocket("ws://127.0.0.1:5000");

  socket.onopen = () => {
    console.log("Connection established!");
  };

  socket.onmessage = message => {
    let contents = message.data.split(":");

    if (contents[0] === "[PID]") {
      chart.addDataFromString(contents[1]);
    } else if (contents[0] === "[LOG]") {
      log.log(contents[1]);
    } else if (contents[0] === "[TELEMETRY]") {
      telemetry.addItemFromString(contents[1]);
    }
  };
}
```

For the sake of time, I sent messages in the format "[type]:contents", which we definitely will refactor to JSON in the future.

### Updating the Backend from the Frontend

In order to be able to configure values from the dashboard, I needed a way to update the backend from the frontend, like an HTTP POST request. To do this, I needed to add message receiving capability. In the `WebSocketServer` class, I added an `onMessage` property to the websocket. As you can see, it's pretty similar to the above Javsascript code.

```kotlin
ws.onMessage { _, message ->
    println(message)

    val message = message.split(":")
    val messageType = message[0]

    when (messageType) {
        "[RPID]" -> runPID(wss)
        "[UPID]" -> updatePID(message[1])
    }
}
```

Here's a look at `runPID`, which uses the previously made `PID` class and simulates a PID process, sending live values to the frontend for it to display.

```kotlin
fun runPID(wss: WebSocketServer) {
    var x = 90.0
    val pid = PID(pidCoefficients, 0.0)
    pid.initController(x)
    while (Math.abs(x) > 0.5) {
        val output = pid.output(x, wss)
        wss.broadcastMessage("[PID]:${pid.desiredVal} $x")
        wss.broadcastMessage("[LOG]:PID updated")
        x += output
        Thread.sleep(100)
    }
}
```

In addition, I wrote an `updatePID` function, which takes in updated PID coefficients. This way, we can tune our constants directly from the dashboard when testing autonomous!

### Testing

Here's the result of `runPID` (without tuning any values).

![Error displaying image](11-23/chart.PNG)

Here's a test of `updatePID`; this is what the PID graph looked like after changing the PID coefficients from 0.1, 0, and 0 to 0.1, -0.004, and -10:

![Error displaying image](11-23/updated-chart.PNG)

Cool, it works!

## Next steps

Next, we have to get this working on our robot, which may require hours of debugging. However, since our backend is already written in Kotlin, it shouldn't be _too_ bad.

As for updates to the dashboard, here are a few things I would still like to implement:

- JSON websocket messages

- hosting the server on the robot controller so multiple laptops can connect to a single server

- more than just one chart and easy customization for which charts to show (maybe a drop-down menu to select which values to show)

- show live connection status to phone's websocket

- customizable configuration

- a prettier telemetry display :)
