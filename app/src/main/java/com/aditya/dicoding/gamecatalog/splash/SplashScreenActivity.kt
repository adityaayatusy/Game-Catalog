package com.aditya.dicoding.gamecatalog.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateInterpolator
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.aditya.dicoding.gamecatalog.BaseActivity
import com.aditya.dicoding.gamecatalog.MainActivity
import com.aditya.dicoding.gamecatalog.databinding.ActivitySplashScreenBinding
import dagger.hilt.android.AndroidEntryPoint

@SuppressLint("CustomSplashScreen")
@AndroidEntryPoint
class SplashScreenActivity : BaseActivity<ActivitySplashScreenBinding>() {

    override val bindingInflater: (LayoutInflater) -> ActivitySplashScreenBinding = ActivitySplashScreenBinding::inflate
    private val splashViewModel: SplashViewModel by viewModels()

    override fun setup() {
        WindowInsetsControllerCompat(window, window.decorView)
            .hide(WindowInsetsCompat.Type.systemBars())

        splashViewModel.isDark.observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }

       val logo = animationAlpha(binding.logoImageView)
       val logoText = animationAlpha(binding.logoTextView)

        AnimatorSet().apply {
            playTogether(logo, logoText)
            start()
        }.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }
        })
    }

    private fun animationAlpha(view: View): ObjectAnimator{
        return ObjectAnimator.ofFloat(view, View.ALPHA, 1f).apply {
            startDelay = 200
            duration = 2000
            interpolator = AccelerateInterpolator()
        }
    }

}