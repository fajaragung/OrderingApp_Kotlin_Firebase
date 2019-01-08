package com.emengsoft.orderingapp.views.tab

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emengsoft.orderingapp.R
import com.emengsoft.orderingapp.contracts.CoffeeContract
import com.emengsoft.orderingapp.extensions.gone
import com.emengsoft.orderingapp.extensions.visible
import com.emengsoft.orderingapp.models.CoffeeOrder
import com.emengsoft.orderingapp.presenters.CoffeePresenter
import com.emengsoft.orderingapp.views.adapter.CoffeeMenuAdapter
import com.emengsoft.orderingapp.views.main.MainActivity
import com.emengsoft.orderingapp.views.main.OrderActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.tab_main.view.*
import org.jetbrains.anko.intentFor

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

class MenuTab : Fragment(), CoffeeContract.View {

    private lateinit var mView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mView = inflater.inflate(R.layout.tab_main, container, false)

        // instance
        val coffeePres = CoffeePresenter(this)
        coffeePres.getCoffeeMenu()

        // refresh data using swipe refreshing
        mView.swipeRefresh.setOnRefreshListener {
            coffeePres.getCoffeeMenu()
        }

        // move to order activity
        mView.toOrderBtn.setOnClickListener {

            startActivity(activity?.intentFor<OrderActivity>()
                ?.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP))

        }

        // call function class
        setAnimate()
        recyclerScrollBehaviour()

        return mView
    }

    override fun showLoading() {

        (activity as MainActivity).progressBar.visible()
        mView.listItem.gone()

        // hide swipe refreshing if progress bar is show
        mView.swipeRefresh.isRefreshing = false

    }

    override fun coffeeMenu(coffees: List<CoffeeOrder>) {

        // serve data on adapter to recycler view
        mView.listItem.layoutManager = LinearLayoutManager(activity)
        mView.listItem.adapter = CoffeeMenuAdapter(activity!!, coffees)
        (mView.listItem.adapter as CoffeeMenuAdapter).notifyDataSetChanged()

    }

    override fun hideLoading() {

        (activity as MainActivity).progressBar.gone()
        mView.listItem.visible()

    }

    private fun setAnimate() {

        mView.toOrderBtn.alpha = 1F
        mView.toOrderBtn.translationY = 0F

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