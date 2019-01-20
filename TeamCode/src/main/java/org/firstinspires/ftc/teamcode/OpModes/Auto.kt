package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.*
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.Vision

import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile
import org.firstinspires.ftc.teamcode.Utils.wait

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto")

class  Auto: LinearOpMode(){
    val l: Logger = Logger("AUTO")
    val pidRotation: PIDConstants = getPIDConstantsFromFile("pid_rotation.json")
    val pidDrive: PIDConstants = getPIDConstantsFromFile("pid_drive.json")
    var dt:DriveTrain? = null


    init{
        l.log("Initialized")

    }


    override fun runOpMode() {

        l.log("waiting for start")
        telemetry.update()
        dt = DriveTrain(this,drivePIDConstants = pidDrive, rotationPIDConstants = pidRotation)
        val arms = Arms(this)
        val armStoppers = ArmStoppers(this)
        val sweeper = Sweeper(this)
        val hook = Hook(this)
        val dumper = Dumper(this,"dumper")
        dumper.setNormalPosition()
        hook.latch()
        armStoppers.lock()

        waitForStart()

        l.log("Opmode Started")
        if(opModeIsActive() && !isStopRequested){
            armStoppers.unlock()
            wait(1000)
            arms.moveDegrees(Direction.SPIN_CCW, 0.1, -5)
            l.log("0")
            wait(1000)
            hook.unlatch()
            dt!!.drive(Direction.FORWARD,44.0,10)
            l.log("1")
            wait(500)
            dt!!.rotate(Direction.SPIN_CCW,40,10)
            l.log("2")
            wait(500)
            dt!!.drive(Direction.FORWARD,5.0,10)
            l.log("3")
            wait(500)
            dumper.setDumpPosition()
            l.log("4")
            wait(500)
            dumper.setNormalPosition()
            wait(500)
            dumper.setDumpPosition()
            l.log("4")
            wait(500)
            dumper.setNormalPosition()
            l.log("5")
            wait(500)
            dt!!.drive(Direction.BACKWARD, 61.0, 10)
            arms.moveDegrees(Direction.SPIN_CCW, 0.1, -90)

//            alignToGold()
        }

    }

    fun alignToGold():Boolean{
        val vision = Vision(this)
        val maxRotations = 10
        val centerPosition = 500
        val pixelThreshold = 50
        var detection = -1
        for(i in 1..maxRotations){
            dt!!.rotate(Direction.SPIN_CW,5,3)
            detection = vision.detectRobust(10,1)
            l.logData("detection",detection)
            if(Math.abs( detection- centerPosition) < pixelThreshold){
                l.log("Detected.")
                return true
            }
        }
        return false
    }

}
