package com.aditya.dicoding.gamecatalog

import android.view.LayoutInflater
import com.aditya.dicoding.gamecatalog.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    override fun setup() {

    }
}