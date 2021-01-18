package com.example.bezpiecznik.models

import kotlin.math.abs

class Counter(private val rowsB: Int, private val columnsB:Int, private val code: Array<Int>) {

    fun printer() : Int{
        val rows = rowsB
        val columns = columnsB
        val c = code
        val rc = code.reversedArray()

        var res = 100

        val xd = neighborsDifference(neighborHorizontallyVertically(columns,c),neighborDiagonally(columns,c),rows,columns)

        println("Ujemne punkty za długość kodu względem planszy: " + lengthRelative(rows,columns,c))
        println("Punkty ujemne za sąsiadów: $xd")

        if(cornerStart(rows, columns, c))
            res -= 15
        if(diagonal1(rows,columns,c) || diagonal2(rows,columns,c) || diagonal1(rows,columns,rc) || diagonal2(rows,columns,rc))
            res -= 75
        res -= if(horizontalLines(columns,c) || verticalLines(rows,columns,c) || horizontalLines(columns,rc) || verticalLines(rows,columns,rc))
            (50 + xd)
        else{
            if(shorterSide(rows,columns,c))
                (40 + lengthRelative(rows,columns,c) + xd)
            else{
                if(longerSide(rows,columns,c))
                    (35 + lengthRelative(rows,columns,c) + xd)
                else
                    (lengthRelative(rows,columns,c) + xd)
            }
        }
        return res
    }

    fun verbalScaleResult(res: Int): String{
        var verbalResult:String
        if(res in 81..100)
            verbalResult = "VERY STRONG"
        else if(res in 61..80)
            verbalResult = "STRONG"
        else if(res in 41..60)
            verbalResult = "MEDIUM"
        else if(res in 21..40)
            verbalResult = "WEAK"
        else
            verbalResult = "VERY WEAK"

        return verbalResult
    }

    private fun cornerStart(rows: Int, columns: Int, code: Array<Int>): Boolean {
        return code[0] == 1 || code[0] == columns || code[0]==columns*(rows-1)+1 || code[0] == columns*rows
    }
    private fun horizontalLines(columns: Int, code: Array<Int>): Boolean {
        var r = 0
        var startBok = false

        for(i in 0 until columns-1){
            if(code[0] == columns * i + 1)
                startBok = true
        }

        return if(startBok && code.size == columns){
            for(i in 0 until columns - 1)
                if(code[i+1] - code[i] == 1)
                    r++
            r == columns - 1
        } else
            false

    }

    private fun verticalLines(rows: Int, columns: Int, code: Array<Int>): Boolean {
        var r = 0
        var startGD = false

        for(i in 0 until columns)
            if(code[0] == i + 1)
                startGD = true

        return if(startGD && code.size == rows){
            for(i in 0 until rows-1)
                if(code[i+1] - code[i] == columns)
                    r++
            r == rows - 1
        } else
            false
    }

    private fun diagonal1(rows: Int, columns: Int, code: Array<Int>): Boolean {
        var res = false
        if(rows == columns && code.size == columns){
            for(i in 0 until rows){
                res = code[i] == (rows + 1) * i + 1
            }
        }
        return res
    }

    private fun diagonal2(rows: Int, columns: Int, code: Array<Int>): Boolean{
        var res = false
        if(rows == columns && code.size == columns){
            for(i in 0 until rows){
                res = code[i] == rows * (1 + i) - i
            }
        }
        return res
    }

    private fun neighborHorizontallyVertically(columns: Int, code: Array<Int>): Int{
        var ile = 0
        for(i in 1 until code.size)
            if (code[i] == code[i - 1] - 1 || code[i] == code[i - 1] + 1 || code[i] == code[i - 1] - columns || code[i] == code[i - 1] + columns)
                ile++
        return ile
    }

    private fun neighborDiagonally(columns: Int, code: Array<Int>): Int{
        var ile = 0
        for(i in 1 until code.size)
            if (code[i] == code[i - 1] + columns + 1 || code[i] == code[i - 1] - columns + 1 || code[i] == code[i - 1] - columns - 1 || code[i] == code[i - 1] + columns - 1)
                ile++
        return ile
    }

    private fun lengthRelative(rows: Int, columns: Int, code: Array<Int>): Int{
        val m:Double = (rows * columns).toDouble()
        val a:Double = (code.size / m)
        val x:Double = ((columns + rows)/2).toDouble()
        val b:Double = (150/x)
        return (b * (1-a)).toInt()
    }

    private fun shorterSide(rows: Int, columns: Int, code: Array<Int>): Boolean{
        val shorter: Int = if(rows > columns){
            columns
        } else{
            rows
        }
        return code.size == shorter
    }

    private fun longerSide(rows: Int, columns: Int, code: Array<Int>): Boolean{

        val short: Int = if(rows > columns){
            rows
        } else{
            columns
        }
        return code.size == short
    }

    private fun neighborsDifference(wh: Int, s: Int, rows: Int, columns: Int): Int{
        return (abs(wh - s) * 30 / (rows * columns))
    }
}