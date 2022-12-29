package com.cstcompany.cloudalarm.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginUI(register: Boolean, error_msg: String, onClick: (String, String) -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = if (register) "Register" else "Login",
            style = MaterialTheme.typography.headlineLarge
        )

        Spacer(modifier = Modifier.height(32.dp))

        var email by remember { mutableStateOf("") }
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
        )

        Spacer(modifier = Modifier.height(16.dp))

        var passwordIsVisible by remember { mutableStateOf(false) }
        var password by remember { mutableStateOf("") }
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = if (passwordIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = {
                    passwordIsVisible = !passwordIsVisible
                }) {
                    if (passwordIsVisible) {
                        Icon(Icons.Filled.Visibility, "Visibility On")
                    } else {
                        Icon(Icons.Filled.VisibilityOff, "Visibility Off")
                    }
                }
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        var passwordAgain by remember { mutableStateOf("") }
        if(register){
            TextField(
                value = passwordAgain,
                onValueChange = { passwordAgain = it },
                label = { Text("Password Again") },
                visualTransformation = if (passwordIsVisible) VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                isError = password != passwordAgain,
                trailingIcon = {
                    if(password != passwordAgain){
                        Icon(Icons.Filled.Error, contentDescription = "Passwords don't match", tint = MaterialTheme.colorScheme.error)
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            if(register){
                if(password != passwordAgain){
                    return@Button
                }
            }
            onClick(email, password)
        }) {
            Text(
                text = if (register) "Sign In" else "Login",
                style = MaterialTheme.typography.bodyLarge
            )
        }
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = error_msg,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
fun Dot(on: Boolean) {
    if(on){
        Icon(Icons.Filled.Circle, contentDescription = "Circle", tint = MaterialTheme.colorScheme.primary)
    }else{
        Icon(Icons.Filled.Circle, contentDescription = "Circle", tint = MaterialTheme.colorScheme.inversePrimary)
    }
}