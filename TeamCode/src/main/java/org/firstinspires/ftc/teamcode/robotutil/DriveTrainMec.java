package org.firstinspires.ftc.teamcode.robotutil;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

//import com.qualcomm.hardware.adafruit.JustLoggingAccelerationIntegrator;

public class DriveTrainMec {
    private ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);
    private DcMotor lDrive,rDrive;
    private IMUNew imu;
    private LinearOpMode opMode;
    private DcMotor[] driveMotors;

    public DriveTrainMec(LinearOpMode opMode) {
        this.opMode = opMode;
        lDrive = opMode.hardwareMap.dcMotor.get("lbDrive");
        rDrive = opMode.hardwareMap.dcMotor.get("lfDrive");
        lDrive = opMode.hardwareMap.dcMotor.get("rbrive");
        rDrive = opMode.hardwareMap.dcMotor.get("rfDrive");

        rDrive.setDirection(DcMotorSimple.Direction.REVERSE);
        lDrive.setDirection(DcMotorSimple.Direction.REVERSE);

        this.imu = new IMUNew("imu",this.opMode);

        this.driveMotors = new DcMotor[]{lDrive,rDrive};

        initMotors();
        this.imu.init();
    }

    public void initMotors(){
        Motors.useEncoders(this.driveMotors);
        Motors.resetEncoders(this.driveMotors);
        Motors.setBrake(this.driveMotors);
    }

    public void move(Direction direction,double power){
        power = Math.abs(power);
        if(direction == Direction.BACK){
            power = -power;
        }
        setPowers(power,power);
    }



    public void rotate(Direction direction,RotateMethod method,double power){
        power = Math.abs(power);
        double rPower = 0;
        double lPower = 0;
        if(method == RotateMethod.SPIN){
            if(direction == Direction.CW){
                rPower = -power;
                lPower = power;
            } else if(direction == Direction.CCW){
                lPower = -power;
                rPower = power;

            }
        }else if (method == RotateMethod.BACKWARD){
            if(direction == Direction.CW){
                rPower = -power;
            } else if(direction == Direction.CCW){
                lPower = -power;
            }
        }else if (method == RotateMethod.FORWARD){
            if(direction == Direction.CW){
                lPower = power;
            } else if(direction == Direction.CCW){
                rPower = power;
            }
        }
        setPowers(lPower,rPower);
    }

    public void setPowers(double lPower, double rPower){
        lDrive.setPower(lPower);
        rDrive.setPower(rPower);
    }


    public void move(Direction direction,double power,double inches,double timeoutS) {
        Motors.resetEncoders(driveMotors);
        Motors.useEncoders(driveMotors);
        int rTarget;
        int lTarget;
        switch (direction) {
            case FORWARD:
                rTarget = rDrive.getCurrentPosition() + (int) (inches * Values.ticksPerInch);
                lTarget = lDrive.getCurrentPosition() + (int) (inches * Values.ticksPerInch);
                break;
            case BACK:
                rTarget = rDrive.getCurrentPosition() - (int) (inches * Values.ticksPerInch);
                lTarget = lDrive.getCurrentPosition() - (int) (inches * Values.ticksPerInch);
                break;
            default:
                 rTarget = 0;
                 lTarget = 0;
        }
        lDrive.setTargetPosition(lTarget);
        rDrive.setTargetPosition(rTarget);
        Motors.runToPosition(driveMotors);
        timer.reset();
        switch (direction) {
            case FORWARD:
                setPowers(Math.abs(power),Math.abs(power));
                break;
            case BACK:
                setPowers(-Math.abs(power),-Math.abs(power));
                break;
            default:
                break;
        }
        while(opMode.opModeIsActive() && (timer.time()< timeoutS*1000) && (rDrive.isBusy() && lDrive.isBusy())) {
            if(!rDrive.isBusy()){
                rDrive.setPower(0);
            }
            if(!lDrive.isBusy()){
                lDrive.setPower(0);
            }
        }
        setPowers(0,0);

        Motors.useEncoders(driveMotors);
    }

//    private int headingCCWError(int start, int turn, int current ) {
//
//        int offset;
//        int target;
//
//        offset = 270 - start;
//        target = start - turn + offset;
//        current += offset;
//        if ( current >=360 ) {
//            current-=360;
//        } // avoid wrap around problem
//        return target - current;
//
//    }
//
//    private int headingCWError(int start, int turn, int current ) {
//
//        int offset;
//        int target;
//
//        offset = 90 - start;
//        target = start + turn + offset;
//        current += offset;
//        if ( current < 0 ) {
//            current+=360;
//        } // avoid wrap around problem
//        return target - current;
//    }


//    public double rotateIMU(double degrees,Direction direction, double power, double timeoutS, Telemetry telemetry) throws Exception{
//        double start = imu.getAngle(Axis.HEADING);
//        double target;
//        if(direction == Direction.CW){
//            target = start+degrees;
//        }else if (direction == Direction.CCW){
//            target = start - degrees;
//        }else{
//
//            target = start;
//        }
//
//
//    }

    // Untested proportional IMU rotation
    public void rotateIMU(Direction direction, double angle, double power, double allowedError, double timeoutS) {
        double currentHeading = imu.getAngle(Axis.HEADING);
        double targetHeading;

        switch (direction) {
            case CW:
                targetHeading = currentHeading + angle;
                break;
            case CCW:
                targetHeading = currentHeading - angle;
                break;
            default:
                targetHeading = currentHeading;
        }
        targetHeading = fixAngle(targetHeading);

        double error = getError(currentHeading, targetHeading);
        double startTime = System.currentTimeMillis();

        while (error > allowedError && (System.currentTimeMillis() - startTime) / 1000 < timeoutS) {
            double proportionalPower = power * error / 180;
            this.rotate(Direction.CW, RotateMethod.SPIN, proportionalPower);

            currentHeading = imu.getAngle(Axis.HEADING);
            error = getError(currentHeading, targetHeading);
        }

        this.setPowers(0, 0);
    }

    // Get angle between -180 and 180
    private double fixAngle(double angle) {
        while (angle > 180 || angle < -180) {
            angle += (angle < 180) ? 360 : -360;
        }
        return angle;
    }

    private double getError(double currentHeading, double targetHeading) {
        return Math.abs(fixAngle(targetHeading - currentHeading));
    }

}