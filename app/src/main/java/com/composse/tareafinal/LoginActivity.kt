package com.composse.tareafinal

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val auth = FirebaseAuth.getInstance() // Get FirebaseAuth instance
    val db = FirebaseFirestore.getInstance() // Get Firestore instance

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Inicio de Sesión")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico o Nombre de usuario") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            visualTransformation = PasswordVisualTransformation() // Hide password
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Recuperar información del usuario desde Firestore
                            val userId = auth.currentUser?.uid
                            userId?.let {
                                db.collection("Usuarios").document(it).get()
                                    .addOnSuccessListener { document ->
                                        if (document.exists()) {
                                            val name = document.getString("name") ?: "Usuario"
                                            val accessCount = document.getLong("accessCount")?.toInt() ?: 0
                                            val updatedAccessCount = accessCount + 1
                                            db.collection("Usuarios").document(it).update("accessCount", updatedAccessCount)
                                            navController.navigate("home/$userId/$name/$updatedAccessCount")
                                        }
                                    }
                                    .addOnFailureListener { e ->
                                        Log.e("LoginScreen", "Error retrieving user: ${e.message}")
                                    }
                            }
                        } else {
                            // Handle login error
                            Log.e("LoginScreen", "Login failed: ${task.exception?.message}")
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("register") },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }
    }
}
