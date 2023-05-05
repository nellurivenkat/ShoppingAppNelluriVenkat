package com.ecomerce.shoponline.app.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.activities.SpecificProductActivity
import com.ecomerce.shoponline.app.models.CartItemAddedModel
import com.google.gson.Gson

class OrderAdapter(val context: Context, private val mutableList:MutableList<CartItemAddedModel>): RecyclerView.Adapter<OrderAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView =itemView.findViewById(R.id.cart_shop_specific_text)
        val totalAmount: TextView =itemView.findViewById(R.id.cart_shop_specific_text_3)
        val quantity: TextView =itemView.findViewById(R.id.cart_shop_specific_text_2)
        val image: ImageView =itemView.findViewById(R.id.cart_shop_specific_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view= LayoutInflater.from(context).inflate(R.layout.cart_template,parent,false)
        return  MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //  holder.title.ellipsize=TextUtils.TruncateAt.valueOf()
        holder.title.text="productId:".plus(mutableList[position].productId.toString())
        holder.totalAmount.text=mutableList[position].amount
        holder.quantity.text=mutableList[position].productQuantity
        Glide.with(context).asBitmap().load(mutableList[position].imageUrl).into(holder.image)
        holder.itemView.setOnClickListener {
            val data=mutableList[position]
          context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE).edit().putString("display",Gson().toJson(data)).apply()
            context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE).edit().putBoolean("isDisplayCalled",true).apply()
            val intent=Intent(context,SpecificProductActivity::class.java)
            intent.putExtra("category",mutableList[position].category )
            Log.d("FragCartc",mutableList[position].category)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return  mutableList.size
    }
}