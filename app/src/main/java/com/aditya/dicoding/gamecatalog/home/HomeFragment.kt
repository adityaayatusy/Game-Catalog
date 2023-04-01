package com.aditya.dicoding.gamecatalog.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.dicoding.gamecatalog.BaseFragment
import com.aditya.dicoding.gamecatalog.MainFragmentDirections
import com.aditya.dicoding.gamecatalog.R
import com.aditya.dicoding.gamecatalog.core.data.Resource
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.ui.CardGameAdapter
import com.aditya.dicoding.gamecatalog.core.ui.GameListenerInterface
import com.aditya.dicoding.gamecatalog.core.ui.ListGameAdapter
import com.aditya.dicoding.gamecatalog.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding>(), GameListenerInterface {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding = FragmentHomeBinding::inflate
    private val homeViewModel: HomeViewModel by viewModels()
    private val listGameAdapter = ListGameAdapter(this@HomeFragment)
    private val cardGameAdapter = CardGameAdapter(this@HomeFragment)
    private var job: Job? = null

    override fun setup() {
        homeViewModel.isDark.observe(viewLifecycleOwner) {
            changeTheme(it)
        }

        loadSearch()
        loadTopGames()
        loadListGame()
        loadRecycleView()
    }

    private fun loadRecycleView(){
        with(binding){
            with(listGames){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = listGameAdapter
            }

            with(listHotGames){
                layoutManager = LinearLayoutManager(context).apply {
                    orientation =  LinearLayoutManager.HORIZONTAL
                }
                setHasFixedSize(true)
                adapter = cardGameAdapter
            }
        }
    }

    private fun showLoading(){
        with(binding){
            loadingGames.visibility = View.VISIBLE
            loadingTopGames.visibility = View.VISIBLE
            listGames.visibility = View.GONE
            listHotGames.visibility = View.GONE
        }
    }

    private fun hideLoading(){
        with(binding){
            loadingGames.visibility = View.GONE
            loadingTopGames.visibility = View.GONE
            listGames.visibility = View.VISIBLE
            listHotGames.visibility = View.VISIBLE
        }
    }

    override fun onDetailClicked(gameModel: GameModel) {
        val toDetail = MainFragmentDirections.actionMainFragmentToDetailFragment(gameModel)
        parentFragment?.parentFragment?.findNavController()?.navigate(toDetail)
    }

    private fun loadSearch(){
        MainScope().launch {
            with(binding){
                search.setText("")
                search.addTextChangedListener {
                    job?.cancel()
                    job = lifecycleScope.launch {
                        delay(500)
                        Log.d(tag, "setup s: ${search.text}")
                        val toSearch = MainFragmentDirections.actionMainFragmentToSearchFragment(it.toString())
                        parentFragment?.parentFragment?.findNavController()?.navigate(toSearch)
                    }
                }
            }
        }
    }

    private fun loadListGame(){
        homeViewModel.games.observe(viewLifecycleOwner) { games ->
            if(games !is Resource.Loading)
                binding.noDataGame.visibility = if (games.data == null) View.VISIBLE else View.GONE

            loadData(games) {
                listGameAdapter.differ.submitList(games.data)
            }
        }

    }

    private fun loadTopGames(){
        homeViewModel.topGames.observe(viewLifecycleOwner) { games ->
            if(games !is Resource.Loading)
                binding.noDataHotGame.visibility = if (games.data == null) View.VISIBLE else View.GONE
            loadData(games) {
                cardGameAdapter.differ.submitList(games.data)
            }
        }
    }

    private fun loadData(games: Resource<List<GameModel>>, success: () -> Unit){
        when(games){
            is Resource.Loading -> {
                showLoading()
            }
            is Resource.Success -> {
                hideLoading()
                success()
            }
            is Resource.Error -> {
                hideLoading()
                Log.d(tag, getString(R.string.error, games.message))
            }
        }
    }
}