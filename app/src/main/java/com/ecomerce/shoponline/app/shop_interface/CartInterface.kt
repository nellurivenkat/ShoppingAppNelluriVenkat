package com.ecomerce.shoponline.app.shop_interface

import com.ecomerce.shoponline.app.models.CartItemAddedModel

interface CartInterface {
    fun addCart(cartItemAddedModel: CartItemAddedModel)
}
