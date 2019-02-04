package org.firstinspires.ftc.teamcode.Utils

import com.google.gson.JsonObject
import org.firstinspires.ftc.teamcode.Models.PIDConstants
import org.firstinspires.ftc.teamcode.Tasks.WebsocketTask
import org.json.JSONException
import org.json.JSONObject
import java.lang.Exception


public class PIDController(val pidConstants: PIDConstants, val desiredVal: Double,val broadcast:Boolean=false,val wss:WebsocketTask? = null) {
    var prevTime: Long? = null
    var prevError: Double? = null
    var runningI: Double = 0.0
    val l:Logger = Logger("PID")

    fun initController(actualVal: Double) {
        prevTime = System.currentTimeMillis()
        prevError = desiredVal - actualVal
//        clearFile("pidReadout.txt")
    }

    fun cap(value: Double, max: Double, min: Double): Double {
        return Math.max(Math.min(value, max),min)
    }


    fun output(actualVal: Double,adjustError:(Double)-> Double = {it}): Double {
        if (prevTime != null && prevError != null) {
            val e: Double = adjustError(desiredVal - actualVal)
            val de = e - prevError!!
            val currentTime = System.currentTimeMillis()
            val dt:Double = (prevTime!! - currentTime).toDouble()/1000

            val P = pidConstants.kP * e
            l.log("${pidConstants.kP} * $e = $P")
            runningI += -pidConstants.kI * e * dt
            val I = runningI

            val D = cap(-pidConstants.kD * de / dt, 0.5, -0.5)

            val output = P + I + D

            l.lineBreak()
            l.logData("ts",System.currentTimeMillis())
            l.logData("err",e)
            l.logData("de",de)
            l.logData("dt",dt)
            l.logData("p",P)
            l.logData("i",I)
            l.logData("d",D)
            l.logData("out",output)
            l.lineBreak()

            prevError = e
            prevTime = currentTime
            try{
                val message = JSONObject()
                        .put("ts",System.currentTimeMillis())
                        .put("error",e)
                        .put("de",de)
                        .put("dt",dt)
                        .put("p",P)
                        .put("i",I)
                        .put("d",D)
                        .put("output",output)
                if(broadcast && wss != null){
                    wss.server.broadcastData("pid", message)
                }
//                writeFile("pidReadout.txt",content=message.toString(),overWrite = false)
            }catch(e:java.lang.Exception){
                l.log(e.message!!)
            } catch(e:JSONException){
                l.log(e.message!!)
            }

            return output
        } else {
            throw KotlinNullPointerException("PIDController controller not initialized!")
        }
    }
}