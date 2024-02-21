package com.manage.composesamples.ui.home.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.manage.composesamples.model.SnackRepo
import com.manage.composesamples.model.SnackbarManager

class CartViewModel(
    private val snackbarManager: SnackbarManager,
    snackRepository: SnackRepo
) : ViewModel() {

    companion object {
        fun provideFactory(
            snackbarManager: SnackbarManager = SnackbarManager,
            snackRepository: SnackRepo = SnackRepo
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CartViewModel(snackbarManager, snackRepository) as T
            }
        }
    }
}