package org.firstinspires.ftc.teamcode.Tasks

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Models.Task
import org.firstinspires.ftc.teamcode.Utils.BasicServer
import org.firstinspires.ftc.teamcode.Utils.Logger
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.util.*

class WebsocketTask(opMode: LinearOpMode): Task(opMode, "WEBSOCKET_TASK"){

    init{

    }
    val server = BasicServer(8887)
    private val l = Logger("WEBSOCKET_TASK")
    init {
        l.log("Created websocket on port 8887")
    }


    override fun run(){
        server.start()
        l.log("Websocket Server started on port: " + server.port)
        val sysin = BufferedReader(InputStreamReader(System.`in`))

        while(opMode.opModeIsActive() && !opMode.isStopRequested && this.running){
            var input: String? = null
            try {
                input = sysin.readLine()
            } catch (e: IOException) {
                e.printStackTrace()
                l.log(e.toString())
                l.log(Arrays.toString(e.stackTrace))
            }
            if (input != null) {
                l.log(input)
                server.broadcast(input)
                if (input == "exit") {
                    server.stop(1000)
                    this.stopThread()
                }
            }
        }
    }

    override fun stopThread(){
        this.server.stop(1000)
        l.log("KILLED SERVER")
    }

}