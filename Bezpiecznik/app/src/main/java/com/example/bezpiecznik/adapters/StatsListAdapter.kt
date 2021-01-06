package com.example.bezpiecznik.adapters

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.entities.Session
import com.example.bezpiecznik.models.entities.User

class StatsListAdapter(var data: LiveData<MutableList<Session>>):RecyclerView.Adapter<StatsListAdapter.Holder>() {


    class Holder(view:View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_statistics,parent,false) as View

        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val textViewSessionDate = holder.itemView.findViewById<TextView>(R.id.textViewSessionDate)
        val expandButton = holder.itemView.findViewById<ImageButton>(R.id.buttonExpandCard)
        val expandableContainer = holder.itemView.findViewById<ConstraintLayout>(R.id.expandableContainer)

        textViewSessionDate.text = data.value?.get(position)?.startDate.toString()
        expandButton.setOnClickListener(){
            if (expandableContainer.visibility == View.GONE)
            {
                TransitionManager.beginDelayedTransition(expandableContainer, AutoTransition())
                expandableContainer.visibility = View.VISIBLE
                expandButton.rotation = 180F
            }
            else if (expandableContainer.visibility == View.VISIBLE){
                TransitionManager.beginDelayedTransition(expandableContainer, AutoTransition())
                expandButton.rotation = 0F
                expandableContainer.visibility = View.GONE
            }
        }
    }

    override fun getItemCount(): Int {
        return data.value?.size?:0
    }
}