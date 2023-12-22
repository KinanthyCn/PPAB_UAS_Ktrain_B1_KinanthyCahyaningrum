package com.kinan.ktrain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinan.ktrain.database.Paket
import com.kinan.ktrain.databinding.FragmentHistoryBinding
import com.kinan.ktrain.databinding.ItemPerjalananBinding

typealias onClick = (Paket) -> Unit
class RiwayatAdapter (
    val listHistory:  MutableList<Paket>,
    private val onClick: onClick) :
    RecyclerView.Adapter<RiwayatAdapter.RiwayatViewHolder>(){
    inner class RiwayatViewHolder (private val binding: ItemPerjalananBinding) :
    RecyclerView.ViewHolder(binding.root){
        fun bind(history: Paket){
            with(binding){


                namaKereta.text = history.train
                asalPerjalanan.text = "Departure\n${history.departure}"
                tujuanPerjalanan.text = "Destination\n${history.destination}"
                classKereta.text = "Class\n${history.classTrain}"
                tanggalPerjalanan.text = "Date\n${history.date}"
                isiPaket.text = "Package\n${history.paket}"

                btnFavorite.setOnClickListener {
                    onClick(history)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RiwayatViewHolder {
        val binding = ItemPerjalananBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RiwayatViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listHistory.size
    }

    override fun onBindViewHolder(holder: RiwayatViewHolder, position: Int) {
        holder.bind(listHistory[position])
    }

}
