package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.Field
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Tasks.WebsocketTask
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Absolute Positioning Test")

class AbsoluteTele : LinearOpMode() {
    val l: Logger = Logger("PID Test")
    val pidRotation: PIDConstants = getPIDConstantsFromFile("pid_rotation.json")
    val pidDrive: PIDConstants = getPIDConstantsFromFile("pid_drive.json")
    val wsTask = WebsocketTask(this)

    init {
        l.log("Initialized")
//        telemetry.addData("PID R",pidRotation.toString())
//        telemetry.addData("PID D",pidDrive.toString())

        l.log(pidRotation.toString())
        l.log(pidDrive.toString())

    }

    override fun runOpMode() {

        l.log("waiting for start")
        telemetry.update()
        wsTask.start()
        val dt = DriveTrain(this, drivePIDConstants = pidDrive, rotationPIDConstants = pidRotation, wss = wsTask)
//        val field = Field(dt)
        val field = Field(dt)
        l.log("initialized field")
        waitForStart()

        l.log("Opmode Started")
//        field.registerPosition(18.0, -18.0, 135.0)
//        field.registerPosition(0.0, 0.0, 0.0)

        while (opModeIsActive() && !isStopRequested) {
            if (gamepad1.a) {
                field
                        .initialize(18.0, -18.0, 135.0)
                        .moveTo(48.0, -24.0, Direction.BACKWARD)
                        .moveTo(56.0, -50.0, Direction.BACKWARD)
            } else if (gamepad1.b) {
                field
                        .initialize(0.0, 0.0, 0.0)
                        .moveTo(12.0, 0.0, Direction.BACKWARD)
                        .moveTo(0.0, 12.0, Direction.BACKWARD)
                        .moveTo(-12.0, 0.0, Direction.BACKWARD)
                        .moveTo(0.0, -12.0, Direction.BACKWARD)
                        .moveTo(0.0, 0.0, Direction.BACKWARD)
            } else if (gamepad1.x) {
                field
                        .initialize(0.0, 0.0, 0.0)
                        .moveTo(12.0, 0.0, Direction.FORWARD)
                        .moveTo(-12.0, 0.0, Direction.BACKWARD)
                        .moveTo(0.0, 12.0, Direction.FORWARD)
                        .moveTo(0.0, -12.0, Direction.BACKWARD)
                        .moveTo(0.0, 0.0, Direction.FORWARD)
            }
//            if (gamepad1.a) {
//                field.registerOffset(135.0)
//                field.registerPosition(18.0, -18.0, 0.0)
//
//                field.moveToDirection(48.0, -24.0, Direction.SPIN_CCW)
//                broadcastTelemetry(field)
//
//                field.moveToDirection(56.0, -50.0, Direction.SPIN_CW)
//                broadcastTelemetry(field)
//
////                field.moveTo(0.0, -12.0)
////                broadcastTelemetry(field)
////
////                field.moveTo(-12.0, 0.0)
////                broadcastTelemetry(field)
////
////                field.moveTo(0.0, 0.0)
//                broadcastTelemetry(field)
//            } else if (gamepad1.x) {
//                field.registerPosition(0.0, 0.0, 0.0)
//
////                field.moveTo(-12.0, 0.0)
//                field.moveTo(12.0, 0.0)
//                broadcastTelemetry(field)
//
//                field.moveTo(0.0, 12.0)
//                broadcastTelemetry(field)
//
//                field.moveTo(-12.0, 0.0)
//                broadcastTelemetry(field)
//
//                field.moveTo(0.0, -12.0)
//                broadcastTelemetry(field)
//
//
//            } else if (gamepad1.b) {
//                field.registerPosition(0.0, 0.0, 0.0)
//
//                field.moveTo(-12.0, 0.0)
//                broadcastTelemetry(field)
//
//                field.moveTo(0.0, -12.0)
//                broadcastTelemetry(field)
//
//                field.moveTo(12.0, 0.0)
//                broadcastTelemetry(field)
//
//                field.moveTo(0.0, 12.0)
//                broadcastTelemetry(field)
//
//            } else if (gamepad1.y) {
//                field.registerPosition(0.0, 0.0, 0.0)
//
//                field.moveTo(12.0, 0.0)
//                field.moveToDirection(0.0, 12.0, Direction.SPIN_CCW)
//                field.moveToDirection(-12.0, 0.0, Direction.SPIN_CCW)
//                field.moveToDirection(0.0, -12.0, Direction.SPIN_CW)
//                field.moveToDirection(0.0, 0.0, Direction.SPIN_CW)
//
//
////                field.moveTo(12.0, 0.0)
//                broadcastTelemetry(field)
//
//            }
        }
        wsTask.stopThread()


    }

//    fun broadcastTelemetry(field: Field) {
//        var message = JSONObject()
//                .put("variable", "x")
//                .put("value", field.currentX)
//        wsTask.server.broadcastData("telemetry", message)
//        message = JSONObject()
//                .put("variable", "y")
//                .put("value", field.currentY)
//        wsTask.server.broadcastData("telemetry", message)
//    }

}
