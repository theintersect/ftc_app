package org.firstinspires.ftc.teamcode.Components


import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Utils.Logger



open class Servo(val hardwareMap: HardwareMap, val name:String){
    val servo = hardwareMap.servo.get(name)
    val l = Logger("SERVO", name)
    val POSITION_TO_ANGLE = 180.0; // use angle to decide position
    var INIT_ANGLE = 90
    var MAX_POSITION = 180
    var MIN_ANGLE = 0
    var POSITION_BLOCKING_THRESHOLD = 0.01

    fun setPosition(angle:Int,blockUntilFinished:Boolean=false){
        val targetPosition = angle/POSITION_TO_ANGLE
//        l.logData("POSITION", targetPosition)
        servo.position = targetPosition
        if(blockUntilFinished){
//            while(Math.abs(servo.position-targetPosition) < POSITION_BLOCKING_THRESHOLD ){
//                l.log("Blocking until servo rotation complete.")
//                l.logData("Target Pos",targetPosition)
//                l.logData("Target Angle",angle)
//                logInfo()
//            }
        }
    }

    fun setMaxAngle(blockUntilFinished:Boolean=false){
        setPosition(MAX_POSITION,blockUntilFinished)
    }

    fun setMinAngle(blockUntilFinished:Boolean=false){
        setPosition(MIN_ANGLE,blockUntilFinished)
    }

    fun setInitAngle(blockUntilFinished:Boolean=false){
        setPosition(INIT_ANGLE,blockUntilFinished)
    }

    fun setDirection(direction:com.qualcomm.robotcore.hardware.Servo.Direction){
        servo.direction = direction
    }

    fun logInfo(){
        l.logData("Position",servo.position)
        l.logData("Angle",servo.position*POSITION_TO_ANGLE)
        l.logData("Direction",servo.direction.toString())
    }

}