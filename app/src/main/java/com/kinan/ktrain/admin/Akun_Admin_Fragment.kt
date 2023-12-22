package com.kinan.ktrain.admin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kinan.ktrain.R
import com.kinan.ktrain.authentication.PrefManager
import com.kinan.ktrain.databinding.FragmentAkunAdminBinding

/**
 * A simple [Fragment] subclass.
 * Use the [Akun_Admin_Fragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Akun_Admin_Fragment : Fragment() {
    private val binding by lazy {
        FragmentAkunAdminBinding.inflate(layoutInflater)
    }
    private lateinit var prefManager: PrefManager
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        prefManager = PrefManager.getInstance(requireContext())
        firebaseAuth = FirebaseAuth.getInstance()
        firestore = Firebase.firestore


        val user = firestore.collection("users").document(firebaseAuth.currentUser?.uid.toString())
        with(binding){
            btnLogoutAdmin.setOnClickListener {
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