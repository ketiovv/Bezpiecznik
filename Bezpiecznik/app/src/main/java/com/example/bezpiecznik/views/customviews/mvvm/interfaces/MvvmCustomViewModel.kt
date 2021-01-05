package com.example.bezpiecznik.views.customviews.mvvm.interfaces

interface MvvmCustomViewModel<T: MvvmCustomViewState> {
    var state: T?
}