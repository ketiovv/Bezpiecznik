package com.example.bezpiecznik.adapters

import android.annotation.SuppressLint
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.entities.Session

class HistoryListAdapter(var data: MutableLiveData<ArrayList<Session>>):RecyclerView.Adapter<HistoryListAdapter.Holder>() {


    class Holder(view:View): RecyclerView.ViewHolder(view)



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.row_history,parent,false) as View

        return Holder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val textViewSessionDate = holder.itemView.findViewById<TextView>(R.id.textViewSessionDate)
        val textViewAttempts = holder.itemView.findViewById<TextView>(R.id.textViewAttempts)
        val expandButton = holder.itemView.findViewById<ImageButton>(R.id.buttonExpandCard)
        val expandableContainer = holder.itemView.findViewById<LinearLayout>(R.id.expandableContainer)
        var attempts: String = ""
        val date = data.value?.get(position)?.startDate.toString()
        val fdate  = date.split("T")
        var time = fdate[1].substringBefore(".")


        for(a in data.value?.get(position)?.attempt!!){
            attempts = attempts + "Pattern: ${a.pattern}" + "\nStrength: ${a.strength}\n" + "Grid size: ${a.columns}x${a.rows}\n\n"

        }


        textViewSessionDate.text = "Date: ${fdate[0]} Time: $time"
        textViewAttempts.text = attempts
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