package org.firstinspires.ftc.teamcode.Tasks

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.Arms
import org.firstinspires.ftc.teamcode.Components.Sweeper
import org.firstinspires.ftc.teamcode.Models.Task

class SweeperTask(opMode: LinearOpMode): Task(opMode, "ARM_TASK"){
    private val sweeper = Sweeper(opMode)

    override fun run(){
        runWhileActive(this::main)
    }

    fun main(){
        if (opMode.gamepad1.a || opMode.gamepad2.a) {
            sweeper.setPower(1.0)
        } else if (opMode.gamepad1.b || opMode.gamepad2.b) {
            sweeper.setPower(-1.0)
        } else {
            sweeper.stop()
        }
    }

}