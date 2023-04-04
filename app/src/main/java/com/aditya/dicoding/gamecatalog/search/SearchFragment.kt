package com.aditya.dicoding.gamecatalog.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.dicoding.gamecatalog.BaseFragment
import com.aditya.dicoding.gamecatalog.R
import com.aditya.dicoding.gamecatalog.core.data.Resource
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.ui.GameListenerInterface
import com.aditya.dicoding.gamecatalog.core.ui.ListGameAdapter
import com.aditya.dicoding.gamecatalog.databinding.FragmentSearchBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import timber.log.Timber

@AndroidEntryPoint
class SearchFragment : BaseFragment<FragmentSearchBinding>(), GameListenerInterface {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentSearchBinding = FragmentSearchBinding::inflate
    private val searchViewModel: SearchViewModel by viewModels()
    private val listGameAdapter = ListGameAdapter(this@SearchFragment)
    private val args: SearchFragmentArgs by navArgs()
    private var job: Job? = null

    override fun setup() {
        searchViewModel.isDark.observe(viewLifecycleOwner) {
            changeTheme(it)
        }

        with(binding){
            btnBack.setOnClickListener {
                findNavController().navigateUp()
            }

            loadSearch(args.keywords)
            search.setText(args.keywords)
            search.addTextChangedListener {
                if (it.toString().isEmpty()){
                    findNavController().navigateUp()
                }
                job?.cancel()
                job = lifecycleScope.launch {
                    delay(500)
                    loadSearch(it.toString())
                }
            }
        }


        loadRecycleView()
    }

    private fun loadSearch(keyword: String){
        listGameAdapter.differ.submitList(null)
        binding.titleGames.text = getString(R.string.search_title, keyword)

        searchViewModel.search(keyword).observe(viewLifecycleOwner){ games ->
            if(games !is Resource.Loading)
                binding.noDataGame.visibility = if (games.data == null) View.VISIBLE else View.GONE

            if (games != null) {
                when(games){
                    is Resource.Loading -> {
                        showLoading()
                    }
                    is Resource.Success -> {
                        hideLoading()
                        listGameAdapter.differ.submitList(games.data)
                    }
                    is Resource.Error -> {
                        hideLoading()
                        Timber.e(getString(R.string.error, games.message))
                    }
                }
            }
        }
    }

    private fun showLoading(){
        with(binding){
            loadingGames.visibility = View.VISIBLE
            listGames.visibility = View.GONE
        }
    }

    private fun hideLoading(){
        with(binding){
            loadingGames.visibility = View.GONE
            listGames.visibility = View.VISIBLE
        }
    }

    override fun onDetailClicked(gameModel: GameModel) {
        val toDetail = SearchFragmentDirections.actionSearchFragmentToDetailFragment(gameModel).setIsSearch(true)
        findNavController().navigate(toDetail)
    }

    private fun loadRecycleView(){
        with(binding){
            with(listGames){
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = listGameAdapter
            }
        }
    }

    override fun destroy() {
        binding.listGames.adapter = null
    }
}