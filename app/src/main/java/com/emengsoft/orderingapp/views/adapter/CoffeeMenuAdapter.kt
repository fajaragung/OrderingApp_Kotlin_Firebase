package com.emengsoft.orderingapp.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emengsoft.orderingapp.R
import com.emengsoft.orderingapp.models.CoffeeOrder
import kotlinx.android.synthetic.main.adapter_menu.view.*

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

class CoffeeMenuAdapter(private val mContext: Context,
                        private val mCoffees: List<CoffeeOrder>) : RecyclerView.Adapter<CoffeeMenuAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_menu, parent, false))

    override fun getItemCount(): Int = mCoffees.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMenuOrder(mCoffees[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        @SuppressLint("SetTextI18n")
        fun bindMenuOrder(coffeeOrder: CoffeeOrder) {

            // set data model to adapter object
            itemView.coffeeName.text = coffeeOrder.name
            itemView.coffeePrice.text = "Price: Rp.${coffeeOrder.price}.000"

        }

    }

}