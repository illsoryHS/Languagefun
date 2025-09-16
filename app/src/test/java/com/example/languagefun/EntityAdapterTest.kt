@file:OptIn(kotlinx.coroutines.ExperimentalCoroutinesApi::class)

package com.example.languagefun

import android.content.Context
import android.widget.FrameLayout
import androidx.test.core.app.ApplicationProvider
import com.example.languagefun.data.remote.dto.DashboardEntityDto
import com.example.languagefun.ui.dashboard.EntityAdapter
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

// ✅ 用 Robolectric 跑，提供 Looper 等安卓环境
@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE, sdk = [33])
class EntityAdapterTest {

    private lateinit var adapter: EntityAdapter
    private lateinit var context: Context
    private lateinit var parent: FrameLayout

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext()
        parent = FrameLayout(context)
        // ⚠️ 你的适配器构造函数是 onClick
        adapter = EntityAdapter(onClick = { /* no-op */ })
    }

    @Test
    fun submitList_updatesItemCount() {
        val list = listOf(
            DashboardEntityDto(
                albumTitle = "A",
                artistName = "Artist A",
                releaseYear = 2001,
                genre = "Pop",
                trackCount = 10,
                description = "desc A",
                popularTrack = "hit A"
            ),
            DashboardEntityDto(
                albumTitle = "B",
                artistName = "Artist B",
                releaseYear = 2002,
                genre = "Rock",
                trackCount = 9,
                description = "desc B",
                popularTrack = "hit B"
            )
        )

        adapter.submitList(list)
        assertEquals(2, adapter.itemCount)
    }

    @Test
    fun clickItem_triggersCallback() {
        val item = DashboardEntityDto(
            albumTitle = "Random Access Memories",
            artistName = "Daft Punk",
            releaseYear = 2013,
            genre = "Electronic",
            trackCount = 13,
            description = "Won Grammy",
            popularTrack = "Get Lucky"
        )

        var clickedTitle: String? = null
        adapter = EntityAdapter(onClick = { clicked ->
            clickedTitle = clicked.albumTitle
        })
        adapter.submitList(listOf(item))

        // 创建并绑定 ViewHolder（无需真实 RecyclerView）
        val vh = adapter.onCreateViewHolder(parent, /* viewType = */ 0)
        adapter.onBindViewHolder(vh, 0)

        // 触发点击（要求你的适配器在 onBindViewHolder 给 itemView 设置了 setOnClickListener）
        vh.itemView.performClick()

        assertEquals("Random Access Memories", clickedTitle)
    }
}

