package org.firstinspires.ftc.teamcode.Tasks

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Models.Task
import org.firstinspires.ftc.teamcode.Utils.Logger

class DriveTrainTask(opMode:LinearOpMode): Task(opMode, "DRIVETRAIN_TASK"){
    var dt = DriveTrain(opMode)
    val l = Logger("DT_TASK")
    init{
    }

    override fun run(){
        runWhileActive(this::main)
    }

    fun joyToPower(joy:Double):Double{
        return(Math.pow(joy,3.0)/2)
    }
    fun main(){
        val leftStick = - opMode.gamepad1.left_stick_y.toDouble();
        val rightStick = - opMode.gamepad1.right_stick_y.toDouble();

        if (leftStick != 0.0 || rightStick != 0.0) {
            l.logData("leftStick", leftStick)
            l.logData("rightStick", rightStick)

        }

        dt.setPowers(
                joyToPower(leftStick),
                joyToPower(rightStick)
        );
        dt.driveMotors.logInfo()
    }

}