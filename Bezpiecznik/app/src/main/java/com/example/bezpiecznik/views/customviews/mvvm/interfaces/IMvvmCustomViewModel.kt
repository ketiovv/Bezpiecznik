package com.example.bezpiecznik.views.customviews.mvvm.interfaces

interface IMvvmCustomViewModel<T: IMvvmCustomViewState> {
    var state: T?
}