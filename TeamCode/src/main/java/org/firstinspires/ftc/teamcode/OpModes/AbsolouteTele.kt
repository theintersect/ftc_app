package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.Field
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Tasks.WebsocketTask
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.WSS
import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile
import org.firstinspires.ftc.teamcode.Utils.wait
import org.json.JSONObject

@com.qualcomm.robotcore.eventloop.opmode.TeleOp(name = "Absoloute Positioning Test")

class AbsolouteTele : LinearOpMode() {
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
        val field = Field(dt)
        waitForStart()

        l.log("Opmode Started")
        field.registerPosition(0.0, 0.0, 0.0)

        while (opModeIsActive() && !isStopRequested) {
            if (gamepad1.a) {
                field.moveTo(0.0, 12.0)
                broadcastTelemetry(field)
            } else if (gamepad1.x) {
                field.moveTo(-12.0, 0.0)
                broadcastTelemetry(field)

            } else if (gamepad1.b) {
                field.moveTo(0.0, -12.0)
                broadcastTelemetry(field)

            } else if (gamepad1.y) {
                field.moveTo(12.0, 0.0)
                broadcastTelemetry(field)

            }
        }
        wsTask.stopThread()


    }

    fun broadcastTelemetry(field: Field) {
        var message = JSONObject()
                .put("variable", "x")
                .put("value", field.currentX)
        wsTask.server.broadcastData("telemetry", message)
        message = JSONObject()
                .put("variable", "y")
                .put("value", field.currentY)
        wsTask.server.broadcastData("telemetry", message)
    }

}
