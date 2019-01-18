package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class Arms(val opMode:LinearOpMode):MotorGroup(){

    val hwMap = opMode.hardwareMap

    init{
        this.addMotor( Motor(hwMap,"lArm"))
        this.addMotor( Motor(hwMap,"rArm"))
        this.useEncoders()
    }

}