package org.firstinspires.ftc.teamcode.tasks;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robotutil.DriveTrainNew;
import org.firstinspires.ftc.teamcode.robotutil.HangSlides;
import org.firstinspires.ftc.teamcode.robotutil.Sweeper;

/**
 * Created by Howard on 10/15/16.
 */
public class HangTask extends TaskThread {
    private HangSlides slides;

    public HangTask(LinearOpMode opMode) {
        this.opMode = opMode;
        initialize();
    }

    @Override
    public void run() {
        timer.reset();

        double power = 1.0;

        while (opMode.opModeIsActive() && this.running) {
            if (opMode.gamepad1.a) {
                slides.setPower(power);
            } else if (opMode.gamepad1.b) {
                slides.setPower(-1 * power);
            } else {
                slides.setPower(0);
            }
        }
    }

    @Override
    public void initialize() {
        DcMotor hangTop = opMode.hardwareMap.dcMotor.get("hangTop");
        DcMotor hangBottom = opMode.hardwareMap.dcMotor.get("hangBottom");

        this.slides = new HangSlides(this.opMode, hangTop, hangBottom);
    }
}


