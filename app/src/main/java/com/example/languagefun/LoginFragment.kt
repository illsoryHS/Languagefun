package com.example.languagefun

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.example.languagefun.viewmodel.LoginUiState
import com.example.languagefun.viewmodel.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

// Fragment responsible for displaying the login screen and handling login logic.
// On successful login, navigates to DashboardFragment and passes the keypass.
// Layout: R.layout.fragment_login
// Dependencies: Navigation Component + Hilt
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels() // Injected ViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Bind view references
        val etUsername: EditText = view.findViewById(R.id.emailEditText)
        val etPassword: EditText = view.findViewById(R.id.passwordEditText)
        val btnStartLearning: Button = view.findViewById(R.id.loginButton)

        // Handle login button click: validate inputs and call login()
        btnStartLearning.setOnClickListener {
            val campus = "footscray" // Campus identifier: "footscray", "sydney", or "br"
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter username & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Call ViewModel to initiate login
            viewModel.login(campus, username, password)
        }

        // Collect UI state updates from ViewModel and react accordingly
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is LoginUiState.Idle -> Unit // No action

                        is LoginUiState.Loading -> {
                            Toast.makeText(requireContext(), "Logging in...", Toast.LENGTH_SHORT).show()
                        }

                        is LoginUiState.Success -> {
                            Toast.makeText(requireContext(), "Login success!", Toast.LENGTH_SHORT).show()

                            // Navigate to DashboardFragment using Safe Args (recommended)
                            val action = LoginFragmentDirections.actionLoginToDashboard(state.keypass)
                            findNavController().navigate(action)
                        }

                        is LoginUiState.Error -> {
                            Toast.makeText(requireContext(), "Invalid username or password", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            }
        }
    }
}

