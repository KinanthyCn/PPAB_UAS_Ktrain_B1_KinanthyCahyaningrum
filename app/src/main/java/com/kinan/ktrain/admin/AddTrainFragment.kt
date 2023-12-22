package com.kinan.ktrain.admin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.kinan.ktrain.R
import com.kinan.ktrain.database.Ktrain
import com.kinan.ktrain.databinding.FragmentAddTrainBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddTrainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddTrainFragment : Fragment() {
    private val binding by lazy {
        FragmentAddTrainBinding.inflate(layoutInflater)
    }
    private val firestore = FirebaseFirestore.getInstance()
    private val trainCollectionRef = firestore.collection("train")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding){
            btnAddTrain.setOnClickListener{
                val train = Ktrain(
                    departure = edtTextStation.text.toString(),
                    destination = edtTextDestination.text.toString(),
                    train = edtTextTrain.text.toString(),
                    classTrain = edtTextClass.text.toString(),
                )
                trainCollectionRef.add(train).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Success add on database", Toast.LENGTH_SHORT).show()
                    train.id = it.id
                    it.set(train)
                    val action = AddTrainFragmentDirections.actionAddTrainFragmentToDashboadAdminFragment()
                    findNavController().navigate(action)
                }.addOnFailureListener{
                    Toast.makeText(requireContext(), "Failed add on database", Toast.LENGTH_SHORT).show()
                }




            }
        }
    }

}