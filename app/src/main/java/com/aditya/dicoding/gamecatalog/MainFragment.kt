package com.aditya.dicoding.gamecatalog

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aditya.dicoding.gamecatalog.databinding.FragmentMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : BaseFragment<FragmentMainBinding>() {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentMainBinding = FragmentMainBinding::inflate

    override fun setup() {
        with(binding) {
            val nav = containerFragment.getFragment<NavHostFragment>().navController
            bottomNavigationView.apply {
                selectedItemId = R.id.navigate_home
                setupWithNavController(nav)

                fab.setOnClickListener {
                    nav.navigate(R.id.navigate_home)
                }
            }
        }
    }

    override fun destroy() {}

}