# LanguageFun App

An Android application developed as part of **NIT3213 – Mobile Application Development** final assignment.  
The project demonstrates proficiency in **API integration, clean architecture, dependency injection (Hilt), RecyclerView usage, and unit testing**.

---

## 📌 Features

### 1. Login Screen
- User authentication via **vu-nit3213-api** (`/footscray/auth`).
- Credentials:
  - **Username:** Allen.
  - **Password:** 8115345.
- Proper error handling for invalid login attempts.
- On success, navigates to the **Dashboard**.

### 2. Dashboard Screen
- Displays a dynamic list of entities fetched from `/dashboard/{keypass}`.
- Implemented using **RecyclerView** with efficient `Adapter` and `ViewHolder` patterns:contentReference[oaicite:6]{index=6}.
- Each item shows a **summary view** (excluding description).
- Clicking an item navigates to the **Details** screen.

### 3. Details Screen
- Presents the **full entity details**, including description, in a clean and user-friendly layout.
- Designed following **Material Design 3 guidelines**:contentReference[oaicite:7]{index=7}.

---

## ⚙️ Technical Implementation

- **Language:** Kotlin
- **Architecture:** Clean Code & MVVM:contentReference[oaicite:8]{index=8}
- **Dependency Injection:** [Hilt](https://developer.android.com/training/dependency-injection/hilt) :contentReference[oaicite:9]{index=9}
- **Networking:** Retrofit with asynchronous coroutines:contentReference[oaicite:10]{index=10}
- **RecyclerView:** Efficient list rendering with `Adapter` & `ViewHolder`:contentReference[oaicite:11]{index=11}
- **Navigation:** Jetpack Navigation Component + Safe Args:contentReference[oaicite:12]{index=12}
- **Testing:** JUnit + MockK + Coroutine test library:contentReference[oaicite:13]{index=13}

---

## 🧪 Testing

- Unit tests for **ViewModels and Adapters** using **JUnit** and **MockK**.
- Coroutine testing with `kotlinx-coroutines-test` ensuring deterministic results:contentReference[oaicite:14]{index=14}.
- Tests are located under:
- `app/src/test/java/com/example/languagefun/`


---

## 📂 Project Structure
LanguageFun/
│── app/
│ ├── src/main/java/com/example/languagefun/
│ │ ├── ui/ # Fragments & Activities
│ │ ├── data/ # DTOs, repositories
│ │ ├── di/ # Hilt modules
│ │ └── viewmodel/ # ViewModels
│ ├── res/ # Layout XML, drawables, strings
│ └── AndroidManifest.xml
│── build.gradle
│── README.md

---


---

## 🚀 How to Run

### Prerequisites
- Android Studio Ladybug (or newer)
- JDK 17+
- Minimum SDK: 24 (Android 7.0)

### Steps
1. Clone the repository: https://github.com/illsoryHS/Languagefun/tree/feat/di-and-network
2. Open the project in Android Studio.
3. Sync Gradle to install dependencies.
4. Run the app on an emulator or physical device.
5. Login using:
Username: Allen
Password: 8115345

🛠️ API Reference

Base URL: https://nit3213api.onrender.com/

Authentication
POST /{footscray}/auth
Body:
{
"username": "Allen",
"password": "8115345"
}

Dashboard
GET /dashboard/{keypass}
Response:
{
"entities": [
{
"property1": "value1",
"property2": "value2",
"description": "Detailed description"
}
],
"entityTotal": 7
}


👨‍💻 Author

Developed by Allen Li
For NIT3213 – Mobile Application Development final assessment at Victoria University.






