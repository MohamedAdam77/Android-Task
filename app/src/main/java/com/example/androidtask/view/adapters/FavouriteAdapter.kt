package com.example.androidtask.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtask.R
import com.example.androidtask.databinding.ItemFavoriteBinding
import com.example.androidtask.interfaces.ItemClickListener
import com.example.androidtask.model.CountryModel


class FavouriteAdapter(
    private val list: List<CountryModel>,
    private val itemClickListener: ItemClickListener
) : RecyclerView.Adapter<FavouriteAdapter.mViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): mViewHolder {
        return mViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context),
            R.layout.item_favorite, parent, false))
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: mViewHolder, position: Int) {
        var model = list[position]
        holder.favoriteItemBinding.tvCountryName.text = model.country
        holder.favoriteItemBinding.tvCountryAlpha.text = model.alpha2

        holder.favoriteItemBinding.tvDelete.setOnClickListener {
            itemClickListener.onDeleteClick(model)
        }

        holder.favoriteItemBinding.root.setOnClickListener {
            itemClickListener.onItemClick(model.latitude, model.longitude)
        }
    }


    inner class mViewHolder(var favoriteItemBinding: ItemFavoriteBinding)
        : RecyclerView.ViewHolder(favoriteItemBinding.root)
}