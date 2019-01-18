package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Tasks.ArmTask
import org.firstinspires.ftc.teamcode.Tasks.DriveTrainTask
import org.firstinspires.ftc.teamcode.Utils.Logger

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "TeleOp")

class Tele: LinearOpMode() {
    val l = Logger("TELEOP")
    val dtTask = DriveTrainTask(this)
//    val armTask = ArmTask(this)

    init {
        l.log("TeleOp initialized.")
    }

    override fun runOpMode() {
        l.log("Waiting for start...")
        waitForStart()
        dtTask.start()
//        armTask.start()
        l.log("Started.")
        while (opModeIsActive() && !isStopRequested) {
            dtTask.run()
//            armTask.run()
        }
        dtTask.stopThread()
//        armTask.stopThread()
    }
}

