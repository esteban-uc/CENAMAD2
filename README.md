# CENAMAD

Muestra la investigación del centro CENAMAD: líneas de investigación, investigadores, proyectos, publicaciones y la ubicación del centro.

Funcionalidades
Registro e inicio de sesión con Room. Las contraseñas se guardan con hash SHA-256 y la sesión queda guardada en el dispositivo. El registro también crea el usuario en la API externa.
Perfil de usuario con opción de cerrar sesión.
Navegación con barra superior, barra inferior y menú lateral. Las transiciones entre pantallas son animadas.
Secciones: Inicio, Investigación, CENAMAD, Novedades, Explorar, Guardados e Indicadores, con vistas de detalle para cada línea, investigador, proyecto y publicación.
Ubicación (GPS): mapa de Google Maps con la posición de CENAMAD y la del usuario. Permite guardar y consultar ubicaciones en la API.
Notificaciones locales al iniciar sesión.

Recursos nativos
GPS (ubicación del usuario en el mapa)
Notificaciones locales

Consumo de API
API REST creada en Xano y consumida con Retrofit, con endpoints de usuarios y de ubicaciones (GET, POST, PUT, PATCH, DELETE y HEAD).

Pruebas unitarias
Pruebas con JUnit sobre las validaciones del formulario (ValidadorFormulario).

Tecnologías
Kotlin y Jetpack Compose
Arquitectura MVVM
Room (con KSP) para la base de datos local
Retrofit y Gson para el consumo de la API
Navigation Compose
Google Maps Compose y Play Services Location

APK
El APK firmado se generó desde Android Studio

Equipo: Tania Silva
        Esteban Ramirez
        Benjamín Escudero
