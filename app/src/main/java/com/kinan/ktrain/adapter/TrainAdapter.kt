package com.kinan.ktrain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinan.ktrain.database.Ktrain
import com.kinan.ktrain.databinding.ItemAdminBinding

typealias OnClickUpdate = (Ktrain) -> Unit
typealias OnClickDelete = (Ktrain) -> Unit

class TrainAdapter (
    val listTrain: MutableList<Ktrain>,
    private val onClickUpdate: OnClickUpdate,
    private val onClickDelete: OnClickDelete) :
    RecyclerView.Adapter<TrainAdapter.TrainViewHolder>() {

        inner class TrainViewHolder(private val binding: ItemAdminBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(train: Ktrain) {
                    with(binding) {
                        namaKereta.text = train.train
                        asalPerjalanan.text = "Destination\n${train.destination}"
                        tujuanPerjalanan.text = "Departure\n${train.departure}"
                        classKereta.text = "Class\n${train.classTrain}"

                        updateButton.setOnClickListener {
                            onClickUpdate(train)
                        }
                        deleteTrain.setOnClickListener {
                            onClickDelete(train)
                        }

                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainViewHolder {
        val binding = ItemAdminBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return TrainViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listTrain.size
    }

    override fun onBindViewHolder(holder: TrainViewHolder, position: Int) {
        holder.bind(listTrain[position])
    }

    }

