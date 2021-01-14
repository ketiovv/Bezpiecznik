package com.example.bezpiecznik.views.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import com.example.bezpiecznik.models.enums.DotState

class CellView(context: Context,
               var dotNumber: Int,
               var columnCount: Int,

               var sleepColor: Int,
               var selectedColor: Int,
               var passwordStrengthColor: Int,

               var showCellBackground: Boolean): View(context){

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var state = DotState.SLEEP
    private var cellBackground: Drawable? = ColorDrawable(sleepColor)


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var cellWidth = MeasureSpec.getSize(widthMeasureSpec) / columnCount
        var cellHeight = cellWidth
        setMeasuredDimension(cellWidth,cellHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        when(state){
            DotState.SLEEP -> drawDot(canvas,null, sleepColor)
            DotState.SELECTED -> drawDot(canvas,cellBackground, selectedColor)
            DotState.AFTER -> drawDot(canvas,cellBackground, passwordStrengthColor)
        }
    }

    fun drawDot(canvas: Canvas?, background: Drawable?, dotColor: Int, radiusRation: Float = 0.3f){
        var radius = getRadius()
        var centerX = width / 2
        var centerY = height / 2

        // TODO: Make background smaller
        if (showCellBackground){
            if (background is ColorDrawable) {
                paint.color = background.color
                paint.style = Paint.Style.FILL
                canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)
            } else {
                background?.setBounds(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom)
                background?.draw(canvas!!)
            }
        }

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
        setState(DotState.SLEEP)
    }

}