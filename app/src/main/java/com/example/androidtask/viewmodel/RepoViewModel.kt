package com.example.androidtask.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.androidtask.interfaces.CallBackInterface
import com.example.androidtask.model.RepoModel
import com.example.androidtask.repository.WebRequestOkHttp3
import com.google.gson.Gson
import org.json.JSONArray
import org.json.JSONException
import java.util.*

class RepoViewModel : ViewModel() {
    val repoList =
        MutableLiveData<List<RepoModel>>()
    val _errorMsg = MutableLiveData<String>()

    fun getRepoData(mLink: String?) {
        WebRequestOkHttp3(mLink!!, object : CallBackInterface {
            override fun onCallBackResult(jsonArray: JSONArray?) {
                collectObjects(jsonArray)
            }

            override fun onCallBackFailed(errorMsg: String) {
                _errorMsg.postValue(errorMsg)
            }
        })
    }

    private fun collectObjects(jsonArray: JSONArray?) {
        val repoModels: MutableList<RepoModel> = ArrayList()
        if (jsonArray != null) {
            for (i in 0 until jsonArray.length()) {
                try {
                    val repoModel =
                        Gson().fromJson(jsonArray[i].toString(), RepoModel::class.java)
                    repoModels.add(repoModel)
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
            }
        }
        if (repoModels.isNotEmpty()) {
            repoList.postValue(repoModels)
        }
    }
}