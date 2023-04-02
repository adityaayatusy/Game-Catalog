package com.aditya.dicoding.gamecatalog.core.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.aditya.dicoding.gamecatalog.core.R
import com.aditya.dicoding.gamecatalog.core.databinding.ItemCardGameBinding
import com.aditya.dicoding.gamecatalog.core.domain.model.GameModel
import com.aditya.dicoding.gamecatalog.core.utils.afterComa
import com.aditya.dicoding.gamecatalog.core.utils.dateFormat
import com.aditya.dicoding.gamecatalog.core.utils.has
import com.bumptech.glide.Glide

class CardGameAdapter(
    private val gameListenerInterface: GameListenerInterface
): RecyclerView.Adapter<CardGameAdapter.ListGameHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListGameHolder {
        context = parent.context
        val binding = ItemCardGameBinding.inflate(LayoutInflater.from(context), parent, false)
        return ListGameHolder(binding)
    }

    override fun onBindViewHolder(holder: ListGameHolder, position: Int) {
        val data = differ.currentList[position]

        with(holder.binding){

            cardTitle.text = data.name
            Glide.with(context)
                .load(data.image)
                .error(R.drawable.no_image)
                .into(cardImage)

            rating.text = context.getString(R.string.rating, data.rating.afterComa("#.#").toString())

            cardTime.text = data.released.dateFormat()

            data.platforms.forEach { platform ->
                when{
                    platform.name.has("pc") -> listIcon.windwosImage.visibility = View.VISIBLE
                    platform.name.has("playstation") -> listIcon.psImage.visibility = View.VISIBLE
                    platform.name.has("xbox") -> listIcon.xboxImage.visibility = View.VISIBLE
                    platform.name.has("macos") -> listIcon.macImage.visibility = View.VISIBLE
                    platform.name.has("linux") -> listIcon.linuxImage.visibility = View.VISIBLE
                    platform.name.has("nintendo") -> listIcon.nitendoImage.visibility = View.VISIBLE
                    platform.name.has("android") -> listIcon.androidImage.visibility = View.VISIBLE
                    platform.name.has("ios") -> listIcon.iosImage.visibility = View.VISIBLE
                }
            }

            if(data.isFavorite){
                favoriteIcon.setImageResource(R.drawable.baseline_bookmark_24)
            }

            containerCard.setOnClickListener {
                gameListenerInterface.onDetailClicked(data)
            }
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    inner class ListGameHolder(var binding: ItemCardGameBinding): ViewHolder(binding.root)

    private val differCallback = object : DiffUtil.ItemCallback<GameModel>(){
        override fun areItemsTheSame(oldItem: GameModel, newItem: GameModel): Boolean = oldItem.gameId == newItem.gameId
        override fun areContentsTheSame(oldItem: GameModel, newItem: GameModel): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)
}