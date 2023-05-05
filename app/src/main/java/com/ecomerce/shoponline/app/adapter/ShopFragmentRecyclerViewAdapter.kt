package com.ecomerce.shoponline.app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.activities.SpecificProductActivity

class ShopFragmentRecyclerViewAdapter(val context: Context, private val mutableList: MutableList<String>): RecyclerView.Adapter<ShopFragmentRecyclerViewAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val title: TextView =itemView.findViewById(R.id.rec_shop_text)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
     val view=LayoutInflater.from(context).inflate(R.layout.shop_frag_rec_categories,parent,false)

        return  MyViewHolder(view)

    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.title.text = mutableList[position]
        holder.itemView.setOnClickListener{

        val intent =Intent(context,SpecificProductActivity::class.java)
            intent.putExtra("category",mutableList[position])
            context.getSharedPreferences(context.packageName,Context.MODE_PRIVATE).edit().putString("category",mutableList[position]).apply()
            context.startActivity(intent)





        }

    }

    override fun getItemCount(): Int {
        return  mutableList.size
    }

}