package com.david.laba.ui.screens.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.view.KeyEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import by.kirich1409.viewbindingdelegate.viewBinding
import com.david.laba.R
import com.david.laba.application.App
import com.david.laba.databinding.ActivitySignInBinding
import com.david.laba.net.service.UserService
import com.david.laba.ui.custom.OnLastAddedListener
import com.david.laba.ui.screens.signup.SignUpActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.kodein.di.instance

class SignInActivity : AppCompatActivity(R.layout.activity_sign_in) {

    private val binding by viewBinding(ActivitySignInBinding::bind)
    private val userService: UserService by App.di.instance()

    private var phoneNumber: String = ""

    companion object {
        fun getIntent(context: Context) = Intent(context, SignInActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        showPhone()
        initCodeListeners()
        initPhoneListeners()
    }


    private fun initCodeListeners() {
        binding.inclCode.vChangePhoneNumber.setOnClickListener {
            showPhone()
        }

        binding.inclCode.vCode.setOnLastAddedListener(object : OnLastAddedListener {
            override fun onLastAdded(s: String) {


                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        userService.sendCode(phoneNumber, s)
                    } catch (e: Exception) {
                        this.cancel()
                    }
                }


                val intent = SignUpActivity.getIntent(baseContext)
                startActivity(intent)
                finish()
            }
        })
    }

    private fun initPhoneListeners() {
        binding.inclPhone.vEtNumber.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_DEL) {
                if (binding.inclPhone.vEtNumber.text?.length ?: 0 == 0) {
                    binding.inclPhone.vEtOperatorCode.requestFocus()
                }
            }
            false
        }

        binding.inclPhone.vEtOperatorCode.addTextChangedListener { text: Editable? ->
            text?.let {
                if (it.length == 2) {
                    binding.inclPhone.vEtNumber.requestFocus()
                }
            }

            checkIfPhoneNumberFilledAndEnableBtn()
        }

        binding.inclPhone.vEtNumber.addTextChangedListener { text: Editable? ->
            text?.let {
                if (it.length == 7) {
                    binding.inclPhone.vEtNumber.clearFocus()
                    val imm =
                        baseContext.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(binding.inclPhone.vEtNumber.windowToken, 0)
                }
            }

            checkIfPhoneNumberFilledAndEnableBtn()
        }
        binding.inclPhone.vBtnSendCode.setOnClickListener {
            phoneNumber = binding.inclPhone.vTvCountryCode.text.toString() +
                    binding.inclPhone.vEtOperatorCode.text.toString() +
                    binding.inclPhone.vEtNumber.text.toString()

            if (phoneNumber.length == 13) {
                binding.inclCode.vTvCodeWasSendOnNumber.text =
                    getString(R.string.code_was_sent, phoneNumber)


                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        userService.sendPhone(phoneNumber)
                    } catch (e: Exception) {
                        this.cancel()
                    }
                }

                showCode()
            }
        }
    }

    private fun showCode() {
        hideAllIncl()

        binding.inclCode.root.visibility = View.VISIBLE
    }

    private fun showPhone() {
        hideAllIncl()

        binding.inclPhone.root.visibility = View.VISIBLE
    }

    private fun hideAllIncl() {
        binding.inclPhone.root.visibility = View.GONE
        binding.inclCode.root.visibility = View.GONE
    }

    private fun checkIfPhoneNumberFilledAndEnableBtn() {
        binding.inclPhone.vBtnSendCode.isEnabled =
            binding.inclPhone.vEtOperatorCode.text?.length ?: 0 == 2 &&
                    binding.inclPhone.vEtNumber.text?.length ?: 0 == 7
    }
}