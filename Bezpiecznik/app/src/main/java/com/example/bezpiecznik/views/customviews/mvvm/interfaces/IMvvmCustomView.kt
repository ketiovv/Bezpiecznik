package com.example.bezpiecznik.views.customviews.mvvm.interfaces

import androidx.lifecycle.LifecycleOwner

interface IMvvmCustomView<V: MvvmCustomViewState, T: MvvmCustomViewModel<V>> {
    val viewModel: T

    fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner)
}