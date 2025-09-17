package com.example.languagefun

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.languagefun.viewmodel.LoginUiState
import com.example.languagefun.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import android.widget.EditText

/**
 * This activity is responsible for displaying the login screen of the application.
 * It allows users to enter their email and password to log in.
 * If the login is successful, the user is redirected to the home screen.
 */

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_login)

        // 1) 拿到输入框和按钮
        val etUsername: EditText = findViewById(R.id.emailEditText)     // ← 如果你的 id 不是这个，请改成你的
        val etPassword: EditText = findViewById(R.id.passwordEditText) // ← 同上
        val btnStartLearning: Button = findViewById(R.id.loginButton)

        // 2) 点击按钮时：读取输入并调用 login
        btnStartLearning.setOnClickListener {
            val campus = "footscray"              // 你的校区：footscray / sydney / br
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(campus, username, password)
        }

        // 3) 收集状态并处理结果（保持你现有的 when(state) 逻辑即可）
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is LoginUiState.Idle -> Unit
                        is LoginUiState.Loading ->
                            Toast.makeText(this@LoginActivity, "Logging in...", Toast.LENGTH_SHORT).show()
                        is LoginUiState.Success -> {
                            Toast.makeText(this@LoginActivity, "Login success!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                            intent.putExtra("keypass", state.keypass)
                            startActivity(intent)
                        }
                        is LoginUiState.Error ->
                            Toast.makeText(this@LoginActivity, "Invalid username or password", Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

