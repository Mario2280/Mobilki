package com.david.laba.ui.screens.main.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.david.laba.R
import com.david.laba.databinding.FragmentProfileBinding

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        fun getInstance(): ProfileFragment = ProfileFragment()
    }

    private val binding by viewBinding(FragmentProfileBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenuClickListeners()
    }

    private fun initMenuClickListeners() {
    }
}