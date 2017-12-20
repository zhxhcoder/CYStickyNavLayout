package com.android.cy.pullrecyclerview;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.android.cy.pullrecyclerview.adapter.HomeAdapters;
import com.android.cy.pullrecyclerview.view.CYStickyNavLayouts;


public class HomeActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        CYStickyNavLayouts layout = (CYStickyNavLayouts) findViewById(R.id.head_home_layout);
        layout.setOnStartActivity(new CYStickyNavLayouts.OnStartActivityListener() {
            @Override
            public void onStart() {
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        RecyclerView mHeadRecyclerView = (RecyclerView) findViewById(R.id.head_home_recyclerview);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        mHeadRecyclerView.setLayoutManager(layoutManager2);
        HomeAdapters mHomeAdapter = new HomeAdapters();
        mHeadRecyclerView.setAdapter(mHomeAdapter);
    }
}
