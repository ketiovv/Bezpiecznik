package com.example.bezpiecznik.views.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import android.widget.GridLayout
import androidx.core.content.ContextCompat
import androidx.core.view.setPadding
import com.example.bezpiecznik.R
import java.util.ArrayList

class PatternLockView :GridLayout{

    var patternRowCount = 0
    var patternColCount = 0

    constructor(context:Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet): super(context, attributeSet){
        var attributes = context.obtainStyledAttributes(attributeSet, R.styleable.PatternLock)

        // TODO: Think about getIntegerOrThrow
        patternRowCount = attributes.getInteger(R.styleable.PatternLock_pattern_row_count, 3)
        patternColCount = attributes.getInteger(R.styleable.PatternLock_pattern_col_count, 3)

        attributes.recycle()

        rowCount = patternRowCount
        columnCount = patternColCount

        setupDots()
    }

    fun setupDots(){
        for(x in 0 until patternColCount){
            for(y in 0 until patternRowCount){
                var cell = CellView(context, patternColCount)
                addView(cell)
            }
        }
    }
}
