package com.emengsoft.orderingapp.contracts

import com.emengsoft.orderingapp.models.CoffeeOrder
import com.emengsoft.orderingapp.models.UserOrder

/**
 * Created by Fajar Agung Pramana on 07 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

interface UserOrderContract {

    interface SetOrder {

        fun orderSuccessfull()
        fun orderFailure()

    }

    interface GetOrder {

        fun showLoading()
        fun userOrders(orders: List<UserOrder>)
        fun hideLoading()

    }

    interface Presenter {

        fun setUserOrders(username: String?,
                          table: String?,
                          date: String?,
                          orders: List<CoffeeOrder>)

        fun getUserOrders()

    }

}