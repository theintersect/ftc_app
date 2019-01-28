package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Tasks.*
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile
import org.firstinspires.ftc.teamcode.Utils.wait

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "PID TeleOp")

class PidTele: LinearOpMode() {
    val l = Logger("PID_TELEOP")
    val pidRotation: PIDConstants = getPIDConstantsFromFile("pid_rotation.json")
    val pidDrive: PIDConstants = getPIDConstantsFromFile("pid_drive.json")
    val wsTask = WebsocketTask(this)

    init {
        l.log("TeleOp initialized.")
    }

    override fun runOpMode() {
        val dt = DriveTrain(this,drivePIDConstants = pidDrive, rotationPIDConstants = pidRotation, wss=wsTask)

        l.log("Waiting for start...")
        wsTask.start()
        waitForStart()

        l.log("Started.")
        while (opModeIsActive() && !isStopRequested) {
            if (gamepad1.a) {
                l.log("doing shit")
                dt.rotate(Direction.SPIN_CCW,10,10,broadcast=true)
            } else if (gamepad1.b) {
                dt.rotate(Direction.SPIN_CW,10,10,broadcast=true)
            } else if (gamepad1.x) {
                dt.rotate(Direction.SPIN_CCW,45,10,broadcast=true)
            } else if (gamepad1.y) {
                dt.rotate(Direction.SPIN_CW,45,10,broadcast=true)
            } else if (gamepad1.dpad_up) {
                dt.rotate(Direction.SPIN_CCW,90,10,broadcast=true)
            } else if (gamepad1.dpad_right) {
                dt.rotate(Direction.SPIN_CW,90,10,broadcast=true)
            } else if (gamepad1.dpad_down) {
                dt.rotate(Direction.SPIN_CCW,150,10,broadcast=true)
            } else if (gamepad1.dpad_left) {
                dt.rotate(Direction.SPIN_CW,150,10,broadcast=true)
            }
        }
        wsTask.stopThread()

        l.log("Stopped threads")
    }
}