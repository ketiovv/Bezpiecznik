package com.example.bezpiecznik.views

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.preference.PreferenceManager
import com.example.bezpiecznik.R
import kotlinx.android.synthetic.main.fragment_tests.*

class TestsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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


    override fun onDestroy() {
        super.onDestroy()
        Log.d("myTag", "onDestroy")
    }

    override fun onPause() {
        super.onPause()
        Log.d("myTag", "onPause")


    }

    override fun onDetach() {
        super.onDetach()
        Log.d("myTag", "onDetach")

    }

    companion object {

        @JvmStatic
        fun newInstance() = TestsFragment()
    }
}