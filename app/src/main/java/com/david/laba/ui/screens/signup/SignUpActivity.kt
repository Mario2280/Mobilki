package com.david.laba.ui.screens.signup

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.david.laba.R
import com.david.laba.databinding.ActivitySignUpBinding
import com.david.laba.ui.screens.main.MainActivity

class SignUpActivity : AppCompatActivity(R.layout.activity_sign_up) {

    companion object {
        fun getIntent(context: Context) = Intent(context, SignUpActivity::class.java)
    }

    private val binding by viewBinding(ActivitySignUpBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initListeners()
    }

    private fun initListeners() {
        binding.vBtnApply.isEnabled = false

        binding.vCbTermsOfUse.setOnCheckedChangeListener { _, isChecked ->
            binding.vBtnApply.isEnabled = isChecked
        }

        binding.vBtnApply.setOnClickListener {
            val intent = MainActivity.getIntent(baseContext)

            startActivity(intent)

            finish()
        }
    }
}