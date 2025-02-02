package com.composse.tareafinal.view

import android.content.Context
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
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import com.composse.tareafinal.R
import com.composse.tareafinal.ui.theme.TareaFinalTheme

@Composable
fun LoginScreen(navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }
    val customFont = FontFamily(
        Font(R.font.mono_regular)
    )
    // Determinar si el tema es oscuro o claro
    val isDarkTheme = isSystemInDarkTheme()

    // Seleccionar la imagen de fondo adecuada
    val backgroundImage = if (isDarkTheme) {
        painterResource(id = R.drawable.background_noir) // Imagen para modo oscuro
    } else {
        painterResource(id = R.drawable.background_light) // Imagen para modo claro
    }

    TareaFinalTheme {
        // Usamos Box para apilar la imagen de fondo y el contenido
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            // Imagen de fondo
            Image(
                painter = backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop, // Ajusta la imagen para llenar el área
                modifier = Modifier.fillMaxSize()
            )
            Surface(
                color = Color.Transparent
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Inicio de Sesion", fontFamily = customFont, fontSize = 40.sp)
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
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else Icons.Filled.VisibilityOff

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(imageVector = image, contentDescription = null)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            Logear(email, password, navController, context)
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Iniciar Sesion", fontFamily = customFont)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("register") },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Registrarse", fontFamily = customFont)
                    }
                }
            }
        }
    }
}

fun Logear(email: String, password: String, navController: NavController, context: Context) {
    if (email.isEmpty() || password.isEmpty()) {
        Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
    } else {
        val auth = FirebaseAuth.getInstance() // Get FirebaseAuth instance
        val db = FirebaseFirestore.getInstance() // Get Firestore instance
        val currentDate = System.currentTimeMillis() // Obtener la fecha y hora actual
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
                                    (context as MainActivity).startPeriodicNotification()
                                    navController.navigate("home/$userId/$name/$updatedAccessCount")
                                }
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error recuperando usuario: ${e.message}", Toast.LENGTH_LONG).show()
                            }
                    } // Aquí agregamos la llave de cierre faltante
                } else {
                    val exception = task.exception
                    Toast.makeText(context, "Inicio de sesión fallido: ${exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error en la autenticación: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
