package org.firstinspires.ftc.teamcode.tasks;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.robotutil.IMU;

public class DriveTrainTaskMecanum extends TaskThread {

    private DcMotor lF, rF, lB, rB;
    private BNO055IMU adaImu;
    private IMU imu;
    private double r,robotAngle,rightX,frontLeft,frontRight,backLeft,backRight,slowMultiplier;

    ElapsedTime timer = new ElapsedTime(ElapsedTime.Resolution.MILLISECONDS);

    public DriveTrainTaskMecanum(LinearOpMode opMode) {
        this.opMode = opMode;

        initialize();
    }

    @Override
    public void run() {
        timer.reset();
        while (opMode.opModeIsActive() && running) {
            r = Math.hypot(opMode.gamepad1.left_stick_x, opMode.gamepad1.left_stick_y);
            robotAngle = Math.atan2(opMode.gamepad1.left_stick_y, opMode.gamepad1.left_stick_x) - Math.PI / 4;
            rightX = -opMode.gamepad1.right_stick_x;
            frontLeft = r * Math.cos(robotAngle) + rightX;
            frontRight = r * Math.sin(robotAngle) - rightX;
            backLeft = r * Math.sin(robotAngle) + rightX;
            backRight = r * Math.cos(robotAngle) - rightX;
            
            if(opMode.gamepad1.start){
                slowMultiplier = .3;
            }else{
                slowMultiplier = 1;
            }


            lF.setPower(frontLeft*slowMultiplier);
            rF.setPower(frontRight*slowMultiplier);
            lB.setPower(backLeft*slowMultiplier);
            rB.setPower(backRight*slowMultiplier);

        }
    }
    @Override
    public void initialize() {
        lF = opMode.hardwareMap.dcMotor.get("lfDrive");
        rF = opMode.hardwareMap.dcMotor.get("rfDrive");
        lB = opMode.hardwareMap.dcMotor.get("lbDrive");
        rB = opMode.hardwareMap.dcMotor.get("rbDrive");

        lB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rB.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rF.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        lF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rF.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lB.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        rF.setDirection(DcMotorSimple.Direction.REVERSE);
        rB.setDirection(DcMotorSimple.Direction.REVERSE);
        lB.setDirection(DcMotorSimple.Direction.FORWARD);
        lF.setDirection(DcMotorSimple.Direction.FORWARD);
        adaImu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu = new IMU(adaImu);

    }
}
