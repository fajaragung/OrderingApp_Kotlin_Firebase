package com.emengsoft.orderingapp.models

/**
 * Created by Fajar Agung Pramana on 07 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */

data class UserOrder(

    val username: String?,
    val table: String?,
    val date: String?,
    val orders: List<CoffeeOrder>?

)