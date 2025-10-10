package com.androidlead.fooddeliveryapp.data.repository

import com.androidlead.fooddeliveryapp.data.database.CartItemDao
import com.androidlead.fooddeliveryapp.data.database.MenuItemDao
import com.androidlead.fooddeliveryapp.data.local.UserDao
import com.androidlead.fooddeliveryapp.data.model.CartItem
import com.androidlead.fooddeliveryapp.data.model.MenuItem
import com.androidlead.fooddeliveryapp.data.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import java.security.MessageDigest

class MainRepository(
    private val menuItemDao: MenuItemDao,
    private val cartItemDao: CartItemDao,
    private val userDao: UserDao
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

    suspend fun createUser(email: String, password: String): User {
        val passwordHash = hashPassword(password)
        val user = User(email = email, passwordHash = passwordHash)
        userDao.insertUser(user)
        return user
    }

    suspend fun loginUser(email: String, password: String): User? {
        val user = userDao.getUserByEmail(email).firstOrNull()
        if (user != null) {
            val passwordHash = hashPassword(password)
            if (user.passwordHash == passwordHash) {
                return user
            }
        }
        return null
    }

    fun observeUser(email: String): Flow<User?> {
        return userDao.getUserByEmail(email)
    }

    private fun hashPassword(password: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(password.toByteArray(Charsets.UTF_8))
        return hash.fold("") { str, it -> str + "%02x".format(it) }
    }

    suspend fun insertInitialMenuItems() {
        if (menuItemDao.getAll().isEmpty()) {
            menuItemDao.insertAll(
                MenuItem(name = "Pizza", description = "Deliciosa pizza de queso", price = 12.99, imageUrl = "pizza.png"),
                MenuItem(name = "Burger", description = "Clásica hamburguesa de ternera", price = 8.99, imageUrl = "hamburguesa.png"),
                MenuItem(name = "Pasta", description = "Cremosa pasta Alfredo", price = 10.99, imageUrl = "pasta.png"),
                MenuItem(name = "Ensalada", description = "Ensalada César con pollo a la parrilla", price = 7.99, imageUrl = "ensalada.png"),
                MenuItem(name = "Tacos", description = "Tacos al pastor con piña", price = 9.99, imageUrl = "tacos.png"),
                MenuItem(name = "Sushi", description = "Rollos de sushi variados", price = 14.99, imageUrl = "sushi.png"),
                MenuItem(name = "Sopa", description = "Sopa de verduras de temporada", price = 6.50, imageUrl = "sopa.png"),
                MenuItem(name = "Sandwich", description = "Sandwich de pavo y queso", price = 7.50, imageUrl = "sandwich.png"),
                MenuItem(name = "Jugo de Naranja", description = "Jugo de naranja recién exprimido", price = 3.50, imageUrl = "jugo_naranja.png"),
                MenuItem(name = "Pastel de Chocolate", description = "Delicioso pastel de chocolate", price = 5.50, imageUrl = "pastel_chocolate.png"),
                MenuItem(name = "Café", description = "Café recién hecho", price = 2.50, imageUrl = "cafe.png"),
                MenuItem(name = "Té", description = "Té caliente", price = 2.00, imageUrl = "te.png")
            )
        }
    }
}
