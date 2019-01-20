package org.firstinspires.ftc.teamcode.Components

import android.graphics.Path
import com.qualcomm.robotcore.hardware.DcMotor
import org.firstinspires.ftc.teamcode.Components.Motor
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Utils.Logger

open class MotorGroup() {
    var motors: ArrayList<Motor> = arrayListOf()
    val l = Logger("MOTORGROUP")

    init {
        l.log("Motor group created")
    }


    fun addMotor(motor: Motor) {
        motors.add(motor)
    }

    fun removeMotor(motor: Motor) {
        motors.remove(motor)
    }

    fun useEncoders() {
        motors.forEach { it.useEncoder() }
    }

    fun setPower(power: Double) {
        motors.forEach { it.setPower(power) }
    }

    fun logInfo(){
        motors.forEach { it.logInfo() }
    }

    fun getAvgCurrentPositon(): Double {
        var total: Int = 0
        motors.forEach { total += it.motor.currentPosition }
        return (total.toDouble() / motors.size)
    }

    fun prepareEncoderDrive() {
        motors.forEach { it.prepareEncoderDrive() }
    }

    fun stop(coast: Boolean = false) {
        motors.forEach { it.stop(coast = coast) }
    }

    fun moveTicks(direction: Direction, power: Double, ticks: Int) {
        motors.forEach({ it.moveTicks(direction, power, ticks) })
    }

    fun setMode(mode: DcMotor.RunMode){
        motors.forEach({it.motor.mode = mode})
    }

}