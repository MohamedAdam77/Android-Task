package com.example.androidtask.view.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.androidtask.R
import com.example.androidtask.database.ItemRepository
import com.example.androidtask.databinding.ActivityFavoriteBinding
import com.example.androidtask.interfaces.ItemClickListener
import com.example.androidtask.model.CountryModel
import com.example.androidtask.view.adapters.FavouriteAdapter
import com.example.androidtask.view.main.MainActivity
import com.google.android.gms.maps.model.LatLng

class FavoriteActivity : AppCompatActivity(), ItemClickListener {

    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding
    private var list = mutableListOf<CountryModel>()
    private lateinit var itemRepository: ItemRepository
    private lateinit var adapter: FavouriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFavoriteBinding = DataBindingUtil.setContentView(this, R.layout.activity_favorite)

        itemRepository = ItemRepository(this)

        adapter = FavouriteAdapter(list, this)

        activityFavoriteBinding.rvFavoriteList.adapter = adapter

        loadItems();
    }

    private fun loadItems() {
        itemRepository.items?.observe(this, Observer {
            if (it!!.isEmpty()) {
                activityFavoriteBinding.tvNoData.visibility = View.VISIBLE
                activityFavoriteBinding.rvFavoriteList.visibility = View.GONE
            }else{
                activityFavoriteBinding.tvNoData.visibility = View.GONE
                activityFavoriteBinding.rvFavoriteList.visibility = View.VISIBLE

                list.clear()
                list.addAll(it as Collection<CountryModel>)
                adapter.notifyDataSetChanged()
            }
        })
    }

    override fun onItemClick(lat: Double, lng: Double) {
        intent = Intent(this, MainActivity::class.java)
        intent.putExtra("lat", lat)
        intent.putExtra("lng", lng)
        startActivity(intent)
        this.finishAffinity()
    }


    override fun onDeleteClick(countryModel: CountryModel) {
        itemRepository.deleteCountry(countryModel)
        Toast.makeText(this, "Item Deleted !!", Toast.LENGTH_LONG).show()
    }
}
