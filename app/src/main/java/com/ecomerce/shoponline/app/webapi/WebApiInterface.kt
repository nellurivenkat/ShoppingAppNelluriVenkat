package com.ecomerce.shoponline.app.webapi

import com.ecomerce.shoponline.app.models.SignUpModel
import com.ecomerce.shoponline.app.webapiresponse.SignUpResponse
import com.ecomerce.shoponline.app.webapiresponse.SpecificItemResponse
import com.ecomerce.shoponline.app.webapiresponse.UserSignUpResponse
import retrofit2.Call
import retrofit2.http.*

interface WebApiInterface {
    companion object {
        const val BASE_URL: String = "https://fakestoreapi.com"
    }

    @POST("/users")
    fun signUpUser(@Body signUpModel: SignUpModel):Call<UserSignUpResponse>
    @GET("/products/categories")
    fun getProductCategories():Call<MutableList<String>>
    @GET("/products/category/" +
            "{specific}")
    fun getProductCategoriesSpecific(@Path("specific")  specific:String):Call<MutableList<SpecificItemResponse>>
    @FormUrlEncoded
    @POST("/auth/login")
    fun loginUser(@Field("username") username:String,@Field("password") password:String):Call<SignUpResponse>
}