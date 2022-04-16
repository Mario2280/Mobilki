package com.david.laba.ui.custom

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.widget.addTextChangedListener
import com.david.laba.R
import com.david.laba.databinding.ViewEditTextCellBinding

class SeparateEditText @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    defStyle: Int = 0
) : LinearLayout(context, attributeSet, defStyle) {
    private val savedInput: CharArray

    private var count = 4

    private var onLastAddedListener: OnLastAddedListener? = null

    init {
        orientation = HORIZONTAL
        weightSum = 100F

        attributeSet?.let { attrs ->
            context.obtainStyledAttributes(
                attrs,
                R.styleable.SeparateEditText,
                defStyle,
                0
            ).apply {
                count = getInt(R.styleable.SeparateEditText_count, 4)
            }
        }

        savedInput = CharArray(count)
    }

    fun setOnLastAddedListener(l: OnLastAddedListener) {
        onLastAddedListener = l
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        addEditTextCells()
    }

    private fun addEditTextCells() {
        for (i in 0 until count) {
            val editText =
                ViewEditTextCellBinding.inflate(LayoutInflater.from(context), this, true)

            editText.root.tag = i

            editText.root.addTextChangedListener { editable ->
                editable?.let { text ->

                    if (text.length == 1) {
                        savedInput[editText.root.tag as Int] = text[0]

                        val next = editText.root.focusSearch(View.FOCUS_RIGHT)

                        next?.requestFocus() ?: kotlin.run {
                            editText.root.clearFocus()
                            val imm =
                                context.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                            imm?.hideSoftInputFromWindow(editText.root.windowToken, 0)

                            val str = StringBuilder()
                            savedInput.forEach {
                                str.append(it)
                            }

                            onLastAddedListener?.onLastAdded(str.toString())
                        }
                    }
                }
            }

            editText.root.setOnFocusChangeListener { view, focus ->
                if (focus && view is EditText) {
                    view.setText("")
                }
            }
        }
    }
}

interface OnLastAddedListener {
    fun onLastAdded(s: String)
}