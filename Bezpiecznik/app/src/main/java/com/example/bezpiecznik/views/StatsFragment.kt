package com.example.bezpiecznik.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.api.ApiRoutes
import com.example.bezpiecznik.models.api.IApiRequest
import com.example.bezpiecznik.models.entities.*
import com.example.bezpiecznik.adapters.StatsListAdapter
import com.example.bezpiecznik.viewmodels.StatsViewModel
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_stats.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.awaitResponse
import retrofit2.converter.gson.GsonConverterFactory

class StatsFragment : Fragment() {
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var statsListAdapter: StatsListAdapter
    lateinit var viewModel: StatsViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(StatsViewModel::class.java)
        viewManager = LinearLayoutManager(requireContext())
        statsListAdapter = StatsListAdapter(viewModel.sessionList)

        viewModel.sessionList.observe(viewLifecycleOwner){
            statsListAdapter.notifyDataSetChanged()
        }


        return inflater.inflate(R.layout.fragment_stats, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply{
            adapter=statsListAdapter
            layoutManager=viewManager
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = StatsFragment()
    }
}