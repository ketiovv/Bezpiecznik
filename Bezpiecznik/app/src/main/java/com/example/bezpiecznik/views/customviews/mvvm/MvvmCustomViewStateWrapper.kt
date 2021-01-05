package com.example.bezpiecznik.views.customviews.mvvm

import android.os.Parcelable
import com.example.bezpiecznik.views.customviews.mvvm.interfaces.IMvvmCustomViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
class MvvmCustomViewStateWrapper(
        val superState: Parcelable?,
        val state: IMvvmCustomViewState?
): Parcelable