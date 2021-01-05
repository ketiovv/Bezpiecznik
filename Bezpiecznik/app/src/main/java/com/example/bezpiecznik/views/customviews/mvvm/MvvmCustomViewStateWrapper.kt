package com.example.bezpiecznik.views.customviews.mvvm

import android.os.Parcelable
import com.example.bezpiecznik.views.customviews.mvvm.interfaces.MvvmCustomViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
class MvvmCustomViewStateWrapper(
        val superState: Parcelable?,
        val state: MvvmCustomViewState?
): Parcelable