package com.emengsoft.orderingapp.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emengsoft.orderingapp.R
import com.emengsoft.orderingapp.models.UserOrder
import kotlinx.android.synthetic.main.adapter_my_order.view.*

/**
 * Created by Fajar Agung Pramana on 08 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

class MyOrderAdapter(private val mContext: Context,
                        private val mMyOrders: List<UserOrder>) : RecyclerView.Adapter<MyOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_my_order, parent, false))

    override fun getItemCount(): Int = mMyOrders.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMenuOrder(mMyOrders[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindMenuOrder(userOrder: UserOrder) {

            // set data model to adapter object
            itemView.username.text = userOrder.username
            itemView.noTable.text = "No. Table: ${userOrder.table}"


        }

    }

}