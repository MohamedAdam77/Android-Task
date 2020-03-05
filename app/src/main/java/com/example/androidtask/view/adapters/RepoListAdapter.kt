package com.example.androidtask.view.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.androidtask.R
import com.example.androidtask.databinding.ItemListBinding
import com.example.androidtask.databinding.ItemLoadingBinding
import com.example.androidtask.model.RepoModel


class RepoListAdapter(
    private val list: MutableList<RepoModel>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1
    private var isLoaderVisible = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            VIEW_TYPE_NORMAL -> mViewHolder(DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.item_list, parent, false)
            )
            VIEW_TYPE_LOADING -> LoadingViewHolder(DataBindingUtil
                .inflate(LayoutInflater.from(parent.context), R.layout.item_loading, parent, false)
            )
            else -> null
        }!!
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        if (isLoaderVisible){
            return if (position == list.size - 1) VIEW_TYPE_LOADING else VIEW_TYPE_NORMAL
        }
        return VIEW_TYPE_NORMAL
    }

    fun addItems(postItems: List<RepoModel?>?) {
        list.addAll(postItems as Collection<RepoModel>)
        notifyDataSetChanged()
    }

    fun addLoading() {
        isLoaderVisible = true
        list.add(RepoModel())
        notifyItemInserted(list.size - 1)
    }

    fun removeLoading() {
        isLoaderVisible = false
        val position: Int = list.size - 1
        val item: RepoModel? = getItem(position)
        if (item != null) {
            list.removeAt(position)
            notifyItemRemoved(position)
        }
    }

    fun getItem(position: Int): RepoModel? {
        return list[position]
    }

    fun clear() {
        list.clear()
        notifyDataSetChanged()
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is mViewHolder){
            holder.onBind(position)
        }
    }

    inner class mViewHolder(var itemListBinding: ItemListBinding)
        : RecyclerView.ViewHolder(itemListBinding.root){

        fun onBind(position: Int){
            val repoModel = list[position]
            itemListBinding.tvRepoName.text = repoModel.name
            itemListBinding.tvDesc.text = repoModel.description
        }
    }

    inner class LoadingViewHolder( loadingBinding: ItemLoadingBinding)
        : RecyclerView.ViewHolder(loadingBinding.root)
}