package com.aditya.dicoding.gamecatalog

import android.app.AlertDialog
import android.view.LayoutInflater
import com.aditya.dicoding.gamecatalog.core.utils.Result
import com.aditya.dicoding.gamecatalog.core.utils.validateSignature
import com.aditya.dicoding.gamecatalog.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {
    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding = ActivityMainBinding::inflate

    @Suppress("SpellCheckingInspection")
    override fun setup() {
        if (this.validateSignature("6eKEXlelenZGsrb43zNwEY5AgQU=") == Result.INVALID) {
            AlertDialog.Builder(this)
                .setTitle("Security Issue!")
                .setMessage("Anda terdeteksi mengubah source code aplikasi ini")
                .setCancelable(false)
                .setNegativeButton("Oke") { _, _ ->
                    finishAffinity()
                }
                .show()
        }
    }
}