package com.kinan.ktrain

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kinan.ktrain.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val accountData = intent.extras

        with(binding){
            btnLogin.setOnClickListener{
                val username = usernameLogin.text.toString()
                val password = PasswordLogin.text.toString()
                val accName = accountData?.getString("NAME")
                val accPassword = accountData?.getString("PASSWORD")

                if (accName == username && accPassword == password){
                    val intent = Intent(this@LoginActivity, DashboardActivity::class.java)
                    intent.putExtra("NAME", accName)
                    startActivity(intent)
                }
                else{
                    Toast.makeText(this@LoginActivity, "Data akun salah", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}