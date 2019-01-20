package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Tasks.*
import org.firstinspires.ftc.teamcode.Utils.Logger

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

        l.log("Waiting for start...")
        waitForStart()
        dtTask.start()
        armTask.start()
        sweeperTask.start()
        hookTask.start()
        dumperTask.start()
        armStopperTask.start()

        l.log("Started.")
        while (opModeIsActive() && !isStopRequested) {
//            dtTask.run()
//            armTask.run()
//            sweeperTask.run()
//            hookTask.run()
            continue
        }
        dtTask.stopThread()
        hookTask.stopThread()
        armTask.stopThread()
        sweeperTask.stopThread()
        dumperTask.stopThread()
        armStopperTask.stopThread()

        l.log("Stopped threads")
    }
}

