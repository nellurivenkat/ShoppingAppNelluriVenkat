package com.ecomerce.shoponline.app.adapter

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.activities.HomeActivity
import com.ecomerce.shoponline.app.fragment.CartFragment
import com.ecomerce.shoponline.app.models.CartItemAddedModel
import com.ecomerce.shoponline.app.models.CartModel
import com.ecomerce.shoponline.app.shop_interface.CartInterface
import com.ecomerce.shoponline.app.webapiresponse.SpecificItemResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class CustomSpecificProductAdapter(val context: Context, private val mutableList: MutableList<SpecificItemResponse>,
                                   private val dispaly:String): RecyclerView.Adapter<CustomSpecificProductAdapter.MyViewHolder>() {
    private var alertDialog:Dialog = Dialog(context)

    init {
        alertDialog.create()
    }
    class  MyViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView){
     val mText:TextView=itemView.findViewById(R.id.rec_shop_specific_text)
        val image:ImageView=itemView.findViewById(R.id.rec_shop_specific_image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view=LayoutInflater.from(context).inflate(R.layout.customspecificproduct,parent,false)
        return  MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    holder.mText.text=mutableList[position].title
        Glide.with(context).asBitmap().load(mutableList[position].image).into(holder.image)
 if (dispaly.isNotEmpty() &&  context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE).getBoolean("isDisplayCalled",false)){
     val dataList=Gson().fromJson(dispaly,CartItemAddedModel::class.java) as CartItemAddedModel
     alertDialog.setContentView(R.layout.view_specific_item)
     val title:TextView=alertDialog.findViewById(R.id.spec_item_tittle)
     val decs:TextView=alertDialog.findViewById(R.id.spec_item_description)
     val price:TextView=alertDialog.findViewById(R.id.spec_item_price)
     val quantityValue:TextView=alertDialog.findViewById(R.id.spec_item_quantityValue)
     val addQuantity:ImageView=alertDialog.findViewById(R.id.spec_item_forward)
     val removeQuantity:ImageView=alertDialog.findViewById(R.id.spec_item_backward)
     val buy:Button=alertDialog.findViewById(R.id.spec_item_buy_btn)
     val iconImage:ImageView=alertDialog.findViewById(R.id.spec_item_image)
     var quantity:Float=1F
     addQuantity.setOnClickListener {
         if(quantity==0F)
             quantity=1F

         ++quantity
         quantityValue.text = quantity.toString()
     }
     removeQuantity.setOnClickListener {
         if(quantity>=1)
             quantityValue.text = quantity--.toString()
     }
     Glide.with(context).asBitmap().load(mutableList[dataList.position].image).into(iconImage)
     title.text="Tittle:"+mutableList[dataList.position].title
     decs.text="Description:".plus(mutableList[dataList.position].description)
     price.text="Price:$".plus(mutableList[dataList.position].price.format("%.2d"))
     buy.setOnClickListener {
         val totalAmount="Total Amount:".plus((mutableList[dataList.position].price.toFloat()*quantity).toString().plus("$"))
         val data=Gson().toJson(CartItemAddedModel(dataList.position,mutableList[dataList.position].image,mutableList[dataList.position].id,"quantity:".plus(quantity.toString()),mutableList[dataList.position].title,totalAmount,mutableList[dataList.position].category))
         val intent= Intent(context, HomeActivity::class.java)

         intent.putExtra("toCart",data)
         context.startActivity(intent)
         alertDialog.dismiss()
     }
     context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE).edit().putBoolean("isDisplayCalled",false).apply()
     alertDialog.show()

 }
           holder.itemView.setOnClickListener{
            alertDialog.setContentView(R.layout.view_specific_item)
            val title:TextView=alertDialog.findViewById(R.id.spec_item_tittle)
            val decs:TextView=alertDialog.findViewById(R.id.spec_item_description)
            val price:TextView=alertDialog.findViewById(R.id.spec_item_price)
            val quantityValue:TextView=alertDialog.findViewById(R.id.spec_item_quantityValue)
            val addQuantity:ImageView=alertDialog.findViewById(R.id.spec_item_forward)
            val removeQuantity:ImageView=alertDialog.findViewById(R.id.spec_item_backward)
            val buy:Button=alertDialog.findViewById(R.id.spec_item_buy_btn)
               val iconImage:ImageView=alertDialog.findViewById(R.id.spec_item_image)
            var quantity:Float=1F
             addQuantity.setOnClickListener {
               if(quantity==0F)
                   quantity=1F

                 ++quantity
                 quantityValue.text = quantity.toString()
             }
            removeQuantity.setOnClickListener {
                if(quantity>=1)
                quantityValue.text = quantity--.toString()
            }
               Glide.with(context).asBitmap().load(mutableList[position].image).into(iconImage)
            title.text="Tittle:"+mutableList[position].title
            decs.text="Description:"+mutableList[position].description
            price.text="Price:"+mutableList[position].price+"$"
            buy.setOnClickListener {
                val totalAmount="Total Amount:".plus((mutableList[position].price.toFloat()*quantity).toString().plus("$"))
                val data=Gson().toJson(CartItemAddedModel(position,mutableList[position].image,mutableList[position].id,"quantity:".plus(quantity.toString()),mutableList[position].title,totalAmount,mutableList[position].category))
                val intent= Intent(context, HomeActivity::class.java)

                intent.putExtra("toCart",data)
                context.startActivity(intent)
                alertDialog.dismiss()
            }

            alertDialog.show()
        }
    }

    override fun getItemCount(): Int {
       return mutableList.size
    }


}