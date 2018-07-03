package com.android.cy.pullrecyclerview

import android.app.Activity
import android.os.Bundle
import android.widget.ScrollView

import com.android.cy.pullrecyclerview.view.CYClipView


/**
 * Created by zhxh on 2018/1/16.
 */

class TestClipMainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sv = ScrollView(this)
        val v = CYClipView(this)
        sv.addView(v)
        setContentView(sv)
    }

}