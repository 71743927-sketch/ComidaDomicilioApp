package com.androidlead.fooddeliveryapp.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.androidlead.fooddeliveryapp.data.model.CartItem
import com.androidlead.fooddeliveryapp.data.model.MenuItem
import com.androidlead.fooddeliveryapp.data.model.User
import com.androidlead.fooddeliveryapp.data.repository.MainRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    private val _menuItems = MutableStateFlow<List<MenuItem>>(emptyList())
    val menuItems: StateFlow<List<MenuItem>> = _menuItems

    private val _cartItems = MutableStateFlow<List<CartItem>>(emptyList())
    val cartItems: StateFlow<List<CartItem>> = _cartItems

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private var userJob: Job? = null

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

    fun createUser(email: String, password: String) {
        viewModelScope.launch {
            try {
                repository.createUser(email, password)
                observeUser(email)
            } catch (e: Exception) {
                _error.value = "Error al crear el usuario"
            }
        }
    }

    fun loginUser(email: String, password: String) {
        viewModelScope.launch {
            val loggedInUser = repository.loginUser(email, password)
            if (loggedInUser != null) {
                observeUser(email)
            } else {
                _error.value = "Correo o contraseña incorrectos"
            }
        }
    }

    private fun observeUser(email: String) {
        userJob?.cancel()
        userJob = viewModelScope.launch {
            repository.observeUser(email).collect { userFromDb ->
                _user.value = userFromDb
            }
        }
    }

    fun clearError() {
        _error.value = null
    }
}
