package org.firstinspires.ftc.teamcode.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robotutil.DriveTrainNew;
import org.firstinspires.ftc.teamcode.robotutil.GoldCV;
import org.firstinspires.ftc.teamcode.robotutil.Utils;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Autonomous",group="FinalShit")

public class Autonomous extends LinearOpMode {

    DriveTrainNew dt;
    GoldCV cv;

    @Override
    public void runOpMode() throws InterruptedException {
        initialize();
        waitForStart();

        if (opModeIsActive()) {
            alignWithGold();
            // Drive forward
        }
    }

    private void initialize() {
        dt = new DriveTrainNew(this);
        cv = new GoldCV(this);
    }

    private void alignWithGold() {
        // Random values, needs tuning
        double alignPos = 100;
        double allowedAlignError = 100;

        if (cv.isFound()) {
            while (Math.abs(cv.getXPosition() - alignPos) > allowedAlignError) {
                // rotate robot
            }
        }
        telemetry.addData("gold mineral found: ", cv.isFound());
        telemetry.addData("gold mineral aligned: ", cv.getAligned());
    }

    private void haltUntilPressStart() {
        while (!gamepad1.start  && !isStopRequested()) {
            Utils.waitFor(300);
        }
    }
}
