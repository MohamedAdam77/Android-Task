package com.example.androidtask.utils

import android.content.Context
import com.example.androidtask.model.CountryModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.Cluster
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer

class MarkerClusterRenderer (
    val context: Context,
    val map: GoogleMap,
    clusterManager: ClusterManager<CountryModel>
) : DefaultClusterRenderer<CountryModel>(context, map, clusterManager) {

    override fun onBeforeClusterItemRendered(item: CountryModel?,
                                             markerOptions: MarkerOptions?) {
        super.onBeforeClusterItemRendered(item, markerOptions)
        markerOptions?.icon(BitmapDescriptorFactory.defaultMarker())
    }

    override fun shouldRenderAsCluster(cluster: Cluster<CountryModel>?): Boolean {
        super.shouldRenderAsCluster(cluster)
        return cluster != null && cluster.size >= 3
    }
}