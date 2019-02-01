package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.hardware.bosch.BNO055IMU
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotor
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.Models.Direction
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Tasks.WebsocketTask
import org.firstinspires.ftc.teamcode.Utils.*


class DriveTrain(val opMode: LinearOpMode, val wss: WebsocketTask? = null, val rotationPIDConstants: PIDConstants = PIDConstants(1.0, 1.0, 1.0), val drivePIDConstants: PIDConstants = PIDConstants(1.0, 1.0, 1.0)) {
    val l: Logger = Logger("DRIVETRAIN2")
    val lfDrive = Motor(opMode.hardwareMap, "lfDrive")
    val lbDrive = Motor(opMode.hardwareMap, "lbDrive")
    val rfDrive = Motor(opMode.hardwareMap, "rfDrive")
    val rbDrive = Motor(opMode.hardwareMap, "rbDrive")
    val driveMotors = MotorGroup()
    val leftMotors = MotorGroup()
    val rightMotors = MotorGroup()

    val imu: IMU = IMU(opMode.hardwareMap.get(BNO055IMU::class.java, "imu"))
    val MAX_POWER = 0.5


    init {
        l.log("entered init")
        lfDrive.setDirection(DcMotorSimple.Direction.REVERSE)
        lbDrive.setDirection(DcMotorSimple.Direction.REVERSE)
        rfDrive.setDirection(DcMotorSimple.Direction.FORWARD)
        rbDrive.setDirection(DcMotorSimple.Direction.FORWARD)
        driveMotors.addMotor(lfDrive)
        driveMotors.addMotor(rfDrive)
        driveMotors.addMotor(lbDrive)
        driveMotors.addMotor(rbDrive)

        leftMotors.addMotor(lbDrive)
        leftMotors.addMotor(lfDrive)
        rightMotors.addMotor(rbDrive)
        rightMotors.addMotor(rfDrive)

        driveMotors.useEncoders()
        driveMotors.motors.forEach({ it.motor.zeroPowerBehavior = DcMotor.ZeroPowerBehavior.BRAKE })

        l.log("Initialized motors")
        l.log("Websocket initialized: ${wss != null}")
        l.log("Rotation PIO: ${rotationPIDConstants}")
        l.log("Drive PIO: ${drivePIDConstants}")
//        initMotors()
    }

    fun normalizePower(input: Double): Double {
//        if (input < 0) {
//            return Math.max(input, -MAX_POWER)
//        } else {
//            return Math.min(input, MAX_POWER)
//        }

        return input
    }

    fun setPowers(lPower: Double, rPower: Double) {
//        rfDrive.setPower(lPower)
        l.logData("lPow", lPower)
        l.logData("rPow", rPower)

        leftMotors.setPower(normalizePower(lPower))
        rightMotors.setPower(normalizePower(rPower))
    }

    fun stopAll(coast: Boolean = false) {
        leftMotors.stop(coast)
        rightMotors.stop(coast)
    }

    fun move(dir: Direction, power: Double) {
        when (dir) {
            Direction.FORWARD -> {
                setPowers(power, power)
            }
            Direction.BACKWARD -> {
                setPowers(-power, -power)
            }
            Direction.SPIN_CW -> {
                setPowers(-power, power)
            }
            Direction.SPIN_CCW -> {
                setPowers(power, -power)
            }
            Direction.FORWARD_CCW -> {
                setPowers(0.0, power)
            }
            Direction.FORWARD_CW -> {
                setPowers(power, 0.0)
            }
            Direction.BACKWARD_CCW -> {
                setPowers(0.0, -power)
            }
            Direction.BACKWARD_CW -> {
                setPowers(-power, 0.0)
            }
        }
    }

    fun drive(dir: Direction, dist: Double, timeout: Int = 10) {
        driveMotors.prepareEncoderDrive()

        val ticks = (dir.intRepr * Values.TICKS_PER_INCH_FORWARD * dist).toInt()
        l.logData("tix", ticks)
        lfDrive.setTargetPosition(lfDrive.motor.currentPosition + ticks)
        rfDrive.setTargetPosition(rfDrive.motor.currentPosition + ticks)
        lbDrive.setTargetPosition(lbDrive.motor.currentPosition + ticks)
        rbDrive.setTargetPosition(rbDrive.motor.currentPosition + ticks)

        driveMotors.setMode(DcMotor.RunMode.RUN_TO_POSITION)

        lfDrive.setPower(Math.abs(.2))
        rfDrive.setPower(Math.abs(.2))
        lbDrive.setPower(Math.abs(.2))
        rbDrive.setPower(Math.abs(.2))

        val startTime = System.currentTimeMillis()
        while (lfDrive.motor.isBusy && rfDrive.motor.isBusy &&
                lbDrive.motor.isBusy && rbDrive.motor.isBusy &&
                System.currentTimeMillis() < startTime + timeout * 1000) {
            wait(10)
            driveMotors.logInfo()
        }

        stopAll()

        driveMotors.setMode(DcMotor.RunMode.RUN_USING_ENCODER)

    }

    fun drivePID(dir: Direction, dist: Double, timeout: Int = 10) {
        driveMotors.prepareEncoderDrive()
        val leftPos = leftMotors.getAvgCurrentPositon()
        val rightPos = rightMotors.getAvgCurrentPositon()

        val lTarget = leftPos +
                dir.intRepr * Values.TICKS_PER_INCH_FORWARD * dist
        val rTarget = rightPos +
                dir.intRepr * Values.TICKS_PER_INCH_FORWARD * dist
        val minError = 50

        val constants = getPIDConstantsFromFile("pid_drive.json")

        val lPID = PIDController(constants, lTarget, broadcast = true, wss = wss)
        val rPID = PIDController(constants, rTarget, broadcast = true, wss = wss)

        lPID.initController(leftPos)
        rPID.initController(rightPos)

        do {
            setPowers(
                    lPID.output(leftMotors.getAvgCurrentPositon()),
                    rPID.output(rightMotors.getAvgCurrentPositon())
            )
        } while (opMode.opModeIsActive() && !opMode.isStopRequested &&
                Math.abs(lPID.prevError!!) > minError && Math.abs(rPID.prevError!!) > minError)

        stopAll()
    }

    fun rotate(dir: Direction, angle: Int, timeout: Int = 10, broadcast: Boolean = false) {
        val targetHeading = fixAngle(imu.angle + dir.intRepr * angle)
        rotateTo(targetHeading, timeout, broadcast = broadcast)
    }

    fun fixAngle(angle: Double): Double {
        var fixedAngle = angle
        while (fixedAngle > 180 || fixedAngle < -180) {
            if (fixedAngle > 180) {
                fixedAngle -= 360
            } else {
                fixedAngle += 360
            }
        }
        return fixedAngle
    }

    fun rotateTo(targetHeading: Double, timeout: Int = 10, broadcast: Boolean = false) {
        val minError = 2
//        val constants = getPIDConstantsFromFile("pid_rotation.json")

        var currentHeading: Double = imu.angle
        var error = fixAngle(targetHeading - currentHeading)

        val constants: PIDConstants = if (Math.abs(error) < 20) {
            getPIDConstantsFromFile("pid_10.json")
        } else if (Math.abs(error) < 60) {
            getPIDConstantsFromFile("pid_45.json")
        } else if (Math.abs(error) < 120) {
            getPIDConstantsFromFile("pid_90.json")
        } else {
            getPIDConstantsFromFile("pid_180.json")
        }


        //todo: remove
//        val pid = PIDController(rotationPIDConstants, targetHeading, broadcast = broadcast, wss = wss)
        val pid = PIDController(constants, targetHeading, broadcast = broadcast, wss = wss)


//        val dir = if (error > 0) {
//            Direction.SPIN_CW
//        } else {
//            Direction.SPIN_CCW
//        }
        val dir = Direction.SPIN_CCW

        pid.initController(currentHeading)
        do {
            currentHeading = imu.getAngle()
            error = fixAngle(targetHeading - currentHeading)

            var proportionalPower = pid.output(currentHeading, this::fixAngle)


//            proportionalPower = if (proportionalPower > 0) {
//                Math.max(proportionalPower + 0.1, minPower)
//            } else {
//                Math.min(proportionalPower - 0.1, -minPower)
//            }
            l.logData("Direction", dir.toString())
            move(dir, proportionalPower)

//            if (dir == Direction.SPIN_CW && error < minError) {
//                break
//            } else if (dir == Direction.SPIN_CCW && error > -minError) {
//                break
//            }

            if (Math.abs(pid.prevError!!) < minError) {
                l.log("Within minError! Waiting...")
                stopAll()
                opMode.sleep(300)
                currentHeading = imu.getAngle()
                pid.output(currentHeading, this::fixAngle)
            }
        } while (opMode.opModeIsActive() && !opMode.isStopRequested && Math.abs(pid.prevError!!) > minError)
        stopAll()
    }

}