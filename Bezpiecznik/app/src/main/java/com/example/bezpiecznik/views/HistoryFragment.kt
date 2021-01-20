package com.example.bezpiecznik.views

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bezpiecznik.R
import com.example.bezpiecznik.adapters.StatsListAdapter
import com.example.bezpiecznik.viewmodels.StatsViewModel
import com.example.bezpiecznik.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_stats.*

class StatsFragment : Fragment() {
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var statsListAdapter: StatsListAdapter
    lateinit var viewModel: StatsViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(StatsViewModel::class.java)
        viewManager = LinearLayoutManager(requireContext())
        statsListAdapter = StatsListAdapter(viewModel.sessionList)


        if (StatsViewModel.dataReady.value == null)
            viewModel.getSessions {  }


        StatsViewModel.dataReady.observe(viewLifecycleOwner){
            if(it)
                viewModel.getSessions {  }
        }


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