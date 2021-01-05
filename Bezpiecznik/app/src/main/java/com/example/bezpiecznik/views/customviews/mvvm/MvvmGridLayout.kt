package com.example.bezpiecznik.views.customviews.mvvm

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.GridLayout
import androidx.lifecycle.LifecycleOwner
import com.example.bezpiecznik.views.customviews.mvvm.exceptions.LifecycleOwnerNotFoundException
import com.example.bezpiecznik.views.customviews.mvvm.interfaces.IMvvmCustomView
import com.example.bezpiecznik.views.customviews.mvvm.interfaces.IMvvmCustomViewModel
import com.example.bezpiecznik.views.customviews.mvvm.interfaces.IMvvmCustomViewState

abstract class MvvmGridLayout<V: IMvvmCustomViewState, T: IMvvmCustomViewModel<V>>(
        context: Context,
        attributeSet: AttributeSet
) :
        GridLayout(context, attributeSet),
        IMvvmCustomView<V, T> {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val lifecycleOwner = context as? LifecycleOwner ?: throw LifecycleOwnerNotFoundException()
        onLifecycleOwnerAttached(lifecycleOwner)
    }

    override fun onSaveInstanceState() = MvvmCustomViewStateWrapper(super.onSaveInstanceState(), viewModel.state)

    override fun onRestoreInstanceState(state: Parcelable?) {
        if (state is MvvmCustomViewStateWrapper) {
            viewModel.state = state.state as V?
            super.onRestoreInstanceState(state.superState)
        }
    }
}