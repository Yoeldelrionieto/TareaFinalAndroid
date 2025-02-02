package com.composse.tareafinal.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
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
import com.composse.tareafinal.viewmodel.UserViewModel
import com.composse.tareafinal.ui.theme.TareaFinalTheme
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.VisualTransformation
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Surface
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.composse.tareafinal.NotificationUtils
import com.composse.tareafinal.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.composse.tareafinal.viewmodel.AnimeJikanViewModel

private lateinit var auth: FirebaseAuth
@SuppressLint("StaticFieldLeak")
private lateinit var firestore: FirebaseFirestore
private lateinit var handler: Handler
private lateinit var runnable: Runnable

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Verificación de Google Play Services
        val resultCode = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
        if (resultCode == ConnectionResult.SUCCESS) {
            // Google Play Services está disponible
            FirebaseApp.initializeApp(this)
            enableEdgeToEdge()
            NotificationUtils.createNotificationChannel(this)
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
                            ApiScreen(navController, UserViewModel())
                        }
                        composable("login") {
                            LoginScreen(navController)
                        }
                        composable("apiAnime") {
                            ApiAnimeJikanScreen(navController, AnimeJikanViewModel())
                        }
                        composable(
                            "details/{animeId}?" +
                                    "title={title}&posterImage={posterImage}&rating={rating}&duration={duration}&" +
                                    "aired={aired}&genres={genres}&studios={studios}&producers={producers}&" +
                                    "trailerUrl={trailerUrl}&episodes={episodes}&synopsis={synopsis}",
                            arguments = listOf(
                                navArgument("animeId") { type = NavType.IntType },
                                navArgument("title") { type = NavType.StringType; defaultValue = "Sin título" },
                                navArgument("posterImage") { type = NavType.StringType; defaultValue = "" },
                                navArgument("rating") { type = NavType.StringType; defaultValue = "N/A" },
                                navArgument("duration") { type = NavType.StringType; defaultValue = "Desconocida" },
                                navArgument("aired") { type = NavType.StringType; defaultValue = "Desconocido" },
                                navArgument("genres") { type = NavType.StringType; defaultValue = "Desconocido" },
                                navArgument("studios") { type = NavType.StringType; defaultValue = "Desconocido" },
                                navArgument("producers") { type = NavType.StringType; defaultValue = "Desconocido" },
                                navArgument("trailerUrl") { type = NavType.StringType; defaultValue = "" },
                                navArgument("episodes") { type = NavType.IntType; defaultValue = -1 },
                                navArgument("synopsis") { type = NavType.StringType; defaultValue = "Sin sinopsis disponible" }
                            )
                        ) { backStackEntry ->
                            val args = backStackEntry.arguments!!

                            DetailsAnimeScreen(
                                navController = navController,
                                title = args.getString("title"),
                                posterImage = args.getString("posterImage"),
                                rating = args.getString("rating")?.toDoubleOrNull(),
                                duration = args.getString("duration"),
                                aired = args.getString("aired"),
                                genres = args.getString("genres"),
                                studios = args.getString("studios"),
                                producers = args.getString("producers"),
                                trailerUrl = args.getString("trailerUrl"),
                                episodes = args.getInt("episodes").takeIf { it != -1 },
                                synopsis = args.getString("synopsis")
                            )
                        }
                    }
                }
            }
        } else {
            // Google Play Services no está disponible
            GoogleApiAvailability.getInstance().getErrorDialog(this, resultCode, 404)?.show()
        }
    }
    // Método para iniciar la notificación periódica
    fun startPeriodicNotification() {
        handler = Handler(Looper.getMainLooper())
        runnable = object : Runnable {
            override fun run() {
                // Lógica para mostrar la notificación
                NotificationUtils.showNotification(
                    context = this@MainActivity,
                    title = "Recuerda consultar la API",
                    message = "Haz click para iniciar sesion"
                )
                handler.postDelayed(this, 500) // 500ms = 0.5s

            }
        }
        handler.post(runnable)
    }
    // Método para detener la notificación periódica
    fun stopPeriodicNotification() {
        handler.removeCallbacks(runnable)
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
    val context = LocalContext.current
    var passwordVisible by remember { mutableStateOf(false) }

    val customFont = FontFamily(
        Font(R.font.mono_regular)
    )
    // Determinar si el tema es oscuro o claro
    val isDarkTheme = isSystemInDarkTheme()

    // Seleccionar la imagen de fondo adecuada
    val backgroundImage = if (isDarkTheme) {
        painterResource(id = R.drawable.background_noir) // Reemplaza con el nombre de tu imagen de fondo para modo oscuro
    } else {
        painterResource(id = R.drawable.background_ligth) // Reemplaza con el nombre de tu imagen de fondo para modo claro
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

            // Superficie semi-transparente para oscurecer o aclarar el fondo si es necesario
            Surface(
                modifier = Modifier.fillMaxSize(), color = Color.Transparent // Ajusta la opacidad si deseas oscurecer el fondo
            ) {// Contenido principal
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(text = "Registro", fontFamily = customFont, fontSize = 40.sp)
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
                            Registrar(name, email, password, navController, context)
                        },
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Registrarse", fontFamily = customFont)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = { navController.navigate("login") }, // Navegar a LoginScreen
                        modifier = Modifier.fillMaxWidth().height(50.dp)
                    ) {
                        Text("Ir a Iniciar Sesión", fontFamily = customFont)
                    }
                }
            }
        }
    }
}
fun Registrar(name: String, email: String, password: String, navController: NavController, context: Context){
    if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
        Toast.makeText(context, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
    } else {
        val auth = FirebaseAuth.getInstance() // Get FirebaseAuth instance
        val db = FirebaseFirestore.getInstance() // Get Firestore instance
        val currentDate = System.currentTimeMillis() // Obtener la fecha y hora actual

        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Guardar información del usuario en Firestore
                    val userId = auth.currentUser?.uid
                    val user = hashMapOf(
                        "name" to name,
                        "email" to email,
                        "accessCount" to 1, // Set initial access count to 1
                        "lastAccess" to currentDate // Guardar la fecha actual
                    )
                    userId?.let {
                        db.collection("Usuarios").document(it).set(user)
                            .addOnSuccessListener {
                                // Iniciar notificaciones periódicas al iniciar sesion
                                (context as MainActivity).startPeriodicNotification()
                                navController.navigate("home/$userId/$name/1")
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, "Error guardando usuario: ${e.message}", Toast.LENGTH_SHORT).show()
                            }
                    }
                } else {
                    val exception = task.exception
                    if (exception is FirebaseAuthUserCollisionException) {
                        Toast.makeText(context, "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error en el registro: ${exception?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            .addOnFailureListener { e ->
                Toast.makeText(context, "Error en la autenticación: ${e.message}", Toast.LENGTH_SHORT).show()
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

