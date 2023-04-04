package com.aditya.dicoding.gamecatalog.detail

import android.view.*
import androidx.core.view.MenuProvider
import androidx.core.view.get
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.aditya.dicoding.gamecatalog.BaseFragment
import com.aditya.dicoding.gamecatalog.R
import com.aditya.dicoding.gamecatalog.core.utils.afterComa
import com.aditya.dicoding.gamecatalog.core.utils.dateFormat
import com.aditya.dicoding.gamecatalog.databinding.FragmentDetailBinding
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>(), MenuProvider {
    private val args: DetailFragmentArgs by navArgs()
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding = FragmentDetailBinding::inflate
    private val detailViewMode: DetailViewModel by viewModels()

    override fun setup() {
        with(binding){
            with(mainActivity){
                setSupportActionBar(toolbar.apply {
                    setNavigationIcon(R.drawable.baseline_chevron_left_24)
                })
                supportActionBar?.apply {
                    title = ""
                    setDisplayHomeAsUpEnabled(true)
                    setDisplayShowHomeEnabled(true)
                }

                addMenuProvider(this@DetailFragment, viewLifecycleOwner, Lifecycle.State.CREATED)
            }


            Glide.with(this@DetailFragment)
                .load(args.gameDetail.image)
                .error(com.aditya.dicoding.gamecatalog.core.R.drawable.no_image)
                .into(imageGame)
            title.text = args.gameDetail.name
            desc.text = args.gameDetail.description
            platform.text = args.gameDetail.platforms.joinToString {
                it.name
            }
            rating.text = context?.getString(R.string.rating, args.gameDetail.rating.afterComa("#.#").toString()) ?: "0/5"
            releaseDate.text = args.gameDetail.released.dateFormat()

            if(args.gameDetail.description == null){
                if(args.isSearch){
                    getSearchDetail()
                }else{
                    getDetail()
                }
            }else{
                hideLoading()
            }
        }
    }


    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.favorite, menu)

        if(args.gameDetail.isFavorite){
            menu[0].setIcon(R.drawable.baseline_bookmark_24)
        }
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when(menuItem.itemId){
            android.R.id.home -> {
                lifecycleScope.launchWhenResumed {
                    findNavController().popBackStack()
                }
            }

            R.id.bookmark -> {
                if(args.gameDetail.isFavorite){
                    args.gameDetail.isFavorite = false
                    menuItem.setIcon(R.drawable.baseline_bookmark_border_24)
                }else{
                    args.gameDetail.isFavorite = true
                    menuItem.setIcon(R.drawable.baseline_bookmark_24)
                }
                detailViewMode.setFavoriteGame(args.gameDetail, args.gameDetail.isFavorite, args.isSearch)

            }
        }
        return true
    }

    private fun showLoading(){
        with(binding){
            loading.visibility = View.VISIBLE
            desc.visibility = View.GONE
        }
    }

    private fun hideLoading(){
        with(binding){
            loading.visibility = View.GONE
            desc.visibility = View.VISIBLE
        }
    }

    private fun getDetail(){
        with(binding){
            detailViewMode.getDetailGame(args.gameDetail).observe(viewLifecycleOwner) {
                if (it != null) {
                    when(it){
                        is com.aditya.dicoding.gamecatalog.core.data.Resource.Loading -> {
                            showLoading()
                        }
                        is com.aditya.dicoding.gamecatalog.core.data.Resource.Success -> {
                            hideLoading()
                            val description = it.data?.description
                            args.gameDetail.description = description
                            desc.text = description
                        }
                        is com.aditya.dicoding.gamecatalog.core.data.Resource.Error -> {
                            hideLoading()
                            Timber.e("error: "+ it.message)
                        }
                    }
                }
            }
        }
    }

    private fun getSearchDetail(){
        with(binding){
            detailViewMode.getSearchDetailGame(args.gameDetail).observe(viewLifecycleOwner) {
                if (it != null) {
                    when(it){
                        is com.aditya.dicoding.gamecatalog.core.data.Resource.Loading -> {
                            showLoading()
                        }
                        is com.aditya.dicoding.gamecatalog.core.data.Resource.Success -> {
                            hideLoading()
                            val description = it.data?.description
                            args.gameDetail.description = description
                            desc.text = description
                        }
                        is com.aditya.dicoding.gamecatalog.core.data.Resource.Error -> {
                            hideLoading()
                            Timber.e(getString(R.string.error, it.message))
                        }
                    }
                }
            }
        }
    }


    override fun destroy(){
        mainActivity.setSupportActionBar(null)
    }
}