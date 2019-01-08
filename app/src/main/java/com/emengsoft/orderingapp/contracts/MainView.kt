package com.emengsoft.orderingapp.contracts

import com.emengsoft.orderingapp.models.CoffeeOrder

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

interface MainView {

    interface IsOrder {

        fun coffeeOrderSelected(orders: CoffeeOrder)
        fun coffeeOrderRemoved(i: Int)

    }

    interface IsOrderRemoved {

        fun removeOrder(i: Int)

    }

}