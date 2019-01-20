package org.firstinspires.ftc.teamcode.Tasks

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.Arms
import org.firstinspires.ftc.teamcode.Components.Hook
import org.firstinspires.ftc.teamcode.Models.Task

class HookTask(opMode: LinearOpMode): Task(opMode, "ARM_TASK"){
    private val hook = Hook(opMode)
    private var debounce: Long = 0

    override fun run(){
        runWhileActive(this::main)
    }

    fun main() {
        if (System.currentTimeMillis() > debounce) {
            if (opMode.gamepad1.x || opMode.gamepad2.x) {
                hook.unlatch()
                debounce = System.currentTimeMillis() + 200
            } else if (opMode.gamepad1.y || opMode.gamepad2.y) {
                hook.latch()
                debounce = System.currentTimeMillis() + 200
            }
        }
    }
}