package com.example.bezpiecznik.views

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.entities.Session
import com.example.bezpiecznik.viewmodels.HistoryViewModel
import com.example.bezpiecznik.viewmodels.UserViewModel
import kotlinx.android.synthetic.main.fragment_tests.*
import java.time.LocalDateTime

class TestsFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tests, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // TODO: Skalowanie dzieje się tylko kolumanami, poprawić tak, żeby skalowało też według row

        val sp = PreferenceManager.getDefaultSharedPreferences(context)

        val col = sp.getInt("col_number",3)
        val row = sp.getInt("row_number",3)
        val background = sp.getBoolean("background",false)
        val border = sp.getBoolean("border",false)
        val indicator = sp.getBoolean("indicator",false)
        val invisibleDrawing = sp.getBoolean("invisible_drawing", false)

        pattern_lock_id.columnCount = col
        pattern_lock_id.rowCount = row
        pattern_lock_id.patternColCount = col
        pattern_lock_id.patternRowCount = row
        pattern_lock_id.showCellBackground = background
        pattern_lock_id.showBorder = border
        pattern_lock_id.showIndicator = indicator
        pattern_lock_id.invisibleDrawing = invisibleDrawing

        pattern_lock_id.reset()
        pattern_lock_id.removeAllViews()
        pattern_lock_id.initDots()
    }



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onPause() {
        super.onPause()
        val attempts = pattern_lock_id.getAttempts()
        if (attempts.size != 0)
        {
            HistoryViewModel.dataReady.postValue(false)
            //Log.d("myTagAttempts", attempts.toString())
            historyViewModel.getSessions {
                val session = Session(LocalDateTime.now().toString(),
                        attempts, UserViewModel.user.id )
                //HistoryViewModel.sessionList.value!!.add(session)
                historyViewModel.addSession(session){
                    HistoryViewModel.dataReady.postValue(true)
                }
            }
        }
    }


    companion object {

        @JvmStatic
        fun newInstance() = TestsFragment()
    }
}