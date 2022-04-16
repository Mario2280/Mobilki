package com.david.laba.ui.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.david.laba.R
import com.david.laba.application.App
import com.david.laba.auxiliary.AppSchedulers
import com.david.laba.databinding.ActivityMainBinding
import com.david.laba.db.AppDatabase
import com.david.laba.db.entities.WeatherPoint
import com.david.laba.ui.screens.main.entities.CatalogFragment
import com.david.laba.ui.screens.main.maps.MapsFragment
import com.david.laba.ui.screens.main.settings.ProfileFragment
import com.ncapdevi.fragnav.FragNavController
import com.ncapdevi.fragnav.FragNavTransactionOptions
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.instance

class MainActivity : AppCompatActivity(R.layout.activity_main) {

    companion object {
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
    }

    private val binding by viewBinding(ActivityMainBinding::bind)

    private val fragNavController =
        FragNavController(supportFragmentManager, R.id.vBaseFragmentContainer)

    private val room: AppDatabase by App.di.instance()
    private val appSchedulers: AppSchedulers by App.di.instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initNavigation(savedInstanceState)

        addSomeDataDelete()
    }

    private fun addSomeDataDelete() {
        val facilities = arrayListOf(
            WeatherPoint(
                0,
                "Mac",
                27.567422,
                53.89147,
            ),
            WeatherPoint(
                0,
                "Burger",
                27.563487,
                53.89347,
            ),
            WeatherPoint(
                0,
                "Tranty",
                27.561234,
                53.89674,
            ),
            WeatherPoint(
                0,
                "Suchi",
                27.568243,
                53.8992834,
            ),
        )

        CoroutineScope(Dispatchers.IO).launch {
            room.weatherDao().deleteAll()

            facilities.forEach { facility ->
                room.weatherDao().insert(facility)
            }
        }
    }

    private fun initNavigation(savedInstanceState: Bundle?) {

        val fragments = listOf(
            CatalogFragment.getInstance(),
            MapsFragment.getInstance(),
            ProfileFragment.getInstance(),
        )

        fragNavController.rootFragments = fragments

        fragNavController.defaultTransactionOptions =
            FragNavTransactionOptions
                .newBuilder()
                .build()

        fragNavController.fragmentHideStrategy = FragNavController.DETACH

        fragNavController.initialize(FragNavController.TAB1, savedInstanceState)

        binding.vBnmBaseMenu.setOnItemSelectedListener {
            when (it.itemId) {

                R.id.catalogPage -> {
                    fragNavController.switchTab(FragNavController.TAB1)
                    true
                }

                R.id.mapsPage -> {
                    fragNavController.switchTab(FragNavController.TAB2)
                    true
                }

                R.id.profilePage -> {
                    fragNavController.switchTab(FragNavController.TAB3)
                    true
                }

                else -> throw Exception("No such navigation index")
            }
        }
    }
}