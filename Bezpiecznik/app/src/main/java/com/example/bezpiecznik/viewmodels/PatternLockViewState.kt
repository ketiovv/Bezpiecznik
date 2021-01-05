package com.example.bezpiecznik.viewmodels

import com.example.bezpiecznik.views.customviews.mvvm.interfaces.IMvvmCustomViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PatternLockViewState(
        val backgroundColorHexCode: String?
): IMvvmCustomViewState