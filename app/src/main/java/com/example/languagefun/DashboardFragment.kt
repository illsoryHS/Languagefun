package com.example.languagefun

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagefun.ui.dashboard.EntityAdapter
import com.example.languagefun.viewmodel.DashboardUiState
import com.example.languagefun.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

/**
 * DashboardFragment
 *
 * 1) 使用 keypass 拉取列表并展示（与 HomeActivity 相同逻辑）。
 * 2) 点击列表项跳到 DetailsFragment（Safe Args）。
 * 3) 布局：R.layout.fragment_home
 */
@AndroidEntryPoint
class DashboardFragment : Fragment(R.layout.fragment_home) {

    private val vm: DashboardViewModel by viewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: EntityAdapter

    // 从 Safe Args 获取 keypass（nav_graph.xml 已声明 <argument name="keypass" app:argType="string" />）
    private val args: DashboardFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView + Adapter
        recycler = view.findViewById(R.id.recyclerEntities)
        adapter = EntityAdapter(onClick = { entity ->
            // 跳转到详情（Safe Args）
            val action = DashboardFragmentDirections.actionDashboardToDetails(entity)
            findNavController().navigate(action)
        })
        recycler.layoutManager = LinearLayoutManager(requireContext())
        recycler.adapter = adapter

        // 读取 keypass 并加载
        val keypass = args.keypass
        if (!keypass.isNullOrEmpty()) {
            vm.loadDashboard(keypass)
        } else {
            Toast.makeText(requireContext(), "Missing keypass", Toast.LENGTH_LONG).show()
        }

        // 观察 UI 状态
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is DashboardUiState.Idle -> Unit
                        is DashboardUiState.Loading ->
                            Toast.makeText(requireContext(), "Loading dashboard...", Toast.LENGTH_SHORT).show()

                        is DashboardUiState.Success ->
                            adapter.submitList(state.entities)

                        is DashboardUiState.Error ->
                            Toast.makeText(requireContext(), state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

