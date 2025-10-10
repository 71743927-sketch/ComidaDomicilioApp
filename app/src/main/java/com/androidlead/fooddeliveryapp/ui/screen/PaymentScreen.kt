package com.androidlead.fooddeliveryapp.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PaymentScreen(
    onBack: () -> Unit,
    onPaymentConfirmed: () -> Unit
) {
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf("") }
    var expiryDate by remember { mutableStateOf("") }
    var cvv by remember { mutableStateOf("") }
    var attemptedToSubmit by remember { mutableStateOf(false) }

    val isFormValid by remember {
        derivedStateOf {
            firstName.isNotBlank() &&
            lastName.isNotBlank() &&
            cardNumber.isNotBlank() &&
            expiryDate.isNotBlank() &&
            cvv.isNotBlank()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Payment") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            OutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("Nombres") },
                modifier = Modifier.fillMaxWidth(),
                isError = attemptedToSubmit && firstName.isBlank(),
                supportingText = { if (attemptedToSubmit && firstName.isBlank()) Text("Campo requerido") }
            )
            OutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Apellidos") },
                modifier = Modifier.fillMaxWidth(),
                isError = attemptedToSubmit && lastName.isBlank(),
                supportingText = { if (attemptedToSubmit && lastName.isBlank()) Text("Campo requerido") }
            )
            OutlinedTextField(
                value = cardNumber,
                onValueChange = { cardNumber = it },
                label = { Text("Card Number") },
                modifier = Modifier.fillMaxWidth(),
                isError = attemptedToSubmit && cardNumber.isBlank(),
                supportingText = { if (attemptedToSubmit && cardNumber.isBlank()) Text("Campo requerido") }
            )
            OutlinedTextField(
                value = expiryDate,
                onValueChange = { expiryDate = it },
                label = { Text("Expiry Date (MM/YY)") },
                modifier = Modifier.fillMaxWidth(),
                isError = attemptedToSubmit && expiryDate.isBlank(),
                supportingText = { if (attemptedToSubmit && expiryDate.isBlank()) Text("Campo requerido") }
            )
            OutlinedTextField(
                value = cvv,
                onValueChange = { cvv = it },
                label = { Text("CVV") },
                modifier = Modifier.fillMaxWidth(),
                isError = attemptedToSubmit && cvv.isBlank(),
                supportingText = { if (attemptedToSubmit && cvv.isBlank()) Text("Campo requerido") }
            )
            Button(
                onClick = {
                    attemptedToSubmit = true
                    if (isFormValid) {
                        onPaymentConfirmed()
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Confirmar Pago")
            }
        }
    }
}
