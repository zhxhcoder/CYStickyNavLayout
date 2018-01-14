package com.android.cy.pullrecyclerview

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import com.android.cy.pullrecyclerview.adapter.HomeAdapters
import com.android.cy.pullrecyclerview.view.CYStickyNavLayouts


class HomeActivity : FragmentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val layout = findViewById(R.id.head_home_layout) as CYStickyNavLayouts
        layout.setOnStartActivity {
            val intent = Intent(this@HomeActivity, MainActivity::class.java)
            startActivity(intent)
        }

        val mHeadRecyclerView = findViewById(R.id.head_home_recyclerview) as RecyclerView
        val layoutManager2 = LinearLayoutManager(this)
        layoutManager2.orientation = LinearLayoutManager.HORIZONTAL
        mHeadRecyclerView.layoutManager = layoutManager2
        val mHomeAdapter = HomeAdapters()
        mHeadRecyclerView.adapter = mHomeAdapter
    }
}
