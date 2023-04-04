package com.aditya.dicoding.gamecatalog.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.dicoding.gamecatalog.BaseFragment
import com.aditya.dicoding.gamecatalog.MainFragmentDirections
import com.aditya.dicoding.gamecatalog.R
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.ui.GameListenerInterface
import com.aditya.dicoding.gamecatalog.core.ui.ListGameAdapter
import com.aditya.dicoding.gamecatalog.di.FavoriteModuleDependencies
import com.aditya.dicoding.gamecatalog.favorite.databinding.FragmentFavoriteBinding
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteFragment : BaseFragment<FragmentFavoriteBinding>(), GameListenerInterface {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentFavoriteBinding = FragmentFavoriteBinding::inflate
    @Inject
    lateinit var factory: FavoriteViewModelFactory

    private val favoriteViewModel: FavoriteViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(requireContext())
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    requireContext(),
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun setup() {
        statusBarColor(R.color.black_600)
        val listGameAdapter = ListGameAdapter(this)

        favoriteViewModel.favoriteGame.observe(viewLifecycleOwner) { dataGames ->
            listGameAdapter.differ.submitList(dataGames)
            binding.noFavorite.visibility = if (dataGames.isNotEmpty()) View.GONE else View.VISIBLE
        }

        with(binding.rvFavorite){
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = listGameAdapter
        }

    }

    override fun onDetailClicked(gameModel: GameModel) {
        val toDetail = MainFragmentDirections.actionMainFragmentToDetailFragment(gameModel)
        parentFragment?.parentFragment?.findNavController()?.navigate(toDetail)
    }

    override fun destroy() {
        binding.rvFavorite.adapter = null
    }
}