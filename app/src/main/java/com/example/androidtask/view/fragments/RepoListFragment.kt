package com.example.androidtask.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.androidtask.R
import com.example.androidtask.databinding.FragmentListBinding
import com.example.androidtask.model.RepoModel
import com.example.androidtask.utils.PaginationListener
import com.example.androidtask.view.adapters.RepoListAdapter
import com.example.androidtask.viewmodel.RepoViewModel
import com.example.androidtask.utils.PaginationListener.Companion.PAGE_SIZE


/**
 * A placeholder fragment containing a simple view.
 */
class RepoListFragment : Fragment() {

    private lateinit var repoViewModel: RepoViewModel
    private var list = mutableListOf<RepoModel>()
    private var perPage = PAGE_SIZE
    var isLastPage: Boolean = false
    var isLoading: Boolean = false

    private lateinit var adapter: RepoListAdapter

    private var url: String = "https://api.github.com/users/JeffreyWay/repos?page=1&per_page="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repoViewModel = ViewModelProvider(this).get(RepoViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val fragmentListBinding: FragmentListBinding = DataBindingUtil
            .inflate(inflater, R.layout.fragment_list, container, false)

        val layoutManager = LinearLayoutManager(context)
        fragmentListBinding.rvRepoList.layoutManager = layoutManager

        adapter = RepoListAdapter(list)
        fragmentListBinding.rvRepoList.adapter = adapter

        repoViewModel.repoList.observe(viewLifecycleOwner, Observer {
            list.clear()
            adapter.addItems(it)
            adapter.removeLoading()
            isLoading = false
        })

        fragmentListBinding.rvRepoList.addOnScrollListener(object: PaginationListener(layoutManager) {

            override fun loadMoreItems() {
                isLoading = true
                perPage += 15
                loadItems()
            }

            override fun isLastPage(): Boolean {
                return isLastPage
            }

            override fun isLoading(): Boolean {
                return isLoading
            }

        })

        loadItems()

        return fragmentListBinding.root
    }


    private fun loadItems() {
        if (list.size < perPage){
            adapter.addLoading()
            repoViewModel.getRepoData(url + perPage)
        }else{
            isLastPage = true
        }
    }
}