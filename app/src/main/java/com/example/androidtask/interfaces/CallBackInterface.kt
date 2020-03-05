package com.example.androidtask.interfaces

import org.json.JSONArray

interface CallBackInterface {
    fun onCallBackResult(jsonArray: JSONArray?)
    fun onCallBackFailed(errorMsg: String)
}