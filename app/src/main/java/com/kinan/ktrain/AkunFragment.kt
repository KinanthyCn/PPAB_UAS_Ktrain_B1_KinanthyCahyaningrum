package com.kinan.ktrain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kinan.ktrain.authentication.PrefManager
import com.kinan.ktrain.database.TrainDB
import com.kinan.ktrain.database.TrainDao
import com.kinan.ktrain.databinding.FragmentAkunBinding
import java.util.concurrent.ExecutorService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 */
class AkunFragment : Fragment() {
    private lateinit var binding: FragmentAkunBinding
    private lateinit var prefManager: PrefManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAkunBinding.inflate(inflater, container, false)
        prefManager = PrefManager.getInstance(requireContext())
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()




        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = firestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
        with(binding) {


            btnLogout.setOnClickListener {
                firebaseAuth.signOut()
                prefManager.clear()
                requireActivity().finish()
            }
            user.get().addOnSuccessListener { document ->
                if (document != null) {
                    nameAkun.text = document.getString("name")
                    textNim.text = document.getString("nim")

                }
            }
        }

    }
}