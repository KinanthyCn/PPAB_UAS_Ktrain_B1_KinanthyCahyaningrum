package com.kinan.ktrain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.util.Executors
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Source
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.kinan.ktrain.adapter.RiwayatAdapter
import com.kinan.ktrain.authentication.PrefManager
import com.kinan.ktrain.database.Paket
import com.kinan.ktrain.database.TrainDB
import com.kinan.ktrain.database.TrainDao
import com.kinan.ktrain.database.TrainRoomDB
import com.kinan.ktrain.databinding.FragmentHistoryBinding
import java.util.concurrent.ExecutorService

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    private val binding by lazy {
        FragmentHistoryBinding.inflate(layoutInflater)
    }
    private lateinit var mFavoritDao: TrainDao
    private lateinit var executorService: ExecutorService
    private val firestore = FirebaseFirestore.getInstance()



    val adapterPaket = RiwayatAdapter(
        listHistory = emptyList<Paket>().toMutableList(),
        onClick = {
            insert(TrainDB(
                departure = it.departure,
                destination = it.destination,
                train = it.train,
                classTrain = it.classTrain,
                uid = FirebaseAuth.getInstance().currentUser?.uid.toString()
            ))
            Toast.makeText(requireActivity(), "Berhasil ditambahkan ke favorit", Toast.LENGTH_SHORT).show()




        }

    )



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val dbPesanan = Firebase.firestore.collection("pesanan")
        val userId =  PrefManager.getInstance(requireContext()).getId().toString()

        dbPesanan.whereEqualTo("uid",userId ).get(Source.SERVER).addOnSuccessListener {
            val listPesanan = it.toObjects(Paket::class.java)
            adapterPaket.listHistory.clear()
            adapterPaket.listHistory.addAll(listPesanan)
            adapterPaket.notifyDataSetChanged()
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executorService = java.util.concurrent.Executors.newSingleThreadExecutor()
        val db = TrainRoomDB.getDatabase(requireContext())
        mFavoritDao = db!!.trainDao()!!

        val dbPesanan = Firebase.firestore.collection("pesanan")
        dbPesanan.get().addOnSuccessListener {
            for (data in it){
                val paket = data.toObject(Paket::class.java)
                if (paket.uid == FirebaseAuth.getInstance().currentUser?.uid.toString()){
                    adapterPaket.listHistory.add(paket)
                }
            }
            memasukkanDataListKeretaDariFirebaseKeDalamAdapterRecyclerViewHistory(adapterPaket.listHistory)
        }
    }
    private fun memasukkanDataListKeretaDariFirebaseKeDalamAdapterRecyclerViewHistory(listPesanan: List<Paket>){
        binding.rvHistory.apply{
            layoutManager = LinearLayoutManager(requireActivity())
            adapter = adapterPaket
        }

        adapterPaket.listHistory.clear()
        adapterPaket.listHistory.addAll(listPesanan)
        adapterPaket.notifyDataSetChanged()
    }
    private fun insert(favorit: TrainDB){
        executorService.execute {
            mFavoritDao.insertAll(favorit)
        }
    }

}