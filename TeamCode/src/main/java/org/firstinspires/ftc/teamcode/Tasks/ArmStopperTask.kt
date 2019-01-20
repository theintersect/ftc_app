package org.firstinspires.ftc.teamcode.Tasks

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.ArmStoppers
import org.firstinspires.ftc.teamcode.Components.Dumper
import org.firstinspires.ftc.teamcode.Models.Task

class ArmStopperTask(opMode: LinearOpMode): Task(opMode, "ARM_LOCK_TASK"){
    private val stoppers = ArmStoppers(opMode)

    override fun run(){
        runWhileActive(this::main)
    }

    fun main(){
        if (opMode.gamepad1.dpad_left || opMode.gamepad2.dpad_left ) {
            stoppers.lock()
            Thread.sleep(250)

        } else if (opMode.gamepad1.dpad_right || opMode.gamepad2.dpad_right){
            stoppers.unlock()
            Thread.sleep(250)

        }
    }

}