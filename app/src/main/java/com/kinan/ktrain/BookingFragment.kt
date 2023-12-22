package com.kinan.ktrain

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.textfield.MaterialAutoCompleteTextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kinan.ktrain.authentication.PrefManager
import com.kinan.ktrain.database.Ktrain
import com.kinan.ktrain.database.Paket
import com.kinan.ktrain.databinding.FragmentBookingBinding
import kotlinx.coroutines.tasks.await
import java.text.SimpleDateFormat

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookingFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookingFragment : Fragment() {
   private lateinit var binding: FragmentBookingBinding
   private val firebase by lazy {
       Firebase.firestore
    }
    private lateinit var prefManager: PrefManager
    private val channelID = "Pesanan anda berhasil"//berupa String




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        prefManager = PrefManager.getInstance(requireContext())
        binding = FragmentBookingBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val notifManager = getSystemService(requireContext(), NotificationManager::class.java)
        as NotificationManager


        with(binding) {

            cardTravel.setOnClickListener {
                ItemListDialogFragment(
                    setText = { ktrain ->
                        txtTrain.text = ktrain.train
                        txtDeparture.text = ktrain.departure
                        txtDestination.text = ktrain.destination
                        txtPrice.text = ktrain.classTrain
                    }
                ).show(
                    parentFragmentManager,
                    "ItemListDialogFragment"
                )

                hitungHarga()
            }

            switch2.setOnCheckedChangeListener { _, _ ->
                hitungHarga()
            }
            switch3.setOnCheckedChangeListener { _, _ ->
                hitungHarga()
            }
            switch4.setOnCheckedChangeListener { _, _ ->
                hitungHarga()
            }
            switch5.setOnCheckedChangeListener { _, _ ->
                hitungHarga()
            }
            switch6.setOnCheckedChangeListener { _, _ ->
                hitungHarga()
            }
            switch7.setOnCheckedChangeListener { _, _ ->
                hitungHarga()
            }
            switch8.setOnCheckedChangeListener { _, _ ->
                hitungHarga()
            }




            showDatePicker.setOnClickListener {
                val datePicker = MaterialDatePicker.Builder.datePicker().build()
                datePicker.addOnPositiveButtonClickListener {
                    val date = SimpleDateFormat("dd/MM/yyyy").format(datePicker.selection)
                    showDatePicker.setText(date)
                }
                datePicker.show(requireActivity().supportFragmentManager, "date picker")

            }
            btnBooking.setOnClickListener{
                val userId = prefManager.getId().toString()
                val departure = txtDeparture.text.toString()
                val destination = txtDestination.text.toString()
                val train = txtTrain.text.toString()
                val classTrain = txtPrice.text.toString()
                val showDatePicker = showDatePicker.text.toString()
                val switch2 = if (binding.switch2.isChecked) "Santapan enak" else null
                val switch3 = if (binding.switch3.isChecked) "Discount Package" else null
                val switch4 = if (binding.switch4.isChecked) "Safe Travel kit" else null
                val switch5 = if (binding.switch5.isChecked) "Asuransi Perjalanan" else null
                val switch6 = if (binding.switch6.isChecked) "Hiburan" else null
                val switch7 = if (binding.switch7.isChecked) "Selimut dan Bantal" else null
                val switch8 = if (binding.switch8.isChecked) "Layanan Porter" else null
                val switches = listOf(switch2, switch3, switch4, switch5, switch6, switch7, switch8)

                val paket = switches.filterNotNull().joinToString(", ")

                val paketPembelian = Paket(
                    uid = FirebaseAuth.getInstance().currentUser?.uid.toString(),
                    id = "",
                    departure = departure,
                    destination = destination,
                    train = train,
                    classTrain = classTrain,
                    date = showDatePicker,
                    paket = paket
                )

                val hargaKelasKereta = if (classTrain == "Ekonomi") {
                    80000

                } else if (classTrain == "Bisnis") {
                    100000

                } else if (classTrain == "Eksekutif"    ) {
                    300000

                } else {
                    0

                }
                val hargaPaket = switches.size * 50000
                val totalHarga = hargaKelasKereta + hargaPaket

                firebase.collection("pesanan").add(paketPembelian).addOnSuccessListener {
                    paketPembelian.id = it.id
                    it.set(paketPembelian).addOnSuccessListener {
                        Toast.makeText(requireContext(), "Success Booking", Toast.LENGTH_SHORT).show()
                        findNavController().navigateUp()
                        val flag = if ( Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            PendingIntent.FLAG_MUTABLE
                        } else {
                            0
                        }
                        val intent = requireActivity().intent

                        val pendingIntent = PendingIntent.getActivity(
                            requireContext(), 0, requireActivity().intent, flag
                        )
                        val builder = NotificationCompat
                            .Builder(requireContext(), channelID)
                            .setSmallIcon(R.drawable.baseline_notifications_24)
                            .setContentTitle("Notifku")
                            .setContentText("Pesanan anda berhasil")
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)

                        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
                            val notifChannel = NotificationChannel(
                                channelID, "Notifku ",
                                NotificationManager.IMPORTANCE_DEFAULT
                            )

                            with(notifManager) {
                                createNotificationChannel(notifChannel)
                                notify(7, builder.build())
                            }
                        } else {
                            notifManager.notify(7, builder.build())
                        }


                    }

                }
            }


        }


    }

    fun hitungHarga() {
        with(binding) {
            val classTrain = txtPrice.text.toString()
            val switch2 = if (binding.switch2.isChecked) "Santapan enak" else null
            val switch3 = if (binding.switch3.isChecked) "Discount Package" else null
            val switch4 = if (binding.switch4.isChecked) "Safe Travel kit" else null
            val switch5 = if (binding.switch5.isChecked) "Asuransi Perjalanan" else null
            val switch6 = if (binding.switch6.isChecked) "Hiburan" else null
            val switch7 = if (binding.switch7.isChecked) "Selimut dan Bantal" else null
            val switch8 = if (binding.switch8.isChecked) "Layanan Porter" else null
            val switches = listOf(switch2, switch3, switch4, switch5, switch6, switch7, switch8)

            val paket = switches.filterNotNull().joinToString(", ")


            val hargaKelasKereta = if (classTrain == "Ekonomi") {
                80000

            } else if (classTrain == "Bisnis") {
                100000

            } else if (classTrain == "Eksekutif"    ) {
                300000

            } else {
                0

            }
            var hargaPaket = switches.filterNotNull().size * 50000
            val totalHarga = hargaKelasKereta + hargaPaket

            binding.harga.text = totalHarga.toString()
        }


    }



}