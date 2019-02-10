package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.robotcore.external.Telemetry
import org.firstinspires.ftc.teamcode.Components.*
import org.firstinspires.ftc.teamcode.Models.*

import org.firstinspires.ftc.teamcode.Tasks.WebsocketTask
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.Vision

import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Auto Crater")

class  AutoCrater: LinearOpMode(){
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
        telemetry.addLine("starting init...")
        telemetry.update()
        wsTask.start()

        dt = DriveTrain(this,drivePIDConstants = pidDrive, rotationPIDConstants = pidRotation, wss = wsTask)

        val arms = Arms(this)
        val armStoppers = ArmStoppers(this)
        val sweeper = Sweeper(this)
        val hook = Hook(this)
        val dumper = Dumper(this,"dumper")
        val field = Field(dt!!)


        dumper.setNormalPosition()
        hook.latch()
        armStoppers.lock()
        arms.setPower(0.0)

        field.initialize(-18.0, -18.0, 45.0)



        vision = Vision(this)
        vision!!.startVision()

        l.log("Init fininshed, waiting for start..")

        telemetry.addLine("INITIALIZATION FINISHED")
        telemetry.update()
        while(!gamepad1.start && !opModeIsActive() && !isStopRequested){
            telemetry.addLine("NOT ARMED")
            telemetry.update()
        }

        vision = Vision(this)
        vision!!.startVision()


        telemetry.addLine("!!!! ARMED - WAITING FOR START !!!!")
        telemetry.update()

        waitForStart()

        l.log("Opmode Started")
        if(opModeIsActive() && !isStopRequested){
            //drop from lander
            armStoppers.unlock()
            arms.moveDegrees(Direction.SPIN_CCW, 0.4, -100)
            hook.unlatch()
            sleep(100)

            arms.moveDegrees(Direction.SPIN_CCW, 0.15, 25)


            val position = threePointGold()
            val positionIndex = position - 1
            l.logData("Detected position",position)

            val goldPositions = arrayOf(
                    Coordinate(-24, -48), //position 1 (x,y)
                    Coordinate(-36, -36), //position 2 (x,y)
                    Coordinate(-48, -24)  //position 3 (x,y)
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
            val avoidanceWaypointCoordinate  =Coordinate(-12,-66)

            val markerDumperCoordinate =  Coordinate(54,-54)

            field
                    .moveTo(goldPositions[positionIndex], Direction.BACKWARD)
                    .moveTo(Coordinate(-18, -18), Direction.FORWARD)
                    .moveTo(avoidanceWaypointCoordinate, Direction.BACKWARD)
                    .moveTo(markerDumperCoordinate, Direction.BACKWARD)

            dumper.setDumpPosition()
            sleep(600)
            dumper.setNormalPosition()
            sleep(600)
            dumper.setDumpPosition()

//            val avoidanceWaypointCoordinate = Coordinate(66,-24)
//            val craterCoordinate =            Coordinate(64,35)

            val craterCoordinate  = Coordinate(-66,-35)
            field.moveTo(avoidanceWaypointCoordinate,Direction.FORWARD)



        }
        if(vision != null) {
            vision!!.shutDown()
        }
        wsTask.stopThread()

    }
    fun waitForButton(){
        l.log("Waiting for button...")
        while(!gamepad1.x && !isStopRequested && opModeIsActive()){
            sleep(100)
        }
        l.log("button pressed!")
    }
    fun threePointGold(): Int {
        val angles: Array<Double> = arrayOf(-23.0, 10.0, 39.0)
        for (i in 1..3) {
            dt!!.rotateTo(angles[i-1], 3)
            val d = vision!!.detectRobust(5, 50)
            if (d > 0) {
//                vision!!.shutDown()
                l.logData("MINERAL DETECTED at position", i)
                return i
            }
        }
//        vision!!.shutDown()
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
