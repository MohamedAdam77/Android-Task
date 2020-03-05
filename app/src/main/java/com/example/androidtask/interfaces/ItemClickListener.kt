package com.example.androidtask.interfaces

import com.example.androidtask.model.CountryModel
import com.google.android.gms.maps.model.LatLng

interface ItemClickListener {
    fun onItemClick(lat: Double, lng: Double)
    fun onDeleteClick(countryModel: CountryModel)
}