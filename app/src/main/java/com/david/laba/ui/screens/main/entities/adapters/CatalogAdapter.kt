package com.david.laba.ui.screens.main.entities.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.david.laba.R
import com.david.laba.db.entities.WeatherPoint

class CatalogAdapter() : RecyclerView.Adapter<CatalogViewHolder>() {

    var weatherPoints: ArrayList<WeatherPoint> = ArrayList()
        @SuppressLint("NotifyDataSetChanged")
        set(value) {
            field.clear()
            field.addAll(value)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        return CatalogViewHolder(
            LayoutInflater
                .from(parent.context)
                .inflate(
                    R.layout.item_facility,
                    parent,
                    false
                )
        )
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        holder.bind(weatherPoints[position])
    }

    override fun getItemCount(): Int {
        return weatherPoints.size
    }
}