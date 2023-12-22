package com.kinan.ktrain.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kinan.ktrain.database.Ktrain
import com.kinan.ktrain.databinding.FragmentItemListDialogListDialogItemBinding

typealias OnClick = (Ktrain) -> Unit

class BookingAdapter (
    private val listBooking: MutableList<Ktrain>,
    private val onClick: OnClick) :
    RecyclerView.Adapter<BookingAdapter.BookingViewHolder>(){

        inner class BookingViewHolder(private val binding: FragmentItemListDialogListDialogItemBinding) :
            RecyclerView.ViewHolder(binding.root) {
                fun bind(booking: Ktrain) {
                    with(binding) {
                        txtTrain.text = booking.train
                        txtDeparture.text = "Destination\n${booking.destination}"
                        txtDestination.text = "Departure\n${booking.departure}"
                        txtPrice.text = "Class\n${booking.classTrain}"

                        itemView.setOnClickListener {
                            onClick(booking)
                        }


                    }
                }
            }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookingViewHolder {
        val binding = FragmentItemListDialogListDialogItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BookingViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listBooking.size
    }

    override fun onBindViewHolder(holder: BookingViewHolder, position: Int) {
        holder.bind(listBooking[position])
    }
}
