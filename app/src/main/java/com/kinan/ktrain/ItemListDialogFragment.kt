package com.kinan.ktrain

import android.os.Bundle
import android.util.Log
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.kinan.ktrain.adapter.BookingAdapter
import com.kinan.ktrain.database.Ktrain
import com.kinan.ktrain.databinding.FragmentItemListDialogListDialogBinding

// TODO: Customize parameter argument names
const val ARG_ITEM_COUNT = "item_count"

/**
 *
 * A fragment that shows a list of items as a modal bottom sheet.
 *
 * You can show this modal bottom sheet from your activity like this:
 * <pre>
 *    ItemListDialogFragment.newInstance(30).show(supportFragmentManager, "dialog")
 * </pre>
 */
class ItemListDialogFragment(
    private val setText : (Ktrain) -> Unit
) : BottomSheetDialogFragment() {

    private var firestore = FirebaseFirestore.getInstance()

    private val binding by lazy {
        FragmentItemListDialogListDialogBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        with(binding) {

            firestore.collection("train").get(Source.SERVER).addOnSuccessListener { querySnapshot ->
                val listKereta = querySnapshot.toObjects(Ktrain::class.java)
                setupRecyclerView(listKereta)
            }.addOnFailureListener {
                Log.d("Dialog", "onViewCreated: ${it.message}")
            }




        }
    }

    private fun setupRecyclerView(listKereta: List<Ktrain>) {
        with(binding) {
            list.layoutManager = LinearLayoutManager(requireActivity())
            list.adapter = BookingAdapter(
                listBooking = listKereta.toMutableList(),
                onClick = {
                    setText(it)
                    dismiss()
                }
            )
        }
    }

}
