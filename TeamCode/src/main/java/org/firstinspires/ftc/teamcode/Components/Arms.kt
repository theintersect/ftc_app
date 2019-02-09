package org.firstinspires.ftc.teamcode.Components

import android.graphics.Path
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Utils.wait

class Arms(val opMode:LinearOpMode):MotorGroup(){

    val hwMap = opMode.hardwareMap
    val DEGREES_TO_TICKS = 1680.0*4.0/360.0
    val rArm = Motor(hwMap,"rArm")
    val lArm = Motor(hwMap, "lArm")


    init{
        rArm.setDirection(DcMotorSimple.Direction.REVERSE)
        this.addMotor(lArm)
        this.addMotor(rArm)
        this.useEncoders()
        this.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE)
    }

    fun run(reverse: Boolean = false, power: Double = 1.0) {
        if (reverse) {
            setPower(-power)
        } else {
            setPower(power)
        }
    }

    fun moveDegrees(direction: Direction, power:Double, degrees:Int){
        val ticks = degrees * DEGREES_TO_TICKS
        lArm.setTargetPosition((lArm.motor.currentPosition + ticks).toInt())
        rArm.setTargetPosition((rArm.motor.currentPosition + ticks).toInt())

        this.setMode(DcMotor.RunMode.RUN_TO_POSITION)
        setPower(Math.abs(power))

        while (lArm.motor.isBusy && rArm.motor.isBusy && !opMode.isStopRequested && opMode.opModeIsActive()) {
            wait(10)
            lArm.logInfo()
        }

        stop()

        this.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

//        this.moveTicks()
    }

}