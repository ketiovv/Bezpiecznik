package com.example.bezpiecznik.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bezpiecznik.views.customviews.mvvm.interfaces.IMvvmCustomViewModel

class PatternLockViewModel: IMvvmCustomViewModel<PatternLockViewState> {
    private val backgroundColor = MutableLiveData<String?>()

    init {
        backgroundColor.value = "#FFFF00"
    }

    override var state: PatternLockViewState? = null
        get() = PatternLockViewState(backgroundColor.value)
        set(value) {
            field = value
            restore(value)
        }

    fun getLiveData(): LiveData<String?> = backgroundColor

    private fun restore(state: PatternLockViewState?) {
        backgroundColor.value = state?.backgroundColorHexCode
    }
}