package org.firstinspires.ftc.teamcode.Models

import org.firstinspires.ftc.teamcode.Components.DriveTrain
import org.firstinspires.ftc.teamcode.Utils.Logger
import org.firstinspires.ftc.teamcode.Utils.wait
import org.json.JSONObject
import java.lang.RuntimeException


class Field(val dt: DriveTrain) {
    val l = Logger("FIELD")
    val fieldX: Double = 288.0
    val fieldY: Double = 288.0

    var currentAngle = 0.0
    var currentX = 0.0
    var currentY = 0.0
    var offset = 0.0

    fun isOutOfBounds(x: Double, y: Double) = (false)

    fun registerOffset(offset: Double) {
        this.offset = offset
    }

    fun registerPosition(x: Double, y: Double, angle: Double) {
        if (isOutOfBounds(x, y)) {
            l.log("($currentX,$currentY) is out of bounds!")
            throw RuntimeException("($currentX,$currentY) is out of bounds")
        }
        this.currentX = x
        this.currentY = y
        this.currentAngle = angle + this.offset
//        if (this.currentX != x) l.log("Wrapped X value from $x => $currentX")
//        if (this.currentY != y) l.log("Wrapped Y value from $y => $currentY")
        l.log("Registered position: ($currentX,$currentY,$currentAngle)")
    }

    fun adjustAngle(angle: Double): Double {
        if (Math.abs(angle) > 90) {
            if (angle > 0) {
                return angle - 180.0
            } else {
                return angle + 180.0
            }
        } else {
            return angle
        }
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

    fun moveTo(x: Double, y: Double) {
        l.log("moveTo($x,$y) called")
        val angle = -Math.toDegrees(Math.atan2(y - currentY, x - currentX)) //heading and x-axis angle is off by 90 derees
        var dist = Math.hypot(x - currentX, y - currentY)
        var degreesToRotate = angle - currentAngle
        if (Math.abs(degreesToRotate) > 90) {
            dist *= -1
        }
        degreesToRotate = adjustAngle(degreesToRotate)
        l.logData("Calculated angle", angle)
        l.logData("Calculated distance", dist)
        dt.rotate(Direction.SPIN_CW, degreesToRotate.toInt(), 10)
//        dt.rotateTo(angle)
        wait(500)
        l.logData("Actual angle", dt.imu.angle)
        dt.drivePID(Direction.FORWARD, dist, timeout = 10)
        wait(500)
        l.logData("Final angle", dt.imu.angle)
        registerPosition(x, y, dt.imu.angle)
    }

    fun moveToDirection(x: Double, y: Double, rotationDirection: Direction) {
        l.log("moveTo($x,$y) called")
        val angle = Math.toDegrees(Math.atan2(y - currentY, x - currentX)) //heading and x-axis angle is off by 90 derees

        var dist = Math.hypot(x - currentX, y - currentY)
        var degreesToRotate = -(angle - currentAngle)
        degreesToRotate = fixAngle(degreesToRotate)
        l.logData("GAY degrees", degreesToRotate)
        l.logData("BIG GAY DIRECTION", rotationDirection.intRepr)
        if (!(rotationDirection.intRepr > 0 && degreesToRotate > 0) &&
                !(rotationDirection.intRepr < 0 && degreesToRotate < 0)) {
            if (Math.abs(degreesToRotate) > 90) {
                dist *= -1
            }
            degreesToRotate = adjustAngle(degreesToRotate)
            l.log("adjusting angle")
        }

        l.logData("Calculated angle", degreesToRotate)
        l.logData("Calculated distance", dist)
        dt.rotate(Direction.SPIN_CW, degreesToRotate.toInt(), 10)
//        dt.rotateTo(angle)
        wait(500)
        l.logData("Actual angle", dt.imu.angle)
        dt.drivePID(Direction.FORWARD, dist, timeout = 10)
        wait(500)
        l.logData("Final angle", dt.imu.angle)
        registerPosition(x, y, dt.imu.angle)
    }

    fun getPositionJson() = JSONObject().put("x", currentX).put("y", currentY).put("angle", currentAngle)
    fun getPositionString() = getPositionJson().toString()


}