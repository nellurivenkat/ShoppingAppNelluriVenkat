package com.ecomerce.shoponline.app.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.adapter.CustomSpecificProductAdapter
import com.ecomerce.shoponline.app.webapi.WebApiClient
import com.ecomerce.shoponline.app.webapiresponse.SpecificItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SpecificProductActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_specific_product)
        initView()
    }
    private fun initView(){
        recyclerView=findViewById(R.id.rec_specific)
        recyclerView.layoutManager=LinearLayoutManager(this)
      performLogic()
    }

    private fun performLogic(){
        val title=intent.getStringExtra("category")
        title?.let {
            WebApiClient.getWebApiInterface().getProductCategoriesSpecific(it).enqueue(object:Callback<MutableList<SpecificItemResponse>>{
                override fun onResponse(
                    call: Call<MutableList<SpecificItemResponse>>,
                    response: Response<MutableList<SpecificItemResponse>>
                ) {
                    Log.d("specific", response.code().toString())
                    if (response.code() == 200) {

                        response.body()?.let { it1 ->
                          val adapter= getSharedPreferences(packageName,Context.MODE_PRIVATE).getString("display","")
                              ?.let { it2 ->
                                  CustomSpecificProductAdapter(
                                      this@SpecificProductActivity,
                                      it1,
                                      it2
                                  )
                              }
                            recyclerView.adapter=adapter
                        }
                    }
                }

                override fun onFailure(
                    call: Call<MutableList<SpecificItemResponse>>,
                    t: Throwable
                ) {
                    Log.d("specific", t.message!!)
                }

            })
        }
    }


}