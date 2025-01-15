package com.composse.tareafinal

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.composse.tareafinal.ui.theme.TareaFinalTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.logging.Log
import com.google.firebase.firestore.FirebaseFirestore

private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        enableEdgeToEdge()
        setContent {
            // NavController para manejar la navegación
            val navController = rememberNavController()
            // Usamos Scaffold como contenedor principal
            Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = "register",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable("register") {
                        RegisterScreen(navController)
                    }
                    composable("home/{userId}/{name}/{accessCount}") { backStackEntry ->
                        val userId = backStackEntry.arguments?.getString("userId") ?: ""
                        val name = backStackEntry.arguments?.getString("name") ?: "Usuario"
                        val accessCount = backStackEntry.arguments?.getString("accessCount")?.toInt() ?: 1
                        // Pasar el UID del usuario, nombre y el contador de accesos
                        HomeScreen(navController, userId = userId, name = name, accessCount = accessCount)
                    }
                    composable("api") {
                        ApiScreen(navController)
                    }
                    composable("login") {
                        LoginScreen(navController)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier

    )
    RegisterScreen(rememberNavController())
}

@Composable
fun RegisterScreen(navController: NavController) {
    var name by remember { mutableStateOf("") }
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
        Text(text = "Registro")
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Correo electrónico") },
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
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            // Guardar información del usuario en Firestore
                            val userId = auth.currentUser?.uid
                            val user = hashMapOf(
                                "name" to name,
                                "email" to email,
                                "accessCount" to 1 // Set initial access count to 1
                            )
                            userId?.let {
                                db.collection("Usuarios").document(it).set(user)
                                    .addOnSuccessListener {
                                        navController.navigate("home/$userId/$name/1")
                                    }
                                    .addOnFailureListener { e ->
                                        //Log.e("RegisterScreen", "Error saving user: ${e.message}")
                                    }
                            }
                        } else {
                            // Handle registration error
                            //Log.e("RegisterScreen", "Registration failed: ${task.exception?.message}")
                        }
                    }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { navController.navigate("login") }, // Navigate to LoginScreen
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir a Iniciar Sesión")
        }
    }
}




@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    TareaFinalTheme {
        Greeting("YOEL")
    }
}