package com.emengsoft.orderingapp.views.tab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emengsoft.orderingapp.R
import com.emengsoft.orderingapp.contracts.UserOrderContract
import com.emengsoft.orderingapp.extensions.gone
import com.emengsoft.orderingapp.extensions.visible
import com.emengsoft.orderingapp.models.UserOrder
import com.emengsoft.orderingapp.presenters.UserOrderPresenter
import com.emengsoft.orderingapp.views.adapter.MyOrderAdapter
import com.emengsoft.orderingapp.views.main.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_main.view.*

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */
 
class MyOrderTab : Fragment(), UserOrderContract.GetOrder {

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.tab_main, container, false)

        mView.toOrderBtn.text = getString(R.string.check_my_order)

        val userOrderPres = UserOrderPresenter(this)

        mView.swipeRefresh.setOnRefreshListener {
            userOrderPres.getUserOrders()
        }

        mView.toOrderBtn.setOnClickListener {
            userOrderPres.getUserOrders()
        }

        // call function class
        recyclerScrollBehaviour()

        return mView
    }

    override fun showLoading() {

        (activity as MainActivity).progressBar.visible()
        mView.listItem.gone()

        // hide swipe refreshing if progress bar is show
        mView.swipeRefresh.isRefreshing = false

    }

    override fun userOrders(orders: List<UserOrder>) {

        // serve data on adapter to recycler view
        mView.listItem.layoutManager = LinearLayoutManager(activity)
        mView.listItem.adapter = MyOrderAdapter(activity!!, orders)
        (mView.listItem.adapter as MyOrderAdapter).notifyDataSetChanged()

    }

    override fun hideLoading() {

        (activity as MainActivity).progressBar.gone()
        mView.listItem.visible()

    }

    private fun recyclerScrollBehaviour() {

        mView.listItem.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                // if recycler view is scrolled btn order slide down
                mView.toOrderBtn.animate().alpha(0F).translationY(400F).setDuration(600).start()

            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {

                // if recycler view not scrolled btn order slide to up (show view)
                if(newState == RecyclerView.SCROLL_STATE_IDLE) {

                    mView.toOrderBtn.alpha = 0F
                    mView.toOrderBtn.translationY = 400F
                    mView.toOrderBtn.animate().alpha(1F).translationY(0F).setDuration(600).start()

                }

                super.onScrollStateChanged(recyclerView, newState)
            }

        })

    }

}