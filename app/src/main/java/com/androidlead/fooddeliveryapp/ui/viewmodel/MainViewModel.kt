package com.androidlead.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidlead.fooddeliveryapp.data.model.CartItem
import com.androidlead.fooddeliveryapp.data.model.MenuItem
import com.androidlead.fooddeliveryapp.data.repository.MainRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    init {
        viewModelScope.launch {
            repository.insertInitialMenuItems()
            _menuItems.value = repository.getMenuItems()
        }

        viewModelScope.launch {
            repository.getCartItems().collect { 
                _cartItems.value = it
            }
        }
    }

    fun addToCart(menuItem: MenuItem) {
        viewModelScope.launch {
            repository.addToCart(menuItem)
        }
    }

    fun removeFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            repository.removeFromCart(cartItem)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
