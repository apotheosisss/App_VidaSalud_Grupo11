# 🏥 VidaSalud App - Grupo 11

Aplicación móvil integral para el monitoreo de salud y bienestar, desarrollada como parte de la Evaluación Parcial 4 de Desarrollo de Aplicaciones Móviles.

## 👥 Integrantes
* **Brian Aravena**
* **Claudio Aro**

---

## 📱 Contexto y Solución
**VidaSalud** nace para solucionar la falta de herramientas centralizadas que permitan a los usuarios monitorear sus hábitos diarios de forma sencilla. La aplicación permite:
* Registrar y visualizar horas de sueño.
* Llevar un control de la ingesta nutricional (calorías, proteínas, grasas).
* Recibir consejos de salud aleatorios desde una fuente externa.
* Autenticación segura y persistencia de datos en la nube.

---

## 🚀 Funcionalidades Principales

1.  **Autenticación de Usuarios:** Registro e inicio de sesión conectados a microservicio Spring Boot.
2.  **Gestión de Sueño:** Registro diario de horas de sueño y visualización de historial.
3.  **Gestión de Alimentación:** Input de macros diarios y cálculo de promedios.
4.  **Consejos Inteligentes:** Consumo de API externa para mostrar tips de bienestar.
5.  **Seguridad Biométrica:** Opción de verificar identidad con huella dactilar (en dispositivos compatibles).
6.  **Sincronización:** Todos los datos (excepto consejos) se persisten en una base de datos H2 remota.

---

## 🔗 Endpoints y Arquitectura

El proyecto utiliza una arquitectura de **Microservicios** con **Spring Boot** y consume una **API Externa**.

### 🛠️ Microservicios Propios (Spring Boot)
Base URL: `http://10.0.2.2:8080/api/` (Desde el emulador)

| Método | Endpoint | Descripción |
| :--- | :--- | :--- |
| **POST** | `/auth/register` | Registra un nuevo usuario en la BD. |
| **POST** | `/auth/login` | Verifica credenciales y retorna el usuario con su ID. |
| **POST** | `/datos/sueno` | Guarda un registro de sueño vinculado al usuario. |
| **GET** | `/datos/sueno/{id}` | Obtiene el historial de sueño de un usuario. |
| **POST** | `/datos/alimentacion` | Guarda un registro nutricional (calorías/macros). |
| **GET** | `/datos/alimentacion/{id}`| Obtiene el historial de alimentación de un usuario. |

### 🌍 API Externa (AdviceSlip)
Utilizada en la pantalla de "Consejos" para obtener recomendaciones aleatorias.
* **URL:** `https://api.adviceslip.com/advice`
* **Método:** `GET`
* **Respuesta:** JSON con ID y texto del consejo.

---

## 🧪 Pruebas Unitarias
El proyecto cuenta con una cobertura de pruebas unitarias superior al 80% en la lógica de negocio (ViewModels), utilizando:
* **JUnit 4**
* **Mockk** (para simular API y Sesión)
* **Kotlinx Coroutines Test**

---

## ⚙️ Pasos para Ejecutar el Proyecto

### 1. Backend (Microservicio)
1.  Abrir el proyecto `Backend` en **IntelliJ IDEA**.
2.  Esperar que carguen las dependencias Maven.
3.  Ejecutar la clase `VidaSaludBackendApplication`.
4.  Verificar que corra en el puerto `8080`.

### 2. Aplicación Móvil (Android)
1.  Abrir el proyecto `App_VidaSalud_Grupo11` en **Android Studio**.
2.  Sincronizar Gradle (`Sync Project`).
3.  Seleccionar un emulador (se recomienda API 30+).
4.  Dar clic en **Run 'app'**.

> **Nota:** Para que la app conecte con el backend local desde el emulador, se utiliza la IP `10.0.2.2`. Asegúrese de que el backend esté encendido antes de iniciar sesión.

---

## 📦 Evidencia de Entrega (APK Firmado)

### Captura de generación de APK Firmado (Release):
![APK Generado](capturas/build_cap.png)

### Captura del archivo Keystore (.jks):
![JKS File](capturas/keystore_cap.png)