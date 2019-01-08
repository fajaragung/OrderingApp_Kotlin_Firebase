package com.emengsoft.orderingapp.contracts

import com.emengsoft.orderingapp.models.CoffeeOrder

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

interface CoffeeContract {

    interface View {

        fun showLoading()
        fun coffeeMenu(coffees: List<CoffeeOrder>)
        fun hideLoading()

    }

    interface Presenter {

        fun getCoffeeMenu()

    }

}