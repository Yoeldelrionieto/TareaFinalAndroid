package com.composse.tareafinal.view

import android.util.Log
import androidx.compose.foundation.layout.*
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
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val auth = FirebaseAuth.getInstance() // Get FirebaseAuth instance
    val db = FirebaseFirestore.getInstance() // Get Firestore instance
    val currentDate = System.currentTimeMillis() // Obtener la fecha y hora actual

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
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                } else {
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
                                                db.collection("Usuarios").document(it).update(
                                                    mapOf(
                                                        "accessCount" to updatedAccessCount,
                                                        "lastAccess" to currentDate // Actualizar la fecha de acceso
                                                    )
                                                )
                                                navController.navigate("home/$userId/$name/$updatedAccessCount")
                                            }
                                        }
                                        .addOnFailureListener { e ->
                                            Toast.makeText(context, "Error recuperando usuario: ${e.message}", Toast.LENGTH_LONG).show()
                                        }
                                }
                            } else {
                                val exception = task.exception
                                Toast.makeText(context, "Inicio de sesión fallido: ${exception?.message}", Toast.LENGTH_LONG).show()
                            }
                        }
                        .addOnFailureListener { e ->
                            Toast.makeText(context, "Error en la autenticación: ${e.message}", Toast.LENGTH_LONG).show()
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
