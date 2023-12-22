package com.kinan.ktrain.authentication

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.kinan.ktrain.NonAdminActivity
import com.kinan.ktrain.R
import com.kinan.ktrain.database.Users
import com.kinan.ktrain.databinding.FragmentRegisterBinding
import java.text.SimpleDateFormat

class RegisterFragment : Fragment() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    private lateinit var binding: FragmentRegisterBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val view = binding.root
        mAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText("Select date")
            .build()
        // Inflate the layout for this fragment
        val simpleDateFormat = SimpleDateFormat("dd/MM/yyyy")

        with(binding) {
            signUp.setOnClickListener {
                (requireActivity() as LogRegScreen).switchFragment(1)
            }

            btnDateBirth.setOnClickListener {
                datePicker.addOnPositiveButtonClickListener {
                    val date = simpleDateFormat.format(datePicker.selection)
                    btnDateBirth.text = date
                }
                datePicker.show(requireActivity().supportFragmentManager, "DATE_PICKER")
            }





        }

        val usernameEditText: EditText = view?.findViewById(R.id.username) ?: return view
        val emailEditText: EditText = view?.findViewById(R.id.Email)?: return view
        val passwordEditText: EditText = view?.findViewById(R.id.Password)?:return view
        val signUpButton: Button = view?.findViewById(R.id.btn_submit)?:return view
        val nimEditTxt: EditText = view?.findViewById(R.id.nim)?:return view

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()
            val nim = nimEditTxt.text.toString()


            if (username.isEmpty()) {
                Toast.makeText(this@RegisterFragment.requireActivity(), "Username cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Check if the password meets the minimum length requirement
            if (password.length < 8) {
                Toast.makeText(this@RegisterFragment.requireActivity(), "Password must be at least 8 characters long", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if(email.isEmpty()) {
                Toast.makeText(
                    this@RegisterFragment.requireActivity(),
                    "Email cannot be empty",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (nim.isEmpty()) {
                Toast.makeText( this@RegisterFragment.requireActivity(), "NIM cannot be empty", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }


            // Daftarkan pengguna dengan email dan password
            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Daftar sukses, simpan data tambahan ke Firestore
                        val user = Users(
                            uid = task.result?.user?.uid ?: "",
                            username = username,
                            email = email,
                            password = password,
                            dateOfBirth = binding.btnDateBirth.text.toString(),
                            role = "user",
                            name = binding.name.text.toString(),
                            nim = binding.nim.text.toString()
                        )

                        firestore.collection("users")
                            .document(mAuth.currentUser?.uid ?: "")
                            .set(user)
                            .addOnSuccessListener {

                                val intent = Intent(this@RegisterFragment.requireActivity(), NonAdminActivity::class.java).apply {
                                    Intent.FLAG_ACTIVITY_NEW_TASK
                                }
                                setEmptyField()
                                startActivity(intent)
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(this@RegisterFragment.requireActivity(), "Gagal menambah data users", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(this@RegisterFragment.requireActivity(), "Gagal melakukan registrasi", Toast.LENGTH_SHORT).show()
                    }
                }
        }

        return view
    }
    private fun setEmptyField() {
        with(binding) {
            username.setText("")
            Email.setText("")
            Password.setText("")
            btnDateBirth.text = ""
            name.setText("")
            nim.setText("")
        }
    }


}