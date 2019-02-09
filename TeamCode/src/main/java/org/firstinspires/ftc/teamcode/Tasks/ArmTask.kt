package org.firstinspires.ftc.teamcode.Tasks

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.Arms
import org.firstinspires.ftc.teamcode.Models.Task

class ArmTask(opMode: LinearOpMode): Task(opMode, "ARM_TASK"){
    private val arms = Arms(opMode)

    override fun run(){
        runWhileActive(this::main)
    }

    fun main(){
        if (opMode.gamepad1.dpad_up || opMode.gamepad2.dpad_up) {
            arms.run(reverse = true, power = 0.3)
        } else if (opMode.gamepad1.dpad_down || opMode.gamepad2.dpad_down) {
            arms.run(reverse = false, power = 0.3)
        } else {
            arms.stop()
        }
    }
}