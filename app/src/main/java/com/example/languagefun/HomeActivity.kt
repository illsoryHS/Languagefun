package com.example.languagefun

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.languagefun.ui.dashboard.EntityAdapter
import com.example.languagefun.viewmodel.DashboardUiState
import com.example.languagefun.viewmodel.DashboardViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {

    private val vm: DashboardViewModel by viewModels()
    private lateinit var recycler: RecyclerView
    private lateinit var adapter: EntityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_home)

        recycler = findViewById(R.id.recyclerEntities)
        adapter = EntityAdapter(onClick = { entity ->
            val i = Intent(this, DetailsActivity::class.java)
            i.putExtra("entity", entity) // DashboardEntityDto implements Serializable
            startActivity(i)
        })
        recycler.layoutManager = LinearLayoutManager(this)
        recycler.adapter = adapter

        val keypass = intent.getStringExtra("keypass")
        if (!keypass.isNullOrEmpty()) {
            vm.loadDashboard(keypass)
        } else {
            Toast.makeText(this, "Missing keypass", Toast.LENGTH_LONG).show()
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                vm.uiState.collect { state ->
                    when (state) {
                        is DashboardUiState.Idle -> Unit
                        is DashboardUiState.Loading ->
                            Toast.makeText(this@HomeActivity, "Loading dashboard...", Toast.LENGTH_SHORT).show()

                        is DashboardUiState.Success -> {
                            // 列表是 List<DashboardEntityDto>
                            adapter.submitList(state.entities)
                        }

                        is DashboardUiState.Error ->
                            Toast.makeText(this@HomeActivity, state.message, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}

