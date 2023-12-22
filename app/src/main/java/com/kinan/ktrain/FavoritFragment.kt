package com.kinan.ktrain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import com.kinan.ktrain.adapter.FavoritAdapter
import com.kinan.ktrain.database.TrainDB
import com.kinan.ktrain.database.TrainDao
import com.kinan.ktrain.database.TrainRoomDB
import com.kinan.ktrain.databinding.FragmentFavoritBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavoritFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavoritFragment : Fragment() {
    private val binding by lazy { FragmentFavoritBinding.inflate(layoutInflater) }
    private lateinit var mFavoritDao: TrainDao
    private lateinit var executorService: ExecutorService



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        executorService = Executors.newSingleThreadExecutor()
        val db = TrainRoomDB.getDatabase(requireContext())
        mFavoritDao = db!!.trainDao()!!

        mFavoritDao.getTrainById(FirebaseAuth.getInstance().currentUser?.uid.toString()).observe(this@FavoritFragment.requireActivity()) {train ->
            binding.rvFavorite.apply {
                adapter = FavoritAdapter(train) { data ->
                    // delete
                    delete(data)
                }
            }
        }
        with(binding) {
            rvFavorite.apply {
                layoutManager = LinearLayoutManager(requireActivity())
            }
            btnBack.setOnClickListener {
                requireActivity().onBackPressed()
            }
        }

    }

    private fun delete(train: TrainDB) {
        executorService.execute {
            mFavoritDao.delete(train)
        }
    }

}