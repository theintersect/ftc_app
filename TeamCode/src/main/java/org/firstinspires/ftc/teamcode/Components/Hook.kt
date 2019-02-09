package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.Components.Servo
import org.firstinspires.ftc.teamcode.Utils.Logger


class Hook(val opMode: LinearOpMode):Servo(opMode.hardwareMap,"hookServo"){

    val servoDirection = com.qualcomm.robotcore.hardware.Servo.Direction.FORWARD

    val latchPosition = 50
    val unlatchPosition = 0

    init {
        l.log("entered init")
        this.setDirection(servoDirection)
        this.latch()
        this.logInfo()
    }

    fun latch() {
        setPosition(latchPosition)
        l.log("latched")
    }

    fun unlatch() {
        setPosition(unlatchPosition)
        l.log("unlatched")
    }

}