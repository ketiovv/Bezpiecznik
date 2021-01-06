package com.example.bezpiecznik.views.customviews

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.bezpiecznik.R
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
    private var lastPaintY = 0f

    var patternRowCount = 0
    var patternColCount = 0

    init {
        val attributes = context.obtainStyledAttributes(attributeSet, R.styleable.PatternLock)

        patternRowCount = attributes.getInteger(R.styleable.PatternLock_pattern_row_count, 3)
        patternColCount = attributes.getInteger(R.styleable.PatternLock_pattern_col_count, 3)
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
        lastPaintY = event.y

        invalidate()
    }

    private fun notifyCellSelected(cell: CellView) {
        selectedCells.add(cell)
        val center = cell.getCenter()
        if (selectedCells.size == 1) {
                patternPath.moveTo(center.x.toFloat(), center.y.toFloat())
        } else {
                patternPath.lineTo(center.x.toFloat(), center.y.toFloat())
        }
    }

    override fun dispatchDraw(canvas: Canvas?) {
        super.dispatchDraw(canvas)
        canvas?.drawPath(patternPath, patternPaint)
        if (selectedCells.size > 0 && lastPointX > 0 && lastPaintY > 0) {
                val center = selectedCells[selectedCells.size - 1].getCenter()
                canvas?.drawLine(center.x.toFloat(), center.y.toFloat(), lastPointX, lastPaintY, patternPaint)
            }
        }

    fun initDots() {
        for(i in 0 until patternRowCount) {
            for(j in 0 until patternColCount) {
                val cell = CellView(context, patternColCount)
                addView(cell)
                cells.add(cell)
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
        patternPaint.color = Color.BLACK
    }

    fun reset() {
        selectedCells.clear()
        patternPath.reset()

        lastPointX = 0f
        lastPaintY = 0f

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

        return x >= view.left + innerPadding && x <= view.right - innerPadding &&
                y >= view.top + innerPadding && y <= view.bottom - innerPadding
    }

    private fun onFinish() {
        lastPointX = 0f
        lastPaintY = 0f

        reset()
    }

    override val viewModel = PatternLockViewModel()

    override fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner) {
        observeLiveData(lifecycleOwner)
    }

    private fun observeLiveData(lifecycleOwner: LifecycleOwner) {
        viewModel.getLiveData().observe(lifecycleOwner, Observer {
            setBackgroundColor(Color.parseColor(it))
        })

    }

}
