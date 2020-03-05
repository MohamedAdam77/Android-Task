package com.example.androidtask.view.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.androidtask.R
import com.example.androidtask.databinding.ActivityMainBinding
import com.example.androidtask.view.fragments.CountriesFragment
import com.example.androidtask.view.fragments.RepoListFragment
import com.example.androidtask.view.adapters.TabsPagerAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val sectionsPagerAdapter =
            TabsPagerAdapter(
                this,
                supportFragmentManager
            )
        val countriesFragment = CountriesFragment()
        intent?.let {
            val bundle = Bundle()
            bundle.putDouble("lat", intent.getDoubleExtra("lat", 0.0))
            bundle.putDouble("lng", intent.getDoubleExtra("lng", 0.0))
            countriesFragment.arguments = bundle
        }

        sectionsPagerAdapter.addFragment(countriesFragment, getString(R.string.tab_text_1))
        sectionsPagerAdapter.addFragment(RepoListFragment(), getString(R.string.tab_text_2))
        mainBinding.viewPager.adapter = sectionsPagerAdapter
        mainBinding.tabs.setupWithViewPager(mainBinding.viewPager)

    }
}