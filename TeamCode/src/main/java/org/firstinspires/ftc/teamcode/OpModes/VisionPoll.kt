package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Tasks.*
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.Vision
import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile
import org.firstinspires.ftc.teamcode.Utils.wait
import org.json.JSONObject

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Vision Tele")

class VisionPoll : LinearOpMode() {
    val l = Logger("VISION_TELE")
    val pidRotation: PIDConstants = getPIDConstantsFromFile("pid_rotation.json")
    val pidDrive: PIDConstants = getPIDConstantsFromFile("pid_drive.json")
    val wsTask = WebsocketTask(this)

    init {
        l.log("TeleOp initialized.")
    }

    override fun runOpMode() {
        val dt = DriveTrain(this, drivePIDConstants = pidDrive, rotationPIDConstants = pidRotation, wss = wsTask)
        val vision = Vision(this)
        vision.startVision()
//        vision.detectRobust(10,100)

        l.log("Waiting for start...")
        wsTask.start()
        waitForStart()

        l.log("Started.")
        while (opModeIsActive() && !isStopRequested) {
            val d = vision.detectRobust(5, 100)
            l.logData("detect", d)
            val message = JSONObject()
                    .put("variable", "detect")
                    .put("value", d)
            wsTask.server.broadcastData("telemetry", message)
            wait(300)
//            if (gamepad1.a) {
//                var d = -1
//                do {
//                    l.log("spinning..")
////                    dt.rotate(Direction.SPIN_CW, 10, 5)
//                    d = vision.detectRobust(5, 100)
////                    wait(300)
//                    l.logData("detect", d)
//                } while (true)
////                l.logData("detection", d)
//
//            }
        }
        wsTask.stopThread()
        if (vision.tfod != null) {
            vision.shutDown()
        }


        l.log("Stopped threads")
    }
}