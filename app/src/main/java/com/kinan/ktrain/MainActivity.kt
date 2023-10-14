package com.kinan.ktrain

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.kinan.ktrain.databinding.ActivityMainBinding
import java.util.Calendar

class MainActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityMainBinding
    var yearOfBirth = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        with(binding){
            val calendar = Calendar.getInstance().get(Calendar.YEAR)
            showDatePicker.setOnClickListener{
                val DatePicker = DatePicker()
                DatePicker.show(supportFragmentManager, "datePicker")
            }

            btnSubmit.setOnClickListener{
                val data = Bundle()
                val username = username.text
                val email = Email.text
                val password = Password.text
                if (yearOfBirth != 0 && !username.isEmpty() && !email.isEmpty() && !password.isEmpty()){
                    if (calendar - yearOfBirth >= 15) {
                        data.putString("NAME", username.toString())
                        data.putString("EMAIL", email.toString())
                        data.putString("PASSWORD", password.toString())
                        val intent = Intent(
                            this@MainActivity,
                            LoginActivity::class.java
                        ).apply { putExtras(data) }
                        startActivity(intent)
                    } else {
                        Toast.makeText(this@MainActivity, "You're still bocil", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@MainActivity, "Please fill in the form", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

//    fungsi ini dipanggil setiap ganti ganti tanggal di dialog
    override fun onDateSet(view: android.widget.DatePicker, Year: Int, Month: Int, dayOfMonth: Int) {
        binding.showDatePicker.text = "$Year-${Month+1}-$dayOfMonth"
        yearOfBirth = Year
    }
}