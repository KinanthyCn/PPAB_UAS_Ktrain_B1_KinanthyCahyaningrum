package com.kinan.ktrain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinan.ktrain.database.TrainDB
import com.kinan.ktrain.databinding.ItemFavoritBinding

typealias OnClickDelete1 = (TrainDB) -> Unit

class FavoritAdapter (
    private val listFavorit: MutableList<TrainDB>,
    private val onClickDelete: OnClickDelete1) :
    RecyclerView.Adapter<FavoritAdapter.FavoritViewHolder>() {

    inner class FavoritViewHolder(private val binding: ItemFavoritBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favorit: TrainDB) {
            with(binding) {
                namaKereta.text = favorit.train
                asalPerjalanan.text = "Destination\n${favorit.destination}"
                tujuanPerjalanan.text = "Departure\n${favorit.departure}"
                classKereta.text = "Class\n${favorit.classTrain}"

                deleteTrain.setOnClickListener {
                    onClickDelete(favorit)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritViewHolder {
        val binding = ItemFavoritBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return FavoritViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavorit.size
    }

    override fun onBindViewHolder(holder: FavoritViewHolder, position: Int) {
        holder.bind(listFavorit[position])
    }
}