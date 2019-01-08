package com.emengsoft.orderingapp.models

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */
 
data class CoffeeOrder (

    val name: String?,
    var price: Int,
    var qty: Int,
    var isOrder: Boolean?

)