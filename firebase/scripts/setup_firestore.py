import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore
import json
import os

# 1. Configurar credenciales
# Descarga serviceAccountKey.json desde Firebase Console -> Configuración del proyecto -> Cuentas de servicio
SERVICE_ACCOUNT_PATH = 'serviceAccountKey.json'
DATA_PATH = '../data_seeds/home_info.json'

if not os.path.exists(SERVICE_ACCOUNT_PATH):
    print(f"Error: No se encontró {SERVICE_ACCOUNT_PATH}")
    print("Sigue las instrucciones en firebase/instructions.md para obtenerlo.")
    exit(1)

# 2. Inicializar App
cred = credentials.Certificate(SERVICE_ACCOUNT_PATH)
firebase_admin.initialize_app(cred)

db = firestore.client()

# 3. Cargar datos
with open(DATA_PATH, 'r') as f:
    data = json.load(f)

# 4. Escribir en Firestore
print("Subiendo datos a Firestore...")
doc_ref = db.collection('home').document('info')
doc_ref.set(data)

print("¡Éxito! Colección 'home', documento 'info' actualizado.")
