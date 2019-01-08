package com.emengsoft.orderingapp.views.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.emengsoft.orderingapp.R
import com.emengsoft.orderingapp.contracts.MainView
import com.emengsoft.orderingapp.extensions.gone
import com.emengsoft.orderingapp.extensions.visible
import com.emengsoft.orderingapp.models.CoffeeOrder
import kotlinx.android.synthetic.main.adapter_menu.view.*

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

class CoffeeOrderAdapter(private val mContext: Context,
                         private val mCoffess: List<CoffeeOrder>,
                         private val mView: MainView.IsOrder) : RecyclerView.Adapter<CoffeeOrderAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.adapter_menu, parent, false))

    override fun getItemCount(): Int = mCoffess.size

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bindMenuOrder(mCoffess[position])
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var mQtyOrder = 0

        @SuppressLint("SetTextI18n")
        fun bindMenuOrder(coffeeOrder: CoffeeOrder) {

            // set data model to adapter object
            itemView.coffeeName.text = coffeeOrder.name
            itemView.coffeePrice.text = "Price: Rp.${coffeeOrder.price}.000"

            // card click response
            itemView.setOnClickListener {

                itemView.selectedOrder.isChecked = isCardClicked()

            }

            // show checkbox on adapter
            itemView.selectedOrder.visible()
            itemView.selectedOrder.setOnCheckedChangeListener { buttonView, isChecked ->

                if(isChecked) {

                    itemView.viewQty.visible()
                    coffeeOrder.isOrder = true

                    // set on click if decrement order btn ispressed
                    itemView.decrementOrder.setOnClickListener {

                        decrement()

                        // set qty to text
                        coffeeOrder.qty = mQtyOrder
                        itemView.qtyOrder.text = "${coffeeOrder.qty}"

                    }

                    // set on click if increment order btn is pressed
                    itemView.incrementOrder.setOnClickListener {

                        increment()

                        // set qty to text
                        coffeeOrder.qty = mQtyOrder
                        itemView.qtyOrder.text = "${coffeeOrder.qty}"


                    }

                    mView.coffeeOrderSelected(coffeeOrder)

                } else {

                    itemView.viewQty.gone()
                    coffeeOrder.isOrder = false

                    mView.coffeeOrderRemoved(adapterPosition)

                }

            }

        }

        // if btn decrement is pressed
        private fun decrement(): Int {

            if(mQtyOrder == 0) {
                return mQtyOrder
            }

            mQtyOrder = mQtyOrder - 1

            return mQtyOrder

        }

        // if btn increment is pressed
        private fun increment(): Int {

            if(mQtyOrder == 10) {
                return mQtyOrder
            }

            mQtyOrder = mQtyOrder + 1

            return mQtyOrder

        }

        private fun isCardClicked() : Boolean {

            var click = false

            click = !itemView.selectedOrder.isChecked

            return click

        }

    }

}