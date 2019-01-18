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
        if (opMode.gamepad1.x) {
            arms.run(reverse = true)
        } else if (opMode.gamepad1.b) {
            arms.run(reverse = false)
        } else {
            arms.stop()
        }
    }

}