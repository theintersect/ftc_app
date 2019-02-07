package org.firstinspires.ftc.teamcode.Components
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import com.qualcomm.robotcore.hardware.HardwareMap
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Utils.Logger


class Motor(val hardwareMap: HardwareMap,val motorName:String ){
    val motor = hardwareMap.dcMotor.get(motorName)
    val l = Logger("MOTOR", motorName)

    init {
        l.log("Motor created")
    }

    fun setPower(power:Double){
        motor.power = power
    }

    fun setDirection(direction: DcMotorSimple.Direction) {
        motor.direction = direction
    }

    fun useEncoder(){
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER
    }

    fun prepareEncoderDrive(){
        motor.mode=DcMotor.RunMode.STOP_AND_RESET_ENCODER
        useEncoder()
    }

    fun stop(coast:Boolean=false){
        if(coast){
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.FLOAT
        }else{
            motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE
        }
        motor.power = 0.0
    }

    fun setTargetPosition(target:Int){
        this.motor.targetPosition = target
    }

    fun moveTicks(direction:Direction, power:Double, ticks:Int, timeout:Int=5){
        val stopTime = System.currentTimeMillis() + timeout*1000
        motor.targetPosition = ticks + direction.intRepr * motor.currentPosition
        motor.mode = DcMotor.RunMode.RUN_TO_POSITION
        motor.power = Math.abs(power)
        l.logData("Target", motor.targetPosition)
        l.logData("Power", motor.power)
        while (motor.isBusy) {
            l.logData("Current position",motor.currentPosition)
            Thread.sleep(10)
            if(System.currentTimeMillis() > stopTime){
                l.log("Timed out")
                break
            }

        }
        stop()
        motor.mode = DcMotor.RunMode.RUN_USING_ENCODER

    }

    fun logInfo(){
        l.logData("Power",motor.power)
        l.logData("Position", motor.currentPosition)
        l.logData("Target", motor.targetPosition)
        l.logData("Mode", motor.mode.toString())

//        l.logData("Busy",motor.isBusy)
//        l.logData("Zero Power Behaviour",motor.zeroPowerBehavior.toString())
    }

}