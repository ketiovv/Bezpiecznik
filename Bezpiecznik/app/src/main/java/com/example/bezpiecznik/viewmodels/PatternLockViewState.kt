package com.example.bezpiecznik.viewmodels

import com.example.bezpiecznik.views.customviews.mvvm.interfaces.MvvmCustomViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PatternLockViewState(
        val hexCode: String?
): MvvmCustomViewState