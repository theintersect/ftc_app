package org.firstinspires.ftc.teamcode.Components

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode
import com.qualcomm.robotcore.hardware.DcMotorSimple
import org.firstinspires.ftc.teamcode.Utils.Logger

public class Sweeper(val opMode: LinearOpMode) {
    val l: Logger = Logger("SWEEPER")

    val sweeper = Motor(opMode.hardwareMap, "sweeper")

    init {

        l.log("entered init")
        sweeper.setDirection(DcMotorSimple.Direction.FORWARD)

    }

    fun setPowers(power: Double) {
        sweeper.setPower(power)
    }

    fun stopAll(coast: Boolean = false) {
        sweeper.stop(coast)
    }


}