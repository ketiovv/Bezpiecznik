package com.example.bezpiecznik.models

import com.example.bezpiecznik.models.enums.PatternStrength
import kotlin.math.abs

class Counter(private val rowsB: Int, private val columnsB:Int, private val code: Array<Int>) {

    fun printer() : Int{
        val rows = rowsB
        val columns = columnsB
        val c = code
        val rc = code.reversedArray()
        val sc = toStringConverter(c)
        val rsc = toStringConverter(rc)
        var res = 100

        val xd = neighborsDifference(neighborHorizontallyVertically(columns,c),neighborDiagonally(columns,c),rows,columns)

        res -= cornerStart(rows,columns,c)

        if(diagonal1(rows,columns,c) || diagonal2(rows,columns,c) || diagonal1(rows,columns,rc) || diagonal2(rows,columns,rc)){
            res -= 80
        }
        else{
            if(horizontalLines(columns,c) || verticalLines(rows,columns,c)){
                res -= (30 + lengthRelative(rows,columns,c) + xd)
            }
            else if(horizontalLines(columns,rc) || verticalLines(rows,columns,rc)){
                res -= (25 + lengthRelative(rows,columns,c) + xd)
            }
            else{
                if(shorterSide(rows,columns,c)){
                    res -= (25 + lengthRelative(rows,columns,c) + xd)

                }
                else{
                    if(longerSide(rows,columns,c)){
                        res -= (20 + lengthRelative(rows,columns,c) + xd)
                    }
                    else{
                        res -= (lengthRelative(rows,columns,c) + xd)
                    }
                }
            }

        }

        if(res < 0)
            res = 0

        for(i in PopularPatterns.Easy.indices){
            if(sc == PopularPatterns.Easy[i] || rsc == PopularPatterns.Easy[i])
                res = 20
        }

        return res
    }

    fun toStringConverter(code: Array<Int>): String{
        var res ="["

        for (element in code) {
            res += element.toString()
            res += ","
        }
        res = res.dropLast(1)
        res += "]"
        return res
    }

    fun verbalScaleResult(res: Int): PatternStrength{
        return when (res) {
            in 81..100 -> PatternStrength.VERY_STRONG
            in 61..80 -> PatternStrength.STRONG
            in 41..60 -> PatternStrength.MEDIUM
            in 21..40 -> PatternStrength.WEAK
            else -> PatternStrength.VERY_WEAK
        }
    }

    private fun cornerStart(rows: Int, columns: Int, code: Array<Int>): Int {
        if(code[0] == 1)
            return 15
        else if(code[0] == columns || code[0]==columns*(rows-1)+1)
            return 14
        else if(code[0] == columns * rows)
            return 13
        else
            return 0
    }
    private fun horizontalLines(columns: Int, code: Array<Int>): Boolean {
        var r = 0
        var startBok = false
        var res = false;

        for(i in 0 until columns){
            println(columns * i + 1)
            if(code[0] == columns * i + 1){
                startBok = true
            }

        }

        if(startBok && code.size == columns){
            for(i in 0 until columns - 1){
                if(code[i+1] - code[i] == 1)
                    r++
            }
            if(r == columns - 1)
                res = true
        }
        return res

    }

    private fun verticalLines(rows: Int, columns: Int, code: Array<Int>): Boolean {
        var r = 0
        var startGD = false
        var res = false

        for(i in 0 until columns)
            if(code[0] == i + 1)
                startGD = true

        if(startGD && code.size == rows){
            for(i in 0 until rows-1){
                if(code[i+1] - code[i] == columns)
                    r++
            }
            if(r == rows - 1)
                res = true
        }
        return res
    }

    private fun diagonal1(rows: Int, columns: Int, code: Array<Int>): Boolean {
        var res = false
        var ile = 0
        if(rows == columns && code.size == columns){
            for(i in 0 until rows){
                if(code[i] == (rows + 1) * i + 1)
                    ile += 1
            }
            if(ile == rows)
                res = true
        }
        return res
    }

    private fun diagonal2(rows: Int, columns: Int, code: Array<Int>): Boolean{
        var ile = 0
        var res = false
        if(rows == columns && code.size == columns){
            for(i in 0 until rows){
                if(code[i] == rows * (1 + i) - i)
                    ile += 1
            }
            if(ile == rows)
                res = true
        }
        return res
    }

    private fun neighborHorizontallyVertically(columns: Int, code: Array<Int>): Int{
        var ile = 0
        for(i in 1 until code.size)
            if ((code[i] == code[i - 1] - 1 && code[i]%columns != 0) || (code[i] == code[i - 1] + 1 && code[i]%columns != 1) || code[i] == code[i - 1] - columns || code[i] == code[i - 1] + columns)
                ile++
        return ile
    }

    private fun neighborDiagonally(columns: Int, code: Array<Int>): Int{
        var ile = 0
        for(i in 1 until code.size)
            if ((code[i] == code[i - 1] + columns + 1 && code[i]%columns != 1) || (code[i] == code[i - 1] - columns + 1 && code[i]%columns != 1) || (code[i] == code[i - 1] - columns - 1 && code[i]%columns != 0) || (code[i] == code[i - 1] + columns - 1 && code[i]%columns != 0))
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
        return if(wh == 0 && s != 0 || wh != 0 && s == 0)
            (20 + abs(wh - s) * 30 / (rows * columns))
        else
            (abs(wh - s) * 30 / (rows * columns))
    }
}