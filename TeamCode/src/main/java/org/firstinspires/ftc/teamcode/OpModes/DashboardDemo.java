package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Utils.BasicServer;
import org.firstinspires.ftc.teamcode.Utils.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Socket client")


public class DashboardDemo extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {
        Logger l = new Logger("DashboardDemo");
        l.log("Waiting for start");


        //todo: log imu position and send across websocket for dashboard to display


    }
}
