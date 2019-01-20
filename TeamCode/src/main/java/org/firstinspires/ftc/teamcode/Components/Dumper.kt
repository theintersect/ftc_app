package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode

class Dumper(opMode: LinearOpMode, name: String): Servo(opMode.hardwareMap, name) {

    val servoDirection = com.qualcomm.robotcore.hardware.Servo.Direction.FORWARD
    val lockPosition: Int = 0
    val unlockPosition: Int = 50

    init {

        l.log("entered init")
        this.setDirection(servoDirection)

    }

    fun setDumpPosition() {
        setPosition(lockPosition)
        l.log("latched")
    }

    fun setNormalPosition() {
        setPosition(unlockPosition)
//        l.log("unlatched")
    }

}