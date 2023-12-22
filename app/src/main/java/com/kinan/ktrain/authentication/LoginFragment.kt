import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kinan.ktrain.NonAdminActivity
import com.kinan.ktrain.admin.AdminActivity
import com.kinan.ktrain.authentication.LogRegScreen
import com.kinan.ktrain.authentication.PrefManager
import com.kinan.ktrain.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var binding: FragmentLoginBinding
    private lateinit var prefManager: PrefManager

    private val firebaseUser by lazy{
        Firebase.firestore.collection("users")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()
        prefManager = PrefManager.getInstance(requireContext())
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(prefManager.isLoggedIn()){
            firebaseAuth.signInWithEmailAndPassword(
                prefManager.getEmail().toString(),
                prefManager.getPassword().toString()
            ).addOnSuccessListener { authResult ->
                // Check user role and navigate accordingly
                val userUid = authResult.user?.uid
                if (userUid != null) {
                    prefManager.saveId(userUid)
                    // TODO: Panggil user yang punya id userUid
                    // dapetin role nya
                    firebaseUser.document(userUid).get().addOnSuccessListener {
                        val userRole = it.getString("role")

                        checkUserRole(userRole.toString())
                    }

                }
            }.addOnFailureListener { exception ->
                // Handle authentication failure here
                Toast.makeText(
                    requireContext(),
                    "Authentication failed: ${exception.message}",
                    Toast.LENGTH_SHORT
                ).show()
                Log.d("LoginFragment", "onViewCreated: ${exception.message}")
            }
        }
        with(binding) {

            signUp.setOnClickListener(View.OnClickListener {
                (requireActivity() as LogRegScreen).switchFragment(0)
            })
            btnLogin.setOnClickListener {
                if (EmailLogin.text.isNullOrEmpty()) {
                    EmailLogin.error = "Email tidak boleh kosong"
                } else if (PasswordLogin.text.isNullOrEmpty()) {
                    PasswordLogin.error = "Password tidak boleh kosong"
                } else {
                    firebaseAuth.signInWithEmailAndPassword(
                        EmailLogin.text.toString(),
                        PasswordLogin.text.toString(),
                    ).addOnSuccessListener { authResult ->
                        // Check user role and navigate accordingly
                        val userUid = authResult.user?.uid
                        if (checkboxLogin.isChecked) {
                            prefManager.saveEmail(EmailLogin.text.toString())
                            prefManager.savePassword(PasswordLogin.text.toString())
                            prefManager.setLoggedIn(true)
                        }
                        if (userUid != null) {
                            prefManager.saveId(userUid)

                            // TODO: Panggil user yang punya id userUid
                            // dapetin role nya
                            firebaseUser.document(userUid).get().addOnSuccessListener {
                                val userRole = it.getString("role")
                                checkUserRole(userRole.toString())
                            }

                        }

                    }.addOnFailureListener { exception ->
                        // Handle authentication failure here
                        Toast.makeText(
                            requireContext(),
                            "Authentication failed: ${exception.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.d("LoginFragment", "onViewCreated: ${exception.message}")
                    }
                }
            }
        }
    }

    private fun checkUserRole(userRole: String) {
        // Retrieve user role from Firestore or another storage mechanism
        // For demonstration purposes, assuming 'user' and 'admin'
//        val userRole = "user" // Replace with your logic to get user role

        // Save user role to shared preferences
        prefManager.saveRole(userRole)


        // Navigate to the appropriate activity based on user role
        val intentToMain = if (userRole == "admin") {
            Intent(requireActivity(), AdminActivity::class.java)
        } else {
            Intent(requireActivity(), NonAdminActivity::class.java)
        }
//
        startActivity(intentToMain)
    }
}



