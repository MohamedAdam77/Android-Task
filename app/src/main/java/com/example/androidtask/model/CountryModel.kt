package com.example.androidtask.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

@Entity
class CountryModel : ClusterItem {

    var country: String? = null
    var alpha2: String? = null
    var alpha3: String? = null
    @PrimaryKey
    var numeric = 0
    var latitude = 0.0
    var longitude = 0.0

    override fun getPosition(): LatLng {
        return LatLng(this.latitude, this.longitude)
    }

    override fun getTitle(): String? {
        return this.country
    }

    override fun getSnippet(): String? {
        return this.alpha2
    }
}