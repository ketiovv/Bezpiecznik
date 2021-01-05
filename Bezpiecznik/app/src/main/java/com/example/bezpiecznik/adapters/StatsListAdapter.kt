package com.example.bezpiecznik.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.entities.User

class StatsListAdapter(var data: LiveData<MutableList<User>>):RecyclerView.Adapter<StatsListAdapter.Holder>() {


    class Holder(view:View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_stats,parent,false) as View

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val textViewJson = holder.itemView.findViewById<TextView>(R.id.textViewJson)

        textViewJson.text = data.value?.get(position).toString()
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }
}