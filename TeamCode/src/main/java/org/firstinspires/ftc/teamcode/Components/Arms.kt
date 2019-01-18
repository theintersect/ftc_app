package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class Arms(opMode:LinearOpMode):MotorGroup(){

    val hwMap = opMode.hardwareMap

    init{
        this.addMotor( Motor(hwMap,"lArm"))
        this.addMotor( Motor(hwMap,"rArm"))
        this.useEncoders()
    }

    fun run(reverse: Boolean = false) {
        if (reverse) {
            setPower(-1.0)
        } else {
            setPower(1.0)
        }
    }

}