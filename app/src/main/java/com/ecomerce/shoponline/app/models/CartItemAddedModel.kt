package com.ecomerce.shoponline.app.models

data class CartItemAddedModel(val position:Int,val imageUrl:String,val productId:Int,val productQuantity: String,val title:String,val amount:String,val category:String)
