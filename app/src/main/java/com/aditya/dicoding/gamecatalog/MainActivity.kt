package com.aditya.dicoding.gamecatalog

import android.view.LayoutInflater
import com.aditya.dicoding.gamecatalog.core.utils.Result
import com.aditya.dicoding.gamecatalog.core.utils.validateSignature
import com.aditya.dicoding.gamecatalog.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    @Suppress("SpellCheckingInspection")
    override fun setup() {
        if (this.validateSignature("6eKEXlelenZGsrb43zNwEY5AgQU=") == Result.VALID) {
           Timber.d("Valid")
        } else {
            Timber.d("Not Valid")
        }
    }
}