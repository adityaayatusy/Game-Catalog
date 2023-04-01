package com.aditya.dicoding.gamecatalog

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity<VB: ViewBinding> : AppCompatActivity() {

    private var _binding: VB? = null
    protected val binding get() = _binding as VB
    abstract val bindingInflater: (LayoutInflater) -> VB
    protected val tag: String = javaClass.name
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = bindingInflater.invoke(layoutInflater)
        setContentView(requireNotNull(_binding).root)

        setup()
    }

    abstract fun setup()

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}