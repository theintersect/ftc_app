package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Utils.Logger

class ArmStoppers(opMode: LinearOpMode){
    val rStopper = Servo(opMode.hardwareMap,"rArmStopper")
    val lStopper = Servo(opMode.hardwareMap,"lArmStopper")

    val lockPosition = 90
    val unlockPosition = 45

    val l = Logger("ARM_STOPPERS")

    init {

        l.log("entered init")
        lStopper.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE)
        rStopper.setDirection(com.qualcomm.robotcore.hardware.Servo.Direction.REVERSE)
        unlock()
    }

    fun lock() {
        lStopper.setPosition(25)
        rStopper.setPosition(90)

        l.log("latched")
    }

    fun unlock() {
        lStopper.setPosition(75)
        rStopper.setPosition(45)
        l.log("unlatched")
    }

}