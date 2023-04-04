package com.aditya.dicoding.gamecatalog.setting

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.aditya.dicoding.gamecatalog.BaseFragment
import com.aditya.dicoding.gamecatalog.R
import com.aditya.dicoding.gamecatalog.databinding.FragmentSettingBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding>() {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSettingBinding = FragmentSettingBinding::inflate
    private val settingViewModel: SettingViewModel by viewModels()
    override fun setup() {
        statusBarColor(R.color.black_600)

        with(binding){
            settingViewModel.getThemeSetting.observe(viewLifecycleOwner){
                switchTheme.isChecked = it
                changeTheme(it)
            }

            switchTheme.setOnCheckedChangeListener { _, b ->
                settingViewModel.saveThemeSetting(b)
            }

            clearDatabase.setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle("Apa anda yakin?")
                    .setMessage("Database akan dihapus")
                    .setPositiveButton("Ya, Hapus") { _, _ ->
                        settingViewModel.deleteDatabas()
                    }
                    .setNegativeButton("Batal") {dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }

        }

    }

    override fun destroy() {}
}