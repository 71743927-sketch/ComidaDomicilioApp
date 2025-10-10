package com.androidlead.fooddeliveryapp.ui

sealed class Screen(val route: String) {
    object LoginScreen : Screen("login_screen")
    object MenuScreen : Screen("menu_screen")
    object CartScreen : Screen("cart_screen")
    object PaymentScreen : Screen("payment_screen")
}
