package org.firstinspires.ftc.teamcode.Components

import org.firstinspires.ftc.teamcode.Components.Motor
import org.firstinspires.ftc.teamcode.Utils.Logger

public class MotorGroup(){
    var motors:ArrayList<Motor> = arrayListOf()
    val l = Logger("MOTORGROUP")

    init {
        l.log("Motor group created")
    }


    fun addMotor(motor: Motor){
        motors.add(motor)
    }

    fun removeMotor(motor: Motor){
        motors.remove(motor)
    }

    fun useEncoders(){
        motors.forEach { it.useEncoder() }
    }

    fun setPower(power:Double){
        motors.forEach { it.setPower(power) }
    }
//
//    fun logInfo(){
//        motors.forEach { logInfo() }
//    }

    fun getAvgCurrentPositon():Double{
        var total:Int = 0
        motors.forEach { total += it.motor.currentPosition}
        return (total.toDouble()/motors.size)
    }
    fun prepareEncoderDrive(){
        motors.forEach { it.prepareEncoderDrive() }
    }

    fun stop(coast:Boolean=false){
        motors.forEach { it.stop(coast=coast) }
    }
}