# TareaFinal: Aplicación de Consulta de Animes

## Descripción General

TareaFinal es una aplicación para Android que permite a los usuarios consultar información sobre animes utilizando la API de Jikan. La aplicación ofrece una interfaz de usuario atractiva y funcional, permitiendo a los usuarios navegar por una lista de animes, ver detalles específicos de cada uno, y acceder a sus trailers. Además, la aplicación incluye un sistema de autenticación de usuarios con Firebase, gestión de perfiles y notificaciones periódicas.

## Características Principales

*   **Consulta de Animes:**
    *   Muestra una lista paginada de animes obtenidos de la API de Jikan.
    *   Permite ver detalles específicos de cada anime, incluyendo título, sinopsis, rating, duración, fecha de emisión, géneros, estudios, productores, trailer, episodios, etc.
    *   Carga de imágenes de los animes usando la librería Coil.
*   **Autenticación de Usuarios:**
    *   Registro e inicio de sesión de usuarios utilizando Firebase Authentication.
    *   Almacenamiento de información del usuario en Firestore (nombre, correo electrónico, contador de accesos, fecha del último acceso).
*   **Perfil de Usuario:**
    *   Pantalla de perfil que muestra el nombre del usuario y el número de accesos.
    *   Posibilidad de cambiar la imagen de perfil.
*   **Navegación:**
    *   Uso del componente Navigation de Jetpack para la navegación entre pantallas.
*   **Notificaciones:**
    *   Notificaciones periódicas para recordar al usuario que consulte la API.
*   **Diseño:**
    *   Uso de Jetpack Compose para la interfaz de usuario.
    *   Adaptación al tema del sistema (claro/oscuro).
    *   Uso de fuentes personalizadas.
* **Manejo de errores:**
    * La aplicacion muestra mensajes de error al usuario en caso de que se produzca algun error.
* **Verificacion de Google Play Services:**
    * La aplicacion verifica si Google Play Services esta disponible.
* **Cerrar aplicacion:**
    * La aplicacion tiene un boton para cerrar la aplicacion.

## Arquitectura

La aplicación sigue el patrón de arquitectura **MVVM (Model-View-ViewModel)**:

*   **Model:**
    *   `AnimeJikan.kt`: Define las clases de datos que representan la estructura de la respuesta de la API de Jikan (`AnimeJikan`, `AnimeResponse`, `Pagination`, `AnimeData`, etc.).
*   **View:**
    *   `ApiAnimeJikanScreen.kt`: Pantalla principal que muestra la lista de animes.
    *   `DetailsAnimeScreen.kt`: Pantalla de detalles de un anime.
    *   `HomeScreen.kt`: Pantalla principal de la aplicación (después del login).
    *   `LoginScreen.kt`: Pantalla de inicio de sesión.
    *   `MainActivity.kt`: Actividad principal que gestiona la navegación y el registro/login.
    *   `RegisterScreen.kt`: Pantalla de registro.
*   **ViewModel:**
    *   `AnimeJikanViewModel.kt`: Gestiona la lógica de negocio y los datos relacionados con los animes.

## Dependencias

*   **Jetpack Compose:** Para la interfaz de usuario.
*   **Navigation:** Para la navegación entre pantallas.
*   **Coil:** Para la carga de imágenes.
*   **Retrofit:** Para la comunicación con la API de Jikan.
*   **Gson:** Para la conversión de JSON.
*   **Kotlin Coroutines:** Para operaciones asíncronas.
*   **Firebase Authentication:** Para la autenticación de usuarios.
*   **Firebase Firestore:** Para el almacenamiento de datos de usuarios.
* **Google Play Services:** Para la verificacion de Google Play Services.

## Estructura del Proyecto

El proyecto está organizado en los siguientes paquetes:

*   **`com.composse.tareafinal`:** Paquete raíz.
    *   **`model`:** Clases de datos (`AnimeJikan.kt`).
    *   **`network`:** Clases relacionadas con la red (`ApiJikanService.kt`).
    *   **`ui.theme`:** Temas de la aplicación.
    *   **`view`:** Clases relacionadas con la interfaz de usuario (`ApiAnimeJikanScreen.kt`, `DetailsAnimeScreen.kt`, `HomeScreen.kt`, `LoginScreen.kt`, `MainActivity.kt`, `RegisterScreen.kt`).
    *   **`viewmodel`:** Clases ViewModel (`AnimeJikanViewModel.kt`).
    * **`NotificationUtils.kt`:** Clase para las notificaciones.

## Flujo de la Aplicación

1.  **Inicio:**
    *   El usuario inicia la aplicación.
    *   Se muestra la pantalla de registro (`RegisterScreen.kt`).
    *   El usuario puede registrarse o ir a la pantalla de inicio de sesión.
2.  **Registro:**
    *   El usuario introduce su nombre, correo electrónico y contraseña.
    *   Se utiliza `FirebaseAuth` para crear un nuevo usuario.
    *   Se guarda la información del usuario en Firestore.
    *   Se inicia la notificacion periodica.
    *   Se navega a la pantalla principal (`HomeScreen.kt`).
3.  **Inicio de Sesión:**
    *   El usuario introduce su correo electrónico y contraseña.
    *   Se utiliza `FirebaseAuth` para autenticar al usuario.
    *   Se recupera la información del usuario de Firestore.
    *   Se actualiza el contador de accesos y la fecha del último acceso.
    *   Se inicia la notificacion periodica.
    *   Se navega a la pantalla principal (`HomeScreen.kt`).
4.  **Pantalla Principal (`HomeScreen.kt`):**
    *   Se muestra el nombre del usuario y el número de accesos.
    *   El usuario puede cambiar su imagen de perfil.
    *   Hay botones para navegar a otras pantallas:
        *   "Consultar API": Navega a la pantalla `ApiScreen.kt` (no implementada en el codigo).
        *   "Consultar API Anime": Navega a la pantalla `ApiAnimeJikanScreen.kt`.
5.  **Pantalla de Animes (`ApiAnimeJikanScreen.kt`):**
    *   Se muestra una lista de animes en una cuadrícula.
    *   Se utiliza `LazyVerticalGrid` para mostrar los animes.
    *   Se utiliza `rememberImagePainter` de Coil para cargar las imágenes.
    *   Hay un botón para cargar más animes.
    *   Al hacer clic en un anime, se navega a la pantalla de detalles (`DetailsAnimeScreen.kt`).
    *   Hay un boton para cerrar la aplicacion y otro para volver.
6.  **Pantalla de Detalles (`DetailsAnimeScreen.kt`):**
    *   Se muestra la información detallada del anime seleccionado.
    *   Se utiliza `Image` y `rememberImagePainter` para mostrar la imagen del anime.
    *   Hay un botón para ver el tráiler que abre un `Intent` con la URL del tráiler.
7. **Notificaciones:**
    * La aplicacion muestra una notificacion periodica para recordar al usuario que consulte la API.
8. **Cerrar aplicacion:**
    * La aplicacion tiene un boton para cerrar la aplicacion.

## Clases Clave

*   **`MainActivity.kt`:**
    *   Actividad principal que gestiona la navegación y el registro/login.
    *   Utiliza `NavHost` para la navegación.
    *   Verifica si Google Play Services esta disponible.
    *   Gestiona las notificaciones periodicas.
*   **`AnimeJikanViewModel.kt`:**
    *   Gestiona la lógica de negocio y los datos relacionados con los animes.
    *   Utiliza `RetrofitInstancia` para obtener los animes de la API.
    *   Utiliza `MutableStateFlow` y `StateFlow` para exponer los datos a la UI.
    *   Implementa la paginación de los animes.
*   **`ApiJikanService.kt`:**
    *   Define la interfaz para interactuar con la API de Jikan.
    *   Utiliza Retrofit para realizar las peticiones.
*   **`AnimeJikan.kt`:**
    *   Define las clases de datos que representan la estructura de la respuesta de la API de Jikan.
*   **`HomeScreen.kt`:**
    *   Pantalla principal de la aplicación (después del login).
    *   Muestra información del usuario y permite cambiar la imagen de perfil.
*   **`LoginScreen.kt`:**
    *   Pantalla de inicio de sesión.
    *   Permite al usuario introducir su correo electrónico y contraseña.
    *   Utiliza `FirebaseAuth` para la autenticación.
    *   Utiliza `FirebaseFirestore` para acceder a la base de datos.
*   **`RegisterScreen.kt`:**
    *   Permite al usuario introducir su nombre, correo electrónico y contraseña.
    *   Utiliza `FirebaseAuth` para crear un nuevo usuario.
    *   Utiliza `FirebaseFirestore` para guardar la información del usuario.
*   **`ApiAnimeJikanScreen.kt`:**
    *   Pantalla principal que muestra la lista de animes.
    *   Utiliza `LazyVerticalGrid` para mostrar los animes en una cuadrícula.
    *   Utiliza `rememberImagePainter` de Coil para cargar las imágenes.
    *   Permite navegar a la pantalla de detalles de un anime.
*   **`DetailsAnimeScreen.kt`:**
    *   Pantalla de detalles de un anime.
    *   Muestra la información detallada del anime.
    *   Permite ver el tráiler del anime.
* **`NotificationUtils.kt`:**
    * Clase para las notificaciones.
    * Crea el canal de notificaciones.
    * Muestra las notificaciones.

## Conclusión

TareaFinal es una aplicación completa que demuestra un buen conocimiento de los fundamentos del desarrollo de aplicaciones Android. Se han utilizado buenas prácticas de desarrollo, como el uso de Jetpack Compose, MVVM, Retrofit, Coil, Firebase, etc. La aplicación tiene un buen potencial y se puede mejorar con las sugerencias propuestas.