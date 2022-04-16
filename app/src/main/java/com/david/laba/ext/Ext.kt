package com.david.laba.ext

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.Resources
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup

fun View.extend(width: Int, height: Int, animDuration: Long = 500) {
    val alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)

    val heightAnimator = ValueAnimator.ofInt(0, height)

    heightAnimator.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        val layoutParams: ViewGroup.LayoutParams = this.layoutParams
        layoutParams.height = value
        this.layoutParams = layoutParams
    }

    val widthAnimator = ValueAnimator.ofInt(0, width)
    widthAnimator.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        val layoutParams: ViewGroup.LayoutParams = this.layoutParams
        layoutParams.width = value
        this.layoutParams = layoutParams
    }

    AnimatorSet().apply {

        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
                visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?) {
            }

            override fun onAnimationCancel(animation: Animator?) {
                visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })

        playTogether(alphaAnimator, heightAnimator, widthAnimator)

        duration = animDuration

        start()
    }
}

fun View.shrink(animDuration: Long = 500) {
    val alphaAnimator = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)

    val width = this.width
    val height = this.height

    val heightAnimator = ValueAnimator.ofInt(height, 0)

    heightAnimator.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        val layoutParams: ViewGroup.LayoutParams = this.layoutParams
        layoutParams.height = value
        this.layoutParams = layoutParams
    }

    val widthAnimator = ValueAnimator.ofInt(width, 0)
    widthAnimator.addUpdateListener { valueAnimator ->
        val value = valueAnimator.animatedValue as Int
        val layoutParams: ViewGroup.LayoutParams = this.layoutParams
        layoutParams.width = value
        this.layoutParams = layoutParams
    }

    AnimatorSet().apply {

        addListener(object : Animator.AnimatorListener {
            override fun onAnimationStart(animation: Animator?) {
            }

            override fun onAnimationEnd(animation: Animator?) {
                visibility = View.GONE
            }

            override fun onAnimationCancel(animation: Animator?) {
                visibility = View.GONE
            }

            override fun onAnimationRepeat(animation: Animator?) {
            }
        })

        playTogether(alphaAnimator, heightAnimator, widthAnimator)

        duration = animDuration

        start()
    }
}

fun Number.dpToPx(): Int {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics
    ).toInt()
}