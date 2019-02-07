package org.firstinspires.ftc.teamcode.Models

import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Utils.Logger

class Field(val dt: DriveTrain) {
    val l = Logger("FIELD")

    var currentHeading = 0.0
    var currentX = 0.0
    var currentY = 0.0
    var offset = 0.0
    init{
        l.log("Initialized field from constructor")
    }

    private fun registerPosition(x: Double, y: Double): Field {
        currentX = x
        currentY = y
        currentHeading = offset - dt.imu.angle
        l.log("Registered position at ($x, $y), facing $currentHeading degrees")
        return this
    }

    fun initialize(x: Double, y: Double, angleOffset: Double): Field {
        offset = angleOffset
        registerPosition(x, y)
        return this
    }

    fun rotateTo(heading: Double): Field {
        dt.rotateTo(heading)
        return this
    }

    fun moveTo(x: Double, y: Double, direction: Direction): Field {
        fun Double.fixHeading(): Double {
            return this - 360 * Math.round(this / 360)
        }

        val xError = x - currentX
        val yError = y - currentY

        val distance = direction.intRepr * Math.hypot(yError, xError)

        var targetHeading = Math.toDegrees(Math.atan2(yError, xError))
        if (direction == Direction.BACKWARD) {
            targetHeading = (targetHeading + 180).fixHeading()
        }
        targetHeading = (offset - targetHeading).fixHeading()

        l.log("moveTo($x, $y, $direction)")
        l.log("moving with distance of $distance to target heading of $targetHeading")

        dt.rotateTo(targetHeading)
        dt.drivePID(Direction.FORWARD, distance)

        registerPosition(x, y)

        return this
    }

    fun moveTo(coords:Coordinate, direction: Direction): Field {
        return moveTo(coords.x.toDouble(),coords.y.toDouble(),direction)
    }
}