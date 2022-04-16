package com.david.laba.ui.screens.main.entities

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.david.laba.R
import com.david.laba.application.App
import com.david.laba.auxiliary.AppSchedulers
import com.david.laba.databinding.FragmentCatalogBinding
import com.david.laba.db.AppDatabase
import com.david.laba.db.entities.WeatherPoint
import com.david.laba.ui.screens.main.entities.adapters.CatalogAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.kodein.di.instance

class CatalogFragment : Fragment(R.layout.fragment_catalog) {

    companion object {
        fun getInstance(): CatalogFragment = CatalogFragment()
    }

    private val binding by viewBinding(FragmentCatalogBinding::bind)

    private val room: AppDatabase by App.di.instance()
    private val appSchedulers: AppSchedulers by App.di.instance()

    private var adapterCatalog: CatalogAdapter? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initAdapters()

        getFacilitiesFromDb()
    }

    private fun initAdapters() {
        adapterCatalog = CatalogAdapter()

        binding.vRvCatalog.adapter = adapterCatalog
        binding.vRvCatalog.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getFacilitiesFromDb() {
        CoroutineScope(Dispatchers.IO).launch {

            val facilities = room.weatherDao().getAll()

            withContext(Dispatchers.Main) {
                adapterCatalog?.weatherPoints = facilities as ArrayList<WeatherPoint>
            }
        }
    }
}