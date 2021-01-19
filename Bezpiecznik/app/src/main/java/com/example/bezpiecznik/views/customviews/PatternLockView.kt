package com.example.bezpiecznik.views.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.view.setPadding
import androidx.lifecycle.LifecycleOwner
import com.example.bezpiecznik.R
import com.example.bezpiecznik.models.Counter
import com.example.bezpiecznik.models.enums.DotState
import com.example.bezpiecznik.models.enums.PatternStrength
import com.example.bezpiecznik.viewmodels.PatternLockViewModel
import com.example.bezpiecznik.viewmodels.PatternLockViewState
import com.example.bezpiecznik.views.customviews.mvvm.MvvmGridLayout
import java.util.ArrayList

class PatternLockView(context: Context, attributeSet: AttributeSet)
    : MvvmGridLayout<PatternLockViewState, PatternLockViewModel>(context, attributeSet) {
    private var cells = ArrayList<CellView>()
    private var selectedCells = ArrayList<CellView>()

    private var patternPaint = Paint()
    private var patternPath = Path()

    private var lastPointX = 0f
    private var lastPointY = 0f

    var patternRowCount = 0
    var patternColCount = 0

    var sleepColor = Color.LTGRAY
    var selectedColor = Color.DKGRAY

    var veryWeakPatternColor = Color.parseColor("#EC204F")
    var weakPatternColor = Color.parseColor("#FF922C")
    var mediumPatternColor = Color.parseColor("#FEED47")
    var strongPatternColor = Color.parseColor("#8DE45F")
    var veryStrongPatternColor = Color.parseColor("#2ECF03")

    var border: Drawable? = null

    var showIndicator = false
    var showCellBackground = false
    var showBorder = false

    private var errorDuration = 400


    init {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.PatternLock)

        patternRowCount = attributes.getInteger(R.styleable.PatternLock_pattern_row_count, 3)
        patternColCount = attributes.getInteger(R.styleable.PatternLock_pattern_col_count, 3)

        border = attributes.getDrawable(R.styleable.PatternLock_border_black)
        attributes.recycle()

        rowCount = patternRowCount
        columnCount = patternColCount

        initDots()
        initPathPaint()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action) {
            MotionEvent.ACTION_DOWN -> {
                val hitCell = getHitCell(event.x.toInt(), event.y.toInt())
                if (hitCell == null) {
                    return false
                } else {
                    notifyCellSelected(hitCell)
                }
            }

            MotionEvent.ACTION_MOVE -> handleActionMove(event)
            MotionEvent.ACTION_UP -> onFinish()
            MotionEvent.ACTION_CANCEL -> reset()

            else -> return false
        }
        return true
    }

    private fun handleActionMove(event: MotionEvent) {
        val hitCell = getHitCell(event.x.toInt(), event.y.toInt())
        if (hitCell != null) {
            if (!selectedCells.contains(hitCell)) {
                notifyCellSelected(hitCell)
            }
        }

        lastPointX = event.x
        lastPointY = event.y

        invalidate()
    }

    private fun notifyCellSelected(cell: CellView) {
        selectedCells.add(cell)

        cell.setState(DotState.SELECTED)
        val center = cell.getCenter()
        if (selectedCells.size == 1) {
            patternPath.moveTo(center.x.toFloat(), center.y.toFloat())
        } else {
            if(!showIndicator){
                patternPath.lineTo(center.x.toFloat(), center.y.toFloat())
            }else{
                var previousCell = selectedCells[selectedCells.size - 2]
                var previousCellCenter = previousCell.getCenter()
                var diffX = center.x - previousCellCenter.x
                var diffY = center.y - previousCellCenter.y
                var radius = cell.getRadius()
                var length = Math.sqrt((diffX * diffX + diffY * diffY).toDouble())

                patternPath.moveTo((previousCellCenter.x + radius * diffX / length).toFloat(), (previousCellCenter.y + radius * diffY / length).toFloat())
                patternPath.lineTo((center.x - radius * diffX / length).toFloat(), (center.y - radius * diffY / length).toFloat())

                val degree = Math.toDegrees(Math.atan2(diffY.toDouble(), diffX.toDouble())) + 90
                previousCell.setDegree(degree.toFloat())
                previousCell.invalidate()
            }
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawPath(patternPath, patternPaint)

        if (selectedCells.size > 0 && lastPointX > 0 && lastPointY > 0) {
            if (!showIndicator){
                val center = selectedCells[selectedCells.size - 1].getCenter()
                canvas?.drawLine(center.x.toFloat(), center.y.toFloat(), lastPointX, lastPointY, patternPaint)
            } else{
                var lastCell = selectedCells[selectedCells.size - 1]
                var lastCellCenter = lastCell.getCenter()
                var radius = lastCell.getRadius()

                if (!(lastPointX >= lastCellCenter.x - radius &&
                                lastPointX <= lastCellCenter.x + radius &&
                                lastPointY >= lastCellCenter.y - radius &&
                                lastPointY <= lastCellCenter.y + radius)) {
                    var diffX = lastPointX - lastCellCenter.x
                    var diffY = lastPointY - lastCellCenter.y
                    var length = Math.sqrt((diffX * diffX + diffY * diffY).toDouble())
                    canvas?.drawLine((lastCellCenter.x + radius * diffX / length).toFloat(),
                            (lastCellCenter.y + radius * diffY / length).toFloat(),
                            lastPointX, lastPointY, patternPaint)
                }
            }
        }
    }

    fun initDots() {
        var numbering = 1
        for(i in 0 until patternRowCount) {
            for(j in 0 until patternColCount) {
                val cell =
                        CellView(context,
                                numbering,
                                patternColCount,
                                sleepColor, selectedColor,
                                showCellBackground, showBorder, showIndicator,
                                border)
                var cellPadding = 72 / columnCount
                cell.setPadding(cellPadding, cellPadding, cellPadding, cellPadding)
                addView(cell)
                cells.add(cell)
                numbering++
            }
        }
    }

    override fun removeAllViews() {
        super.removeAllViews()
        cells.clear()
    }

    private fun initPathPaint() {
        patternPaint.isAntiAlias = true
        patternPaint.isDither = true
        patternPaint.style = Paint.Style.STROKE
        patternPaint.strokeJoin = Paint.Join.ROUND
        patternPaint.strokeCap = Paint.Cap.ROUND
        patternPaint.strokeWidth = 6f
        patternPaint.color = selectedColor
    }

    fun reset() {
        for(cell in selectedCells) {
            cell.reset()
        }

        selectedCells.clear()
        patternPaint.color = selectedColor
        patternPath.reset()

        lastPointX = 0f
        lastPointY = 0f

        invalidate()
    }

    private fun getHitCell(x: Int, y: Int) : CellView? {
        for(cell in cells) {
            if (isSelected(cell, x, y)) {
                return cell
            }
        }
        return null
    }

    private fun isSelected(view: View, x: Int, y: Int) : Boolean {
        val innerPadding = view.width * 0.2f

        return  x >= view.left + innerPadding &&
                x <= view.right - innerPadding &&
                y >= view.top + innerPadding &&
                y <= view.bottom - innerPadding
    }

    private fun onFinish() {
        lastPointX = 0f
        lastPointY = 0f

        val strength = getPatternStrength()
        setColorAfterDrawing(getColorByPatternStrength(strength))

        invalidate()

        postDelayed({
            reset()
        }, errorDuration.toLong())
    }

    private fun getColorByPatternStrength(strength: PatternStrength):Int {
        return when(strength){
            PatternStrength.VERY_STRONG -> veryStrongPatternColor
            PatternStrength.STRONG -> strongPatternColor
            PatternStrength.MEDIUM -> mediumPatternColor
            PatternStrength.WEAK -> weakPatternColor
            else -> veryWeakPatternColor
        }
    }

    private fun setColorAfterDrawing(color: Int){
        for (cell in selectedCells) {
            cell.setPatternStrengthColor(color)
            cell.setState(DotState.AFTER)
        }
        patternPaint.color = color
    }

    private fun getPatternStrength(): PatternStrength{
        val arrayOfSelectedDotsNumbers: ArrayList<Int> = ArrayList()

        for (cell in selectedCells) {
            arrayOfSelectedDotsNumbers.add(cell.dotNumber)
        }
        Log.d("test", arrayOfSelectedDotsNumbers.toString())

        val array = arrayOfSelectedDotsNumbers.toTypedArray()

        val res = Counter(patternRowCount, patternColCount, array)
        println(res.verbalScaleResult(res.printer()))
        val strength = res.verbalScaleResult(res.printer())
        Log.d("test", strength.toString())

        return strength
    }

    // ViewModel
    override val viewModel = PatternLockViewModel()

    override fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner) {
        observeLiveData(lifecycleOwner)
    }

    private fun observeLiveData(lifecycleOwner: LifecycleOwner) {
//        viewModel.getLiveData().observe(lifecycleOwner, Observer {
//            setBackgroundColor(Color.parseColor(it))
//        })

    }

}
