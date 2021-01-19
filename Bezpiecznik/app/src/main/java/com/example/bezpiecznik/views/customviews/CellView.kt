package com.example.bezpiecznik.views.customviews

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import com.example.bezpiecznik.models.enums.DotState
import kotlin.math.min

class CellView(context: Context,
               var dotNumber: Int,
               var columnCount: Int,

               var sleepColor: Int,
               var selectedColor: Int,

               var showCellBackground: Boolean,
               var showBorder: Boolean,
               var showIndicator: Boolean,
               var border: Drawable?): View(context){

    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var state = DotState.SLEEP
    private var cellBackground: Drawable? = ColorDrawable(sleepColor)
    private var patternStrengthColor: Int = Color.parseColor("#EC204F")

    private var degree: Float = -1f
    private var indicatorPath: Path = Path()


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var cellWidth = MeasureSpec.getSize(widthMeasureSpec) / columnCount
        var cellHeight = cellWidth
        setMeasuredDimension(cellWidth, cellHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        when(state){
            DotState.SLEEP -> drawDot(canvas,null,null, sleepColor)
            DotState.SELECTED -> drawDot(canvas, cellBackground, border, selectedColor)
            DotState.AFTER -> drawDot(canvas, cellBackground, border, patternStrengthColor)
        }
    }

    private fun drawDot(canvas: Canvas?, background: Drawable?, borderCell: Drawable?, dotColor: Int, radiusRation: Float = 0.3f){
        var radius = getRadius()
        var centerX = width / 2
        var centerY = height / 2

        if (showCellBackground){
            if (background is ColorDrawable) {
                paint.color = background.color
                paint.style = Paint.Style.FILL
                canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), radius.toFloat(), paint)
            }
        }

        if (showBorder){
            borderCell?.setBounds(paddingLeft, paddingTop, width - paddingRight, height - paddingBottom)
            borderCell?.draw(canvas!!)
        }

        paint.color = dotColor
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(centerX.toFloat(), centerY.toFloat(), radius * radiusRation, paint)

        if (showIndicator && (state == DotState.SELECTED || state == DotState.AFTER)){
            drawIndicator(canvas)
        }
    }

    private fun drawIndicator(canvas: Canvas?) {
        if (degree != -1f){
            if (indicatorPath.isEmpty) {
                indicatorPath.fillType = Path.FillType.WINDING
                val radius = getRadius()
                val height = radius * 0.2f
                indicatorPath.moveTo(
                        (width / 2).toFloat(),
                        radius * (1 - 0.3f - 0.2f) / 2 + paddingTop)
                indicatorPath.lineTo(
                        (width /2).toFloat() - height,
                        radius * (1 - 0.3f - 0.2f) / 2 + height + paddingTop)
                indicatorPath.lineTo(
                        (width / 2).toFloat() + height,
                        radius * (1 - 0.3f - 0.2f) / 2 + height + paddingTop)
                indicatorPath.close()
            }

            if (state == DotState.SELECTED) {
                paint.color = selectedColor
            } else {
                paint.color = patternStrengthColor
            }

            paint.style = Paint.Style.FILL

            canvas?.save()
            canvas?.rotate(degree, (width / 2).toFloat(), (height / 2).toFloat())
            canvas?.drawPath(indicatorPath, paint)
            canvas?.restore()
        }
    }

    fun getRadius() : Int {
        return (min(width, height) - (paddingLeft + paddingRight)) / 2
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

    fun setDegree(newDegree: Float){
        degree = newDegree
    }

    fun setPatternStrengthColor(color: Int){
        patternStrengthColor = color
    }

    fun reset() {
        setState(DotState.SLEEP)
        degree = -1f
    }

}