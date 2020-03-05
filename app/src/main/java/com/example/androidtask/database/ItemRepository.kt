package com.example.androidtask.database

import android.annotation.SuppressLint
import android.content.Context
import android.os.AsyncTask
import androidx.lifecycle.LiveData
import com.example.androidtask.model.CountryModel

class ItemRepository(context: Context?) {

    private val appDatabase: AppDatabase = AppDatabase.getAppDatabase(context)

    @SuppressLint("StaticFieldLeak")
    fun insertCountry(country: CountryModel?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                appDatabase.daoAccess().insertPost(country)
                return null
            }
        }.execute()
    }

    @SuppressLint("StaticFieldLeak")
    fun deleteCountry(country: CountryModel?) {
        object : AsyncTask<Void?, Void?, Void?>() {
            override fun doInBackground(vararg params: Void?): Void? {
                appDatabase.daoAccess().deletePost(country)
                return null
            }
        }.execute()
    }

    fun isFavorite(title: String?): Boolean {
        return appDatabase.daoAccess().getCountry(title) == 1
    }

    val items: LiveData<List<CountryModel?>?>?
        get() = appDatabase.daoAccess().fetchAllItems()

}