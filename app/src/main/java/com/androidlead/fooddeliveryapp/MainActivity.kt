package com.androidlead.fooddeliveryapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.androidlead.fooddeliveryapp.data.repository.MainRepository
import com.androidlead.fooddeliveryapp.ui.Screen
import com.androidlead.fooddeliveryapp.ui.screen.CartScreen
import com.androidlead.fooddeliveryapp.ui.screen.LoginScreen
import com.androidlead.fooddeliveryapp.ui.screen.MenuScreen
import com.androidlead.fooddeliveryapp.ui.theme.AppTheme
import com.androidlead.fooddeliveryapp.ui.viewmodel.MainViewModel
import com.androidlead.fooddeliveryapp.ui.viewmodel.ViewModelFactory

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        val database = (application as FoodDeliveryApplication).database
        ViewModelFactory(MainRepository(database.menuItemDao(), database.cartItemDao()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(navController = navController, startDestination = Screen.LoginScreen.route) {
                    composable(Screen.LoginScreen.route) {
                        LoginScreen(navController = navController)
                    }
                    composable(Screen.MenuScreen.route) {
                        val menuItems by viewModel.menuItems.collectAsState()
                        val cartItems by viewModel.cartItems.collectAsState()
                        MenuScreen(
                            menuItems = menuItems, 
                            cartItems = cartItems,
                            onAddToCart = { viewModel.addToCart(it) },
                            onNavigateToCart = { navController.navigate(Screen.CartScreen.route) },
                            onClose = { finish() }
                        )
                    }
                    composable(Screen.CartScreen.route) {
                        val cartItems by viewModel.cartItems.collectAsState()
                        val menuItems by viewModel.menuItems.collectAsState()
                        CartScreen(
                            cartItems = cartItems, 
                            menuItems = menuItems,
                            onClearCart = { viewModel.clearCart() },
                            onBack = { navController.popBackStack() },
                            onRemoveFromCart = { viewModel.removeFromCart(it) }
                        )
                    }
                }
            }
        }
    }
}
