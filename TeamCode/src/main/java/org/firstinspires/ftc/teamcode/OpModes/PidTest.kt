package org.firstinspires.ftc.teamcode.OpModes

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.WSS
import org.firstinspires.ftc.teamcode.Utils.getPIDConstantsFromFile
import org.firstinspires.ftc.teamcode.Utils.wait

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "PID Test")

class  PidTest: LinearOpMode(){
    val l: Logger = Logger("PID Test")
    val pidRotation: PIDConstants = getPIDConstantsFromFile("pid_rotation.json")
    val pidDrive: PIDConstants = getPIDConstantsFromFile("pid_drive.json")

    init{
        l.log("Initialized")
//        telemetry.addData("PID R",pidRotation.toString())
//        telemetry.addData("PID D",pidDrive.toString())

        l.log(pidRotation.toString())
        l.log(pidDrive.toString())

    }


    override fun runOpMode() {

        l.log("waiting for start")
        telemetry.update()
        val dt = DriveTrain(this,drivePIDConstants = pidDrive, rotationPIDConstants = pidRotation)
        waitForStart()

        l.log("Opmode Started")
        if(opModeIsActive() && !isStopRequested){
            l.log("Starting turn")
            dt.drive(Direction.FORWARD,15.0,10)

//            dt.rotate(Direction.SPIN_CCW,10,10,broadcast=false)
//            wait(mill = 1500)
//            dt.rotate(Direction.SPIN_CW,10,10,broadcast=false)
//            wait(mill = 1500)
//
//            dt.rotate(Direction.SPIN_CCW,45,10,broadcast=false)
//            wait(mill = 1500)
//
//            dt.rotate(Direction.SPIN_CW,45,10,broadcast=false)
//            wait(mill = 1500)
//
//
//            dt.rotate(Direction.SPIN_CCW,90,10,broadcast=false)
//            wait(mill = 1500)
//
//            dt.rotate(Direction.SPIN_CW,90,10,broadcast=false)
//            wait(mill = 1500)
//
//
//            dt.rotate(Direction.SPIN_CCW,150,10,broadcast=false)
//            wait(mill = 1500)
//
//            dt.rotate(Direction.SPIN_CW,150,10,broadcast=false)

            l.log("Finished turn")
        }

    }

}
