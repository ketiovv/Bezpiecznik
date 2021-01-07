package com.example.bezpiecznik.views.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.enums.DotState

class CellView(context: Context, var columnCount: Int): View(context){
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var state = DotState.REGULAR

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var cellWidth = MeasureSpec.getSize(widthMeasureSpec) / columnCount
        var cellHeight = cellWidth
        setMeasuredDimension(cellWidth,cellHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        when(state){
            DotState.REGULAR -> drawDot(canvas,null, Color.GRAY)
            DotState.SELECTED -> drawDot(canvas,null, Color.GREEN)
            DotState.ERROR -> drawDot(canvas,null, Color.RED)
        }
    }

    fun drawDot(canvas: Canvas?, background: Drawable?, dotColor: Int, radiusRation: Float = 0.3f){
        var radius = getRadius()
        var centerX = width/2
        var centerY = height/2
        
        paint.color = dotColor
        paint.style = Paint.Style.FILL

        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), radius * radiusRation, paint)
    }

    fun getRadius() : Int {
        return (Math.min(width, height) - (paddingLeft + paddingRight)) / 2
    }

    fun getCenter() : Point {
        var point = Point()
        point.x = left + (right - left) / 2
        point.y = top + (bottom - top) / 2
        return point
    }

    fun setState(newState: DotState) {
        state = newState
        invalidate()
    }

    fun reset() {
        setState(DotState.REGULAR)
    }

}