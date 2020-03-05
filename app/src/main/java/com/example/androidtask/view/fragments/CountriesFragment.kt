package com.example.androidtask.view.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.androidtask.R
import com.example.androidtask.database.ItemRepository
import com.example.androidtask.databinding.FragmentCountriesBinding
import com.example.androidtask.model.CountryModel
import com.example.androidtask.utils.MarkerClusterRenderer
import com.example.androidtask.view.favorite.FavoriteActivity
import com.example.androidtask.viewmodel.CountriesViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.clustering.ClusterManager


/**
 * A placeholder fragment containing a simple view.
 */
class CountriesFragment : Fragment() {

    private lateinit var countriesViewModel: CountriesViewModel
    private lateinit var mClusterManager: ClusterManager<CountryModel>

    private var selectedBitmap: BitmapDescriptor? = null
    private var unselectedBitmap: BitmapDescriptor? = null
    private var lastMarker: Marker? = null

    private lateinit var map: GoogleMap

    private lateinit var itemRepository: ItemRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        countriesViewModel = ViewModelProvider(this).get(CountriesViewModel::class.java)
        itemRepository = ItemRepository(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentCountriesBinding: FragmentCountriesBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_countries, container, false)

        val smf: SupportMapFragment = childFragmentManager.findFragmentById(R.id.map)
                as SupportMapFragment

        smf.getMapAsync {
            this.map = it
            setUpClusterer()
        }
        countriesViewModel.getCountriesList().observe(viewLifecycleOwner,
            Observer {
                if (it.isNotEmpty()){
                    addItemToCluster(it)
                }
        })

        fragmentCountriesBinding.fabFavorites.setOnClickListener {
            startActivity(Intent(context, FavoriteActivity::class.java))
        }

        return fragmentCountriesBinding.root

    }

    private fun addItemToCluster(it: List<CountryModel>?) {
        if (it != null) {
            for (model: CountryModel in it){
                mClusterManager.addItems(it)
                mClusterManager.cluster()
            }

            arguments?.let {
                if (it.getDouble("lat") != 0.0) {
                    map.moveCamera(
                        CameraUpdateFactory
                            .newLatLngZoom(LatLng(it.getDouble("lat"), it.getDouble("lng")), 5f)
                    )
                }
            }
        }
    }

    private fun setUpClusterer() {
        // Initialize the manager with the context and the map.
        // (Activity extends context, so we can pass 'this' in the constructor.)
        mClusterManager = ClusterManager<CountryModel>(context, map)

        val clusterRenderer = MarkerClusterRenderer(context!!, map, mClusterManager)
        mClusterManager.renderer = clusterRenderer

        selectedBitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
        unselectedBitmap = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)

        // Point the map's listeners at the listeners implemented by the cluster
        // manager.
        map.setOnCameraIdleListener(mClusterManager)
        map.setOnMarkerClickListener(mClusterManager)

        mClusterManager.setOnClusterItemClickListener{
            //Log.e("I clicked @ ", "Cluster Item")
            lastMarker?.setIcon(unselectedBitmap)
            lastMarker = clusterRenderer.getMarker(it) // Here we access a selected marker.
            lastMarker?.setIcon(selectedBitmap)

            itemRepository.insertCountry(it)
            Toast.makeText(context, "Place Added To Favourite List!", Toast.LENGTH_LONG).show()

            false
        }

        countriesViewModel.apply { context?.let { it1 -> getCountries(it1) } }
    }
}