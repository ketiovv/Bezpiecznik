package com.example.bezpiecznik.views

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.observe
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bezpiecznik.R
import com.example.bezpiecznik.adapters.HistoryListAdapter
import com.example.bezpiecznik.viewmodels.HistoryViewModel
import kotlinx.android.synthetic.main.fragment_history.*

class HistoryFragment : Fragment() {
    lateinit var viewManager: RecyclerView.LayoutManager
    lateinit var historyListAdapter: HistoryListAdapter
    lateinit var viewModel: HistoryViewModel
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        viewManager = LinearLayoutManager(requireContext())
        historyListAdapter = HistoryListAdapter(HistoryViewModel.sessionList)


        if (HistoryViewModel.dataReady.value == null)
            viewModel.getSessions {  }


        HistoryViewModel.dataReady.observe(viewLifecycleOwner){
            if(it)
                viewModel.getSessions {  }
        }


        HistoryViewModel.sessionList.observe(viewLifecycleOwner){
            historyListAdapter.notifyDataSetChanged()
        }


        return inflater.inflate(R.layout.fragment_history, container, false)


    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView.apply{
            adapter=historyListAdapter
            layoutManager=viewManager
        }

    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}