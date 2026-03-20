# Firebase Setup Instructions

Este directorio contiene las herramientas necesarias para configurar Firestore y Remote Config para el feature de Home.

## Requisitos Previos

1.  **Firebase CLI instalado**:
    ```bash
    npm install -g firebase-tools
    ```
2.  **Login en Firebase**:
    ```bash
    firebase login
    ```
3.  **Seleccionar el proyecto**:
    ```bash
    firebase use --add
    ```

## Configuración de Firestore

Para cargar los datos iniciales en Firestore, puedes usar el script de Python proporcionado o hacerlo manualmente siguiendo la estructura en `data_seeds/home_info.json`.

### Estructura Requerida:
- **Colección**: `home`
- **Documento**: `info`
- **Campos**: Ver `data_seeds/home_info.json`.

### Uso del Script de Configuración (Python):
1. Instala las dependencias:
   ```bash
   pip install firebase-admin
   ```
2. Descarga tu archivo de credenciales de cuenta de servicio (`serviceAccountKey.json`) desde la consola de Firebase.
3. Ejecuta el script:
   ```bash
   python scripts/setup_firestore.py
   ```

## Configuración de Remote Config

Se pueden manejar etiquetas y configuraciones rápidas desde Remote Config.

1. Obtén la plantilla actual:
   ```bash
   firebase remoteconfig:get > remote_config_current.json
   ```
2. Mezcla o actualiza usando la plantilla proporcionada en `data_seeds/remote_config_template.json`.
3. Sube los cambios:
   ```bash
   firebase remoteconfig:rollback --force # O usa el dashboard para mayor seguridad
   ```
   *Nota: El CLI de Remote Config es limitado para creaciones masivas sin plantillas completas.*

## Scripts Disponibles

- `scripts/setup_firestore.py`: Carga los datos de `home_info.json` a Firestore.
