package com.androidlead.fooddeliveryapp.data.repository

import com.androidlead.fooddeliveryapp.data.database.CartItemDao
import com.androidlead.fooddeliveryapp.data.database.MenuItemDao
import com.androidlead.fooddeliveryapp.data.model.CartItem
import com.androidlead.fooddeliveryapp.data.model.MenuItem
import kotlinx.coroutines.flow.Flow

class MainRepository(
    private val menuItemDao: MenuItemDao,
    private val cartItemDao: CartItemDao
) {

    suspend fun getMenuItems(): List<MenuItem> {
        return menuItemDao.getAll()
    }

    fun getCartItems(): Flow<List<CartItem>> {
        return cartItemDao.getCartItems()
    }

    suspend fun addToCart(menuItem: MenuItem) {
        val existingCartItem = cartItemDao.getCartItemByMenuItemId(menuItem.id)
        if (existingCartItem != null) {
            val updatedItem = existingCartItem.copy(quantity = existingCartItem.quantity + 1)
            cartItemDao.update(updatedItem)
        } else {
            cartItemDao.insert(CartItem(menuItemId = menuItem.id, quantity = 1))
        }
    }

    suspend fun removeFromCart(cartItem: CartItem) {
        if (cartItem.quantity > 1) {
            val updatedItem = cartItem.copy(quantity = cartItem.quantity - 1)
            cartItemDao.update(updatedItem)
        } else {
            cartItemDao.delete(cartItem.id)
        }
    }

    suspend fun clearCart() {
        cartItemDao.clearCart()
    }

    suspend fun insertInitialMenuItems() {
        if (menuItemDao.getAll().isEmpty()) {
            menuItemDao.insertAll(
                MenuItem(name = "Pizza", description = "Deliciosa pizza de queso", price = 12.99, imageUrl = "pizza.png"),
                MenuItem(name = "Burger", description = "Clásica hamburguesa de ternera", price = 8.99, imageUrl = "hamburguesa.png"),
                MenuItem(name = "Pasta", description = "Cremosa pasta Alfredo", price = 10.99, imageUrl = "pasta.png")
            )
        }
    }
}
