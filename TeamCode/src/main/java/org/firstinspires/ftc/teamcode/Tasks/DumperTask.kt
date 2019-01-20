package org.firstinspires.ftc.teamcode.Tasks

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.Dumper
import org.firstinspires.ftc.teamcode.Models.Task

class DumperTask(opMode: LinearOpMode): Task(opMode, "DUMPER_TASK"){
    private val dumper = Dumper(opMode, "dumper")
    init {
        dumper.setNormalPosition()
    }
    override fun run(){
        runWhileActive(this::main)
    }

    fun main(){

        if (opMode.gamepad1.left_bumper || opMode.gamepad2.left_bumper) {
            dumper.setDumpPosition()
        } else{
            dumper.setNormalPosition()
        }

    }

}