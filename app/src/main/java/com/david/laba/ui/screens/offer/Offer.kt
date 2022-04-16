package com.david.laba.ui.screens.offer

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.david.laba.R
import com.david.laba.databinding.ActivityOfferBinding

class Offer : AppCompatActivity(R.layout.activity_offer) {

    companion object {
        fun getIntent(context: Context) = Intent(context, Offer::class.java)
    }

    private val binding by viewBinding(ActivityOfferBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
}