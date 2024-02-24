package com.manage.composesamples.ui.utils

import java.math.BigDecimal
import java.text.NumberFormat

fun formatPrice(price: Long): String {
    return NumberFormat.getCurrencyInstance().format(
        BigDecimal(price).movePointLeft(2)
    )
}