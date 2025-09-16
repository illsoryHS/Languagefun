package com.example.languagefun

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.languagefun.data.remote.dto.DashboardEntityDto

class DetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val entity = intent.getSerializableExtra("entity") as? DashboardEntityDto

        findViewById<TextView>(R.id.tvTitle).text    = entity?.albumTitle.orEmpty()
        findViewById<TextView>(R.id.tvSubtitle).text = entity?.artistName.orEmpty()
        findViewById<TextView>(R.id.tvDesc).text     = entity?.description.orEmpty()
    }
}





