package com.emengsoft.orderingapp.views.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import com.emengsoft.orderingapp.R
import com.emengsoft.orderingapp.views.adapter.TabAdapter
import com.emengsoft.orderingapp.views.tab.MenuTab
import com.emengsoft.orderingapp.views.tab.MyOrderTab
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.support_toolbar.*

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // call function class
        setToolbar()
        setTabMenu()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.action_main, menu)
        return true

    }

    // setup toolbar
    private fun setToolbar() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        toolbarTitle.text = getString(R.string.app_name)

    }

    private fun setTabMenu() {

        // instance
        val tabAdapter = TabAdapter(supportFragmentManager)
        tabAdapter.setTab(MenuTab(), getString(R.string.menu))
        tabAdapter.setTab(MyOrderTab(), getString(R.string.my_order))

        pagerMain.adapter = tabAdapter  // set adapter class to view pager
        tabMain.setupWithViewPager(pagerMain)

    }

}