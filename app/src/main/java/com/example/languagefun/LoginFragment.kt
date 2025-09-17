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

/**
 * LoginFragment
 *
 * 用于展示登录界面与处理登录逻辑。
 * 成功后通过 Navigation 跳转到 Dashboard，并传递 keypass。
 *
 * 布局：R.layout.fragment_login
 * 依赖：Navigation Component + Hilt
 */
@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 1) 绑定视图
        val etUsername: EditText = view.findViewById(R.id.emailEditText)
        val etPassword: EditText = view.findViewById(R.id.passwordEditText)
        val btnStartLearning: Button = view.findViewById(R.id.loginButton)

        // 2) 点击登录：读取输入并调用 login()
        btnStartLearning.setOnClickListener {
            val campus = "footscray" // 你的校区：footscray / sydney / br（之后可做下拉切换）
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(requireContext(), "Please enter username & password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            viewModel.login(campus, username, password)
        }

        // 3) 收集状态并处理结果
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is LoginUiState.Idle -> Unit

                        is LoginUiState.Loading -> {
                            Toast.makeText(requireContext(), "Logging in...", Toast.LENGTH_SHORT).show()
                        }

                        is LoginUiState.Success -> {
                            Toast.makeText(requireContext(), "Login success!", Toast.LENGTH_SHORT).show()

                            // ✅ 使用 Safe Args（推荐）把 keypass 传给 DashboardFragment
                            // 需要在 nav_graph.xml 给 dashboardFragment 声明 <argument name="keypass" app:argType="string" />
                            val action = LoginFragmentDirections.actionLoginToDashboard(state.keypass)
                            findNavController().navigate(action)

                            // ❗如果暂时不使用 Safe Args，也可以用 Bundle 方案（取消注释）：
                            // findNavController().navigate(
                            //     R.id.dashboardFragment,
                            //     bundleOf("keypass" to state.keypass)
                            // )
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
