package com.kinan.ktrain.admin

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kinan.ktrain.R
import com.kinan.ktrain.adapter.TrainAdapter
import com.kinan.ktrain.database.Ktrain
import com.kinan.ktrain.databinding.FragmentDashboadAdminBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Dashboad_admin_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboad_admin_Fragment : Fragment() {
    private val binding by lazy {
        FragmentDashboadAdminBinding.inflate(layoutInflater)
    }
    private val firestore = FirebaseFirestore.getInstance()
    private val trainCollectionRef = firestore.collection("train")


    private val adapterTrain = TrainAdapter(
        listTrain = emptyList<Ktrain>().toMutableList(),
        onClickUpdate = {
            val action = Dashboad_admin_FragmentDirections.actionDashboadAdminFragmentToUpdateTrainFragment(
                it.id, it.departure, it.destination, it.train, it.classTrain
            )
            findNavController().navigate(action)

            // how to use that in target fragment?
            // val args = UpdateTrainFragmentArgs.fromBundle(requireArguments())
        },
        onClickDelete = {
            trainCollectionRef.document(it.id).delete().addOnSuccessListener {
                updateListTrain()
            }
        }
    )

    private fun updateListTrain() {
        trainCollectionRef.get(Source.SERVER).addOnSuccessListener {
            val listTrain = it.toObjects(Ktrain::class.java)
            adapterTrain.listTrain.clear()
            adapterTrain.listTrain.addAll(listTrain)

            Log.d("RecyclerKereta", "onResume: ${adapterTrain.listTrain.size}")

            adapterTrain.notifyDataSetChanged()
        }.addOnFailureListener {
            Log.d("RecyclerKereta", "onResume: ${it.message}")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return binding.root
    }


    override fun onResume() {
        super.onResume()

        updateListTrain()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dbTrain = Firebase.firestore.collection("train")

        dbTrain.get(Source.SERVER).addOnSuccessListener {
            val listTrain = it.toObjects(Ktrain::class.java)

            memasukkanDataListKeretaDariFirebaseKeDalamAdapterRecyclerViewDasborAdmin(listTrain)
        }
        with(binding) {
            addTrain.setOnClickListener {
                findNavController().navigate(R.id.action_dashboad_admin_Fragment_to_addTrainFragment)
            }
        }



    }

    private fun memasukkanDataListKeretaDariFirebaseKeDalamAdapterRecyclerViewDasborAdmin(listTrain: List<Ktrain>) {
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterTrain
        }

        adapterTrain.listTrain.clear()
        adapterTrain.listTrain.addAll(listTrain)
        adapterTrain.notifyDataSetChanged()
    }

}

