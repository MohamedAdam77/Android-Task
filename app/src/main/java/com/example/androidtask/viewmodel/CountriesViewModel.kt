package com.example.androidtask.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.androidtask.R
import com.example.androidtask.model.CountryModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.io.InputStream
import java.nio.charset.StandardCharsets


class CountriesViewModel : ViewModel() {

    private val countriesList = MutableLiveData<List<CountryModel>>()

    fun getCountriesList() : LiveData<List<CountryModel>> {
        return countriesList
    }

    fun getCountries(context: Context) {
        val token: TypeToken<List<CountryModel?>?> =
            object : TypeToken<List<CountryModel?>?>() {}
        countriesList.value = Gson().fromJson(loadJSONFromAsset(context), token.getType())
    }

    /**
     * Read json file as string
     *
     * @return json String
     */
    private fun loadJSONFromAsset(context: Context): String? {
        var json: String?
        json = try {
            val inputStream: InputStream = context.resources.openRawResource(R.raw.country_codes_lat_long)
            val size: Int = inputStream.available()
            val buffer = ByteArray(size)
            inputStream.read(buffer)
            inputStream.close()
            String(buffer, StandardCharsets.UTF_8)
        } catch (ex: IOException) {
            ex.printStackTrace()
            return null
        }
        return json
    }

}