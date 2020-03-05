package com.example.androidtask.repository

import com.example.androidtask.interfaces.CallBackInterface
import okhttp3.*
import org.json.JSONArray
import java.io.IOException

class WebRequestOkHttp3(
    link: String,
    callBackInterface: CallBackInterface?
) : Callback {
    private var okHttpClient: OkHttpClient? = null
    private var callBackInterface: CallBackInterface? = null
    private val mLink: String

    private fun onRequest() {
        onGet()
    }

    private fun onGet() {
        okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .addHeader("Content-Type", "application/json; charset=utf-8")
            .url(mLink)
            .get()
            .build()
        val call = okHttpClient!!.newCall(request)
        call.enqueue(this)
    }

    override fun onFailure(call: Call, e: IOException) {
        e.message?.let { callBackInterface!!.onCallBackFailed(it) }
    }

    @Throws(IOException::class)
    override fun onResponse(call: Call, response: Response) {
        try {
            val Data = response.body()!!.string()
            val jsonArray = JSONArray(Data)
            callBackInterface?.onCallBackResult(jsonArray)
        } catch (e: Exception) {
            e.printStackTrace()
            callBackInterface?.onCallBackResult(null)
        }
    }

    init {
        this.callBackInterface = callBackInterface
        mLink = link
        onRequest()
    }
}