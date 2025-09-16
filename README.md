# LanguageFun

A simple **language learning app prototype** built for the NIT3213 Mobile Application Development final assignment.  
The app demonstrates **login, dashboard, and details screens**, integrated with a provided REST API and implemented using **Android best practices**.

---

##  Features

- **Login Screen**  
  - Enter username & password, authenticate via API.  
  - Campus endpoint (`footscray`, `sydney`, `br`) supported.  
  - On success → receives a `keypass` token for further requests.

- **Dashboard Screen**  
  - Fetches data from `GET /dashboard/{keypass}` using Retrofit.  
  - Displays a list of `entities` in a RecyclerView.  
  - Each item shows summary info (e.g. album title, artist, year).  
  - Clicking an item navigates to the Details screen.

- **Details Screen**  
  - Shows the full information of the selected entity, including `description`.  

---

##  Tech Stack

- **Language:** Kotlin  
- **UI:** Material Design 3, ConstraintLayout, RecyclerView  
- **Architecture:** MVVM + Clean code principles  
- **Dependency Injection:** Hilt  
- **Networking:** Retrofit2 + Gson  
- **Asynchrony:** Coroutines + Flow  
- **Testing:**  
  - JUnit4  
  - MockK (mocking repository)  
  - kotlinx-coroutines-test (testing flows)  
  - Robolectric (RecyclerView adapter tests with Android resources)  

---


## How to Run

1. Clone repo & open in **Android Studio** (latest stable).
2. Ensure you have **Android SDK 33** installed.
3. Run on emulator or device:
   - Username: `Allen`
   - Password: `8115345`
   - Campus: `footscray`
4. Login → loads dashboard → click item → details screen.

---

##  Running Tests

Unit tests are in `src/test/java`.

- Run all tests:
  ```bash
  ./gradlew test
