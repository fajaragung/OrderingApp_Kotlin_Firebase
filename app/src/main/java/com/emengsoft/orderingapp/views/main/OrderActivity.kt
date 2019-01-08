package com.emengsoft.orderingapp.views.main

import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.Menu
import android.widget.Toast
import com.emengsoft.orderingapp.R
import com.emengsoft.orderingapp.contracts.CoffeeContract
import com.emengsoft.orderingapp.contracts.MainView
import com.emengsoft.orderingapp.contracts.UserOrderContract
import com.emengsoft.orderingapp.extensions.gone
import com.emengsoft.orderingapp.extensions.visible
import com.emengsoft.orderingapp.models.CoffeeOrder
import com.emengsoft.orderingapp.presenters.CoffeePresenter
import com.emengsoft.orderingapp.presenters.UserOrderPresenter
import com.emengsoft.orderingapp.views.adapter.CoffeeOrderAdapter
import com.emengsoft.orderingapp.views.adapter.CoffeeOrderedAdapter
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.alert_confirm_order.view.*
import kotlinx.android.synthetic.main.support_toolbar.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

class OrderActivity : AppCompatActivity(),
    CoffeeContract.View,
    MainView.IsOrder,
    MainView.IsOrderRemoved,
    UserOrderContract.SetOrder {

    private val mOrders = mutableListOf<CoffeeOrder>()

    private lateinit var mCoffeePres: CoffeePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        mCoffeePres = CoffeePresenter(this)
        mCoffeePres.getCoffeeMenu()

        // refresh data using swipe refresh
        swipeRefresh.setOnRefreshListener {
            mCoffeePres.getCoffeeMenu()
        }

        // set date order
        dateOrder.text = SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).format(Date())

        // call function class
        setToolbar()
        recyclerScrollBehaviour()

    }

    override fun showLoading() {
        progressBar.visible()
        listItemToOrder.gone()

        // if progress bar show, swipe refresh gone
        swipeRefresh.isRefreshing = false

    }

    override fun coffeeMenu(coffees: List<CoffeeOrder>) {

        // set data on adapter to recycler view
        listItemToOrder.layoutManager = LinearLayoutManager(this@OrderActivity)
        listItemToOrder.adapter = CoffeeOrderAdapter(this@OrderActivity, coffees, this)
        (listItemToOrder.adapter as CoffeeOrderAdapter).notifyDataSetChanged()

    }

    override fun hideLoading() {
        progressBar.gone()
        listItemToOrder.visible()
    }

    override fun coffeeOrderSelected(orders: CoffeeOrder) {
        mOrders.add(
            orders
        )

        // inflate layout
        val view = LayoutInflater.from(this@OrderActivity).inflate(R.layout.alert_confirm_order, null)

        orderBtn.setOnClickListener {

            if(validateInput()) {

                if(orders.isOrder == true && orders.qty > 0) {

                    // set data adapter to recycler view
                    view.listOrdered.layoutManager = LinearLayoutManager(this)
                    view.listOrdered.adapter = CoffeeOrderedAdapter(this, mOrders, this)
                    (view.listOrdered.adapter as CoffeeOrderedAdapter).notifyDataSetChanged()

                    // show selected order on custom alert dialog
                    AlertDialog.Builder(this@OrderActivity)
                        .setView(view)
                        .setNegativeButton(getString(R.string.cancel), {dialog, which ->
                            mOrders.clear()
                            mCoffeePres.getCoffeeMenu()
                        })
                        .setPositiveButton(getString(R.string.order), {dialog, which ->

                            val userOrderPres = UserOrderPresenter(this)
                            userOrderPres.setUserOrders(
                                fieldName.text.toString(),
                                fieldNoTable.text.toString(),
                                SimpleDateFormat("EEE, dd MMM yyyy", Locale.getDefault()).format(Date()),
                                mOrders)

                            mOrders.clear()
                            finish()

                        })
                        .setCancelable(false)
                        .show()

                }

            }

        }

    }

    override fun coffeeOrderRemoved(i: Int) {
        try {
            mOrders.removeAt(i)
        } catch(exc: Exception) {
            print(exc.message)
        }
    }

    override fun removeOrder(i: Int) {
        mOrders.removeAt(i)
    }

    override fun orderSuccessfull() {
        Toast.makeText(this@OrderActivity, getString(R.string.order_successfully), Toast.LENGTH_SHORT).show()
    }

    override fun orderFailure() {
        Toast.makeText(this@OrderActivity, getString(R.string.order_failure), Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.action_main, menu)
        menu?.findItem(R.id.actionSearch)?.isVisible = false // hide action search
        return true

    }

    // setup toolbar
    private fun setToolbar() {

        setSupportActionBar(toolbar)
        supportActionBar?.title = null
        toolbarTitle.text = getString(R.string.app_name)

    }

    private fun recyclerScrollBehaviour() {

        listItemToOrder.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                // if recycler view is scrolled btn order slide down
                orderBtn.animate().alpha(0F).translationY(400F).setDuration(600).start()

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                // if recycler view not scrolled btn order slide to up (show view)
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {

                    orderBtn.alpha = 0F
                    orderBtn.translationY = 400F
                    orderBtn.animate().alpha(1F).translationY(0F).setDuration(600).start()

                }

                super.onScrollStateChanged(recyclerView, newState)
            }

        })

    }

    // validate user input
    private fun validateInput(): Boolean {

        var valid = false

        if(fieldName.text.isNotEmpty()) {
            valid = true

            if(fieldNoTable.text.toString().isNotEmpty()) {
                valid = true

            } else {
                valid = false
                fieldNoTable.error = getString(R.string.invalid_no_table)

            }

        } else {
            valid = false
            fieldName.error = getString(R.string.invalid_name)

        }

        return valid

    }

}