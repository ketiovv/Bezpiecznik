package com.example.bezpiecznik.views.customviews.mvvm.interfaces

import androidx.lifecycle.LifecycleOwner

interface IMvvmCustomView<V: IMvvmCustomViewState, T: IMvvmCustomViewModel<V>> {
    val viewModel: T

    fun onLifecycleOwnerAttached(lifecycleOwner: LifecycleOwner)
}