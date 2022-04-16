package com.david.laba.ui.screens.main.entities.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.david.laba.databinding.ItemFacilityBinding
import com.david.laba.db.entities.WeatherPoint
import com.david.laba.ui.screens.offer.Offer

class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemFacilityBinding.bind(itemView)

    fun bind(weatherPoint: WeatherPoint) {
        binding.vLatitude.text = weatherPoint.latitude.toString()
        binding.vLongitude.text = weatherPoint.longitude.toString()
        binding.vTitle.text = weatherPoint.title
        binding.vWeatherType.text = weatherPoint.weatherType.toString()

        binding.root.setOnClickListener {
            val intent = Offer.getIntent(binding.root.context)

            binding.root.context.startActivity(intent)
        }
    }
}