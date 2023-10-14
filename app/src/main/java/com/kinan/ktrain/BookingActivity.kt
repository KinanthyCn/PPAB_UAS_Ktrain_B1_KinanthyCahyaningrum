package com.kinan.ktrain

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.DatePicker
import com.kinan.ktrain.databinding.ActivityBookingBinding
import kotlin.math.tan

class BookingActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var binding: ActivityBookingBinding
    var year = 0
    var month = 0
    var dateOfMonth = 0
    var isChanged = false
//    tempat naruh paket yang digunain
    var paket = arrayOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityBookingBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapterKereta = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Kereta))
        adapterKereta.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        val adapterAsal = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Kota))
        adapterAsal.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        val adapterTujuan = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Kota))
        adapterTujuan.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        val adapterKelas = ArrayAdapter(this, android.R.layout.simple_spinner_item, resources.getStringArray(R.array.Class))
        adapterKelas.setDropDownViewResource(com.google.android.material.R.layout.support_simple_spinner_dropdown_item)

        with(binding){
            val dataPesanan = Bundle()
            var inputKelas = resources.getStringArray(R.array.Class)[0]

            asal.adapter = adapterAsal
            tujuan.adapter = adapterTujuan
            train.adapter = adapterKereta
            classTrain.adapter = adapterKelas
//Buat ngeganti harga berdasarkan class yang dipilih
            classTrain.onItemSelectedListener =
                object : AdapterView.OnItemSelectedListener{
                    override fun onItemSelected(
                        parent: AdapterView<*>?,
                        view: View?,
                        position: Int,
                        id: Long
                    ) {
                        inputKelas = resources.getStringArray(R.array.Class)[position]
                        when (position){
                            0, 1 -> harga.text = "Rp 1.000.000,00"
                            2 -> harga.text = "Rp 500.000,00"
                            4, 5 -> harga.text = "Rp 200.000,00"
                            6 -> harga.text = "Rp 80.000,00"
                        }
                        isChanged = true
                    }

                    override fun onNothingSelected(parent: AdapterView<*>?) {
                    }

                }

            showDatePicker.setOnClickListener{
                val DatePicker = DatePicker()
                DatePicker.show(supportFragmentManager, "DatePicker")
            }
//            To do : tambahin sistem biar tanggal harus diisi dlu
            btnBooking.setOnClickListener{
                val inputAsal = asal.selectedItem.toString()
                val inputTujuan = tujuan.selectedItem.toString()
                val inputKereta = train.selectedItem.toString()

//                Ngecek paket apa aja yang dpake. Kali dipake, ditambahin ke list paket
                if(switch2.isChecked){
                    Log.d("Paket 2", "Masuk")
                    paket += switch2.text.toString()
                }
                if(switch3.isChecked){
                    Log.d("Paket 3", "Masuk")
                    paket += switch3.text.toString()
                }
                if(switch4.isChecked){
                    paket += switch4.text.toString()
                }
                if(switch5.isChecked){
                    paket += switch5.text.toString()
                }
                if(switch6.isChecked){
                    paket += switch6.text.toString()
                }
                if(switch7.isChecked){
                    paket += switch7.text.toString()
                }
                if(switch8.isChecked){
                    paket += switch8.text.toString()
                }

//                Nambahin data2 lain
                dataPesanan.putString("ASAL", inputAsal)
                dataPesanan.putString("TUJUAN", inputTujuan)
                dataPesanan.putString("KERETA", inputKereta)
                dataPesanan.putString("KELAS", inputKelas)
                dataPesanan.putInt("YEAR", year)
                dataPesanan.putInt("MONTH", month)
                dataPesanan.putInt("DATE", dateOfMonth)
                dataPesanan.putStringArray("PAKET", paket)

                val intent = Intent().apply { putExtras(dataPesanan) }
                setResult(Activity.RESULT_OK, intent)
                finish()
            }



        }
    }

    override fun onDateSet(p0: DatePicker?, tahun: Int, bulan: Int, tanggal: Int) {
        year = tahun
        month = bulan
        dateOfMonth = tanggal
        binding.showDatePicker.text = "$tahun-${bulan+1}-$tanggal"
    }
}