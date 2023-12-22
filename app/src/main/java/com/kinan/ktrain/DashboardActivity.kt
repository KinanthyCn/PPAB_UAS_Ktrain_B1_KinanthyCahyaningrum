//package com.kinan.ktrain
//
//import android.content.Intent
//import androidx.appcompat.app.AppCompatActivity
//import android.os.Bundle
//import android.util.Log
//import androidx.activity.result.contract.ActivityResultContracts
//import com.kinan.ktrain.databinding.ActivityDashboardBinding
//import java.text.SimpleDateFormat
//import java.util.Calendar
//import java.util.Locale
//
//class DashboardActivity : AppCompatActivity() {
//    private lateinit var binding: Binding
//
//    private val launcher =
////        Launcher dijalanin ketika balik ke halaman ini
//        registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
//            result ->
////            Ngambil bundle dari BookingActivity
//            val data = result.data?.extras
//
//            with(binding){
//                asalPerjalanan.text = data?.getString("ASAL")
//                tujuanPerjalanan.text = data?.getString("TUJUAN")
//                namaKereta.text = data?.getString("KERETA")
//                classKereta.text = data?.getString("KELAS")
////                Ngeformat tanggal, dari yang tadi dikirim bentuk Integer, di format lagi jadi bentuk tahun - bulan - tanggal
//                val dateFormat = SimpleDateFormat("y MMMM dd", Locale.getDefault())
//                val formattedDate = dateFormat.format(Calendar.getInstance().apply {
//                    if (data != null) {
//                        set(Calendar.YEAR, data.getInt("YEAR"))
//                    }
//                    if (data != null) {
//                        set(Calendar.MONTH, data.getInt("MONTH"))
//                    }
//                    if (data != null) {
//                        set(Calendar.DAY_OF_MONTH, data.getInt("DATE"))
//                    }
//                }.time)
//                tanggalPerjalanan.text = formattedDate.toString()
////                Nambahin list paket yang dipilih
//                val paket = data?.getStringArray("PAKET")
//                var listPaket = ""
//                if (paket != null) {
//                    Log.d("paket", "Masuk")
//
//                    for (i in paket){
//                        Log.d("paket", i)
//                        listPaket += "- ${i}\n"
//                    }
//                }
//                isiPaket.text = listPaket
//            }
//        }
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        binding = ActivityDashboardBinding.inflate(layoutInflater)
//        super.onCreate(savedInstanceState)
//        setContentView(binding.root)
//
//
//        with(binding){
//           textView3.text = intent.getStringExtra("NAME")
//            btnStart.setOnClickListener{
//                intent = Intent(this@DashboardActivity, BookingActivity::class.java)
//                launcher.launch(intent)
//            }
//        }
//    }
//}