package com.kinan.ktrain.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.firestore.FirebaseFirestore
import com.kinan.ktrain.R
import com.kinan.ktrain.database.Ktrain
import com.kinan.ktrain.databinding.FragmentUpdateBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [UpdateTrainFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class UpdateTrainFragment : Fragment() {
    private lateinit var binding: FragmentUpdateBinding
    private val firestore = FirebaseFirestore.getInstance()
    private val trainCollectionRef = firestore.collection("train")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUpdateBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = UpdateTrainFragmentArgs.fromBundle(requireArguments())
        val id = args.id
        val departure = args.departure
        val destination = args.destination
        val train = args.train


        with(binding) {
            binding.edtTextStation.setText(departure)
            binding.edtTextDestination.setText(destination)
            binding.edtTextTrain.setText(train)


            btnUpdate.setOnClickListener {
                val train = Ktrain(
                    id = id,
                    departure = edtTextStation.text.toString(),
                    destination = edtTextDestination.text.toString(),
                    train = edtTextTrain.text.toString(),
                    classTrain = edtTextClass.text.toString(),
                )

                trainCollectionRef.document(id).set(train).addOnSuccessListener {
                    Toast.makeText(requireContext(), "Success update on database", Toast.LENGTH_SHORT).show()
                    findNavController().navigateUp()
                }.addOnFailureListener{
                    Toast.makeText(requireContext(), "Failed update on database", Toast.LENGTH_SHORT).show()
                }

            }


        }


    }
}