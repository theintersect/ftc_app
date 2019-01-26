package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Tasks.*
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.json.JSONObject

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp (Threaded)")

class Tele: LinearOpMode() {
    val l = Logger("TELEOP")

    init {
        l.log("TeleOp initialized.")
    }

    override fun runOpMode() {
        val dtTask = DriveTrainTask(this)
        val armTask = ArmTask(this)
        val sweeperTask = SweeperTask(this)
        val hookTask = HookTask(this)
        val dumperTask = DumperTask(this)
        val armStopperTask = ArmStopperTask(this)
        val socketTask = WebsocketTask(this)

        l.log("Waiting for start...")
        waitForStart()
        dtTask.start()
        armTask.start()
        sweeperTask.start()
        hookTask.start()
        dumperTask.start()
        armStopperTask.start()
        socketTask.start()

        l.log("Started.")
        while (opModeIsActive() && !isStopRequested) {
//            dtTask.run()
//            armTask.run()
//            sweeperTask.run()
//            hookTask.run()
            socketTask.server.broadcastMessage( JSONObject().put("type","ping").put("message","hello"))
            sleep(1000)
            continue
        }
        socketTask.stopThread()
        dtTask.stopThread()
        hookTask.stopThread()
        armTask.stopThread()
        sweeperTask.stopThread()
        dumperTask.stopThread()
        armStopperTask.stopThread()

        l.log("Stopped threads")
    }
}

