# Workshop UCundinamarca - Android

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Kotlin-0095D5?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack_Compose-4285F4?style=for-the-badge&logo=jetpack-compose&logoColor=white)
![Firebase](https://img.shields.io/badge/Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Gemini AI](https://img.shields.io/badge/Gemini_AI-4285F4?style=for-the-badge&logo=google-gemini&logoColor=white)
![Hilt](https://img.shields.io/badge/Dagger_Hilt-00B0FF?style=for-the-badge&logo=google&logoColor=white)

Aplicacion Android para workshop de la Universidad de Cundinamarca.

## 🚀 Características Principales

- **🤖 AI Chat Integration**: Chatbot inteligente impulsado por Google Gemini AI para asistencia contextual.
- **📅 Attendance Management**: Registro y validación de asistencias mediante Firebase Firestore.
- **💾 Local Storage**: Persistencia de datos eficiente utilizando Room Database.
- **☁️ Firebase Ecosystem**: Integración completa con Firestore y Remote Config para dinamicidad.
- **🎨 Modern UI**: Interfaz de usuario premium construida 100% con Jetpack Compose y Material 3.
- **🧭 Advanced Navigation**: Naveguación fluida con Navigation Compose y soporte para WebViews integrados.

## 🛠️ Tech Stack

- **Lenguaje**: [Kotlin](https://kotlinlang.org/)
- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose)
- **Dependency Injection**: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- **Database**: [Room](https://developer.android.com/training/data-storage/room)
- **Network & Images**: [Coil 3](https://coil-kt.github.io/coil/) con soporte para OkHttp.
- **AI**: [Google AI SDK for Android](https://ai.google.dev/) (Gemini).
- **Backend**: [Firebase Firestore](https://firebase.google.com/docs/firestore) & [Remote Config](https://firebase.google.com/docs/remote-config).
- **Arquitectura**: MVVM (Model-View-ViewModel) con Clean Architecture.

## ⚙️ Configuración del Proyecto

Para ejecutar este proyecto localmente, sigue estos pasos:

### 1. Requisitos Previos
- Android Studio Ladybug (o superior).
- JDK 21.
- Gradle 8.x.

### 2. Clonar el Repositorio
```bash
git clone https://github.com/danielveloper/workshop-ucundinamarca-android.git
cd workshop-ucundinamarca-android
```

### 3. API Keys
Este proyecto requiere una API Key de Gemini. Debes agregarla en tu archivo `local.properties`:

```properties
GEMINI_API_KEY=tu_api_key_aqui
```

> [!IMPORTANT]
> Nunca el archivo `local.properties` al control de versiones para mantener la llave segura.

### 4. Firebase
Asegúrate de tener el archivo `google-services.json` correspondiente en el directorio `app/`.

## 📦 Estructura del Proyecto

```text
app/src/main/java/edu/ucundinamarca/workshop/
├── core/               # Utilidades y configuración base
├── data/               # Repositorios y fuentes de datos
├── domain/             # Modelos de negocio y casos de uso
├── features/           # Módulos por funcionalidad
├── shared/             # Componentes UI y lógica compartida
└── WorkshopApplication.kt
```
