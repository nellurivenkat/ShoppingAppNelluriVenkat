package com.ecomerce.shoponline.app.models

import com.ecomerce.shoponline.app.webapi_objects.address
import com.ecomerce.shoponline.app.webapi_objects.name

data class SignUpModel(val email:String, val username:String, val password:String, val name: name,val address: address,val phone:String)
