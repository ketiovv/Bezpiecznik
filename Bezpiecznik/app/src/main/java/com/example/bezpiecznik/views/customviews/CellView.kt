package com.example.bezpiecznik.views.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bezpiecznik.R

class CellView(context: Context, var columnCount: Int): View(context){
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var cellWidth = MeasureSpec.getSize(widthMeasureSpec) / columnCount
        var cellHeight = cellWidth
        setMeasuredDimension(cellWidth,cellHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawDot(canvas)
    }
    fun drawDot(canvas: Canvas?){
        var radius = getRadius()
        var centerX = width/2
        var centerY = height/2

        paint.color = ContextCompat.getColor(context, R.color.purple_700)
        paint.style = Paint.Style.FILL

        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), radius * 0.3f, paint)
    }

    fun getRadius() : Int {
        return (Math.min(width, height) - (paddingLeft + paddingRight)) / 2
    }

}