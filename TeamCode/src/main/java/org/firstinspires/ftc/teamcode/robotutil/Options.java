package org.firstinspires.ftc.teamcode.robotutil;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.Telemetry;

import java.util.ArrayList;

public class Options {

    private Telemetry telemetry;
    private Gamepad gamepad;
    private ArrayList<Option> options;
    private int optionIndex;

    public Options(LinearOpMode opMode) {
        this.telemetry = opMode.telemetry;
        this.gamepad = opMode.gamepad1;
        this.options = new ArrayList<>();
        this.optionIndex = 0;
    }

    public void setOptions() {
        boolean confirmed = false;

        while (!confirmed) {
            if (gamepad.dpad_up && optionIndex < options.size() - 1) {
                optionIndex++;
            } else if (gamepad.dpad_down && optionIndex > 0) {
                optionIndex--;
            } else if (gamepad.x) {
                options.get(optionIndex).increment(true);
            } else if (gamepad.y) {
                options.get(optionIndex).increment(false);
            } else if (gamepad.left_stick_button && gamepad.right_stick_button) {
                telemetry.addLine("Confirmed!");
                confirmed = true;
            }
            displayOptions();

            Utils.waitFor(100);
        }
    }

    public void displayOptions() {
        telemetry.addData("Selected option:", optionIndex);
        for (Option option : options) {
            telemetry.addData(option.getName(),
                    (option.getQuantitative()) ? option.getValue() : option.getChoice());
        }
        telemetry.update();
    }

    public Option getOption(String name) {
        for (Option option : options) {
            if (option.getName().equals(name)) {
                return option;
            }
        }
        return new Option("Blank", 0, 0, 0);
    }

    public void addQuantitativeOption(String name, double min, double max, double step) {
        Option option = new Option(name, min, max, step);
        options.add(option);
    }

    public void addCategoricalOption(String name, String[] choices) {
        Option option = new Option(name, choices);
        options.add(option);
    }

}