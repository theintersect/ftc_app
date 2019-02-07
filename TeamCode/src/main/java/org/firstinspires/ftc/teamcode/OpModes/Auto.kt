package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.*
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.Field
import org.firstinspires.ftc.teamcode.Models.Coordinate

import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Tasks.WebsocketTask
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.Vision

import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto")

class  Auto: LinearOpMode(){
    val l: Logger = Logger("AUTO")
    val pidRotation: PIDConstants = getPIDConstantsFromFile("pid_rotation.json")
    val pidDrive: PIDConstants = getPIDConstantsFromFile("pid_drive.json")
    var dt:DriveTrain? = null
    var vision: Vision? = null
    val wsTask = WebsocketTask(this)


    init{
        l.log("Initialized")

    }


    override fun runOpMode() {
        wsTask.start()
        telemetry.update()
        dt = DriveTrain(this,drivePIDConstants = pidDrive, rotationPIDConstants = pidRotation, wss = wsTask)
        val arms = Arms(this)
        val armStoppers = ArmStoppers(this)
        val sweeper = Sweeper(this)
        val hook = Hook(this)
        val dumper = Dumper(this,"dumper")
        val field = Field(dt!!)
        field.initialize(18.0, -18.0, 135.0)
        dumper.setNormalPosition()
        hook.latch()
        armStoppers.lock()
        arms.setPower(0.0)

        vision = Vision(this)
        vision!!.startVision()
        l.log("Init fininshed, waiting for start..")

        waitForStart()

        l.log("Opmode Started")
        if(opModeIsActive() && !isStopRequested){
            val position = threePointGold()
            val positionIndex = position - 1
            l.logData("Detected position",position)

            val goldPositions = arrayOf(
                    Coordinate(48, -24), //position 1 (x,y)
                    Coordinate(36, -36), //position 2 (x,y)
                    Coordinate(24, -48)  //position 3 (x,y)
            )
            val directions = arrayOf(
                    Direction.BACKWARD,
                    Direction.BACKWARD,
                    Direction.BACKWARD
            )
            val directionsSecondDrive = arrayOf(
                    Direction.BACKWARD,
                    Direction.BACKWARD,
                    Direction.BACKWARD
            )

            val markerDumperCoordinate =      Coordinate(56,-56)
            val avoidanceWaypointCoordinate = Coordinate(24,-66)
            val craterCoordinate =            Coordinate(-35,-64)

            field
                .moveTo(goldPositions[positionIndex], directions[positionIndex])
                .moveTo(markerDumperCoordinate, directionsSecondDrive[positionIndex])

            dt!!.rotateTo(-45.0)
            dumper.setDumpPosition()
            sleep(800)
            dumper.setNormalPosition()
            sleep(600)
            dumper.setDumpPosition()

            field
                .moveTo(avoidanceWaypointCoordinate,Direction.FORWARD)
                .moveTo(craterCoordinate,Direction.FORWARD)

//            armStoppers.unlock()
//            wait(1000)
//            arms.moveDegrees(Direction.SPIN_CCW, 0.1, -5)
//            l.log("0")
//            wait(1000)
//            hook.unlatch()
//            dt!!.drive(Direction.FORWARD,44.0,10)
//            l.log("1")
//            wait(500)


        }
        wsTask.stopThread()

    }

    fun threePointGold(): Int {
        val angles: Array<Double> = arrayOf(-19.0, 10.0, 35.0)
        for (i in 1..3) {
            dt!!.rotateTo(angles[i-1], 3)
            val d = vision!!.detectRobust(5, 100)
            if (d > 0) {
                vision!!.shutDown()
                l.logData("MINERAL DETECTED at position", i)
                return i
            }
        }
        vision!!.shutDown()
        l.log("DID NOT DETECT")
        return 2 //return middle position when failed to detect
    }

    fun alignToGold():Boolean{
        var d = vision!!.detectRobust(5, 100)
        val maxRotations = 10
        val threshold = 450
        for (i in 1..maxRotations) {
            l.log("spinning..")
            dt!!.rotate(Direction.SPIN_CW, 10, 5)
            d = vision!!.detectRobust(5,100)
            l.logData("detect",d)
            if (d > threshold) {
                vision!!.shutDown()
                l.logData("MINERAL DETECTED", d)
                return true
            }
        }
        vision!!.shutDown()
        l.logData("DID NOT DETECT", d)
        return false
//        val vision = Vision(this)
//        val maxRotations = 10
//        val centerPosition = 500
//        val pixelThreshold = 50
//        var detection = -1
//        for(i in 1..maxRotations){
//            dt!!.rotate(Direction.SPIN_CW,5,3)
//            detection = vision.detectRobust(10,1)
//            l.logData("detection",detection)
//            if(Math.abs( detection- centerPosition) < pixelThreshold){
//                l.log("Detected.")
//                return true
//            }
//        }
//        return false
    }

}
