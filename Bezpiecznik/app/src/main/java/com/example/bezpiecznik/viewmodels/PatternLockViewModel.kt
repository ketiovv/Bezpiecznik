package com.example.bezpiecznik.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.bezpiecznik.views.customviews.mvvm.interfaces.MvvmCustomViewModel

class PatternLockViewModel: MvvmCustomViewModel<PatternLockViewState> {
    private val liveData = MutableLiveData<String?>()

    init {
        liveData.value = "#FFFF00"
    }

    override var state: PatternLockViewState? = null
        get() = PatternLockViewState(liveData.value)
        set(value) {
            field = value
            restore(value)
        }

    fun getLiveData(): LiveData<String?> = liveData

    private fun restore(state: PatternLockViewState?) {
        liveData.value = state?.hexCode
    }
}