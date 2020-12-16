package com.example.bezpiecznik.views

import android.content.Context
import android.util.AttributeSet
import android.widget.GridLayout
import com.example.bezpiecznik.R

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

    }
}