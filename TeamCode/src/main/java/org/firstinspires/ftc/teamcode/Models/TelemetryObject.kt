package org.firstinspires.ftc.teamcode.Models

import org.json.JSONException
import org.json.JSONObject

class TelemetryObject() {
    var json = new JSONObject()

    fun addVar(name:String, value:Int):TelemetryObject{
        this.json.put(name,value)
        return this
    }

    fun addVar(name:String, value:Double):TelemetryObject{
        this.json.put(name,value)
        return this
    }

    fun addVar(name:String, value:String):TelemetryObject{
        this.json.put(name,value)
        return this
    }

    fun addVar(name:String, value:Boolean):TelemetryObject{
        this.json.put(name,value)
        return this
    }

    fun addVar(name:String, value:Long):TelemetryObject{
        this.json.put(name,value)
        return this
    }

    fun getJSON():JSONObject{
        return this.json
    }


}