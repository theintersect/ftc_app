package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.Utils.Logger

class Sweeper(opMode: LinearOpMode): MotorGroup() {

    val hwMap = opMode.hardwareMap

    init {
        this.addMotor(Motor(hwMap, "sweeper"))
    }

}