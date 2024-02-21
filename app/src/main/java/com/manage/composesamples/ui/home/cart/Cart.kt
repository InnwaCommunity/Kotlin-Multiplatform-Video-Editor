package com.manage.composesamples.ui.home.cart

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun Cart(
    onSnackClick: (Long) -> Unit,
    onNavigateToRoute: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: CartViewModel = viewModel(factory = CartViewModel.provideFactory())
) {

}