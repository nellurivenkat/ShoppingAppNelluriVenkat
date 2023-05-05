package com.ecomerce.shoponline.app.fragment

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.drawable.Icon
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.activities.HomeActivity
import com.ecomerce.shoponline.app.adapter.CartAdapter
import com.ecomerce.shoponline.app.models.CartItemAddedModel
import com.ecomerce.shoponline.app.shop_interface.CartInterface
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CartFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CartFragment : Fragment(),CartInterface {
    private lateinit var recyclerView: RecyclerView
    private lateinit var  adapter:CartAdapter
    private   lateinit var floatingActionButton:FloatingActionButton
    private lateinit var  sharedPreferences: SharedPreferences


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_cart, container, false)
        sharedPreferences=requireActivity().getSharedPreferences(requireActivity().packageName, Context.MODE_PRIVATE)
        recyclerView=view.findViewById(R.id.cart_frag_rex)
        floatingActionButton=view.findViewById(R.id.floatingBarOrder)
        floatingActionButton.setImageResource(R.drawable.ic_baseline_local_printshop_24)
        recyclerView.layoutManager=LinearLayoutManager(requireContext())
        val type=object: TypeToken<MutableList<CartItemAddedModel>>(){}.type
        if(sharedPreferences.getString("dataCart","")?.isNotEmpty() == true){
            val dataList=Gson().fromJson(sharedPreferences.getString("dataCart",""),type) as MutableList<CartItemAddedModel>
            val adapter= CartAdapter(requireContext(),dataList)
            recyclerView.adapter=adapter
            floatingActionButton.setOnClickListener {

                val alertDialog=AlertDialog.Builder(requireContext())
                alertDialog.setTitle("Order Product")
                alertDialog.setIcon(R.drawable.ic_baseline_shopping_cart_24)
                alertDialog.setMessage("you are about to purchase the products in the cart and the total amount is:${ sharedPreferences.getString("totalAmount","0.0$")}")
                alertDialog.setNeutralButton("cancel", DialogInterface.OnClickListener { dialogInterface, i ->
                    run {
                        dialogInterface.cancel()
                    }
                })

                alertDialog.setPositiveButton("order", DialogInterface.OnClickListener { dialogInterface, i ->
                    run {

                        if(sharedPreferences.getString("orderItems","")?.isNotEmpty() == true){

                            val type1=object:TypeToken<MutableList<CartItemAddedModel>>(){}.type
                            val dataList1=Gson().fromJson(sharedPreferences.getString("orderItems",""),type1) as MutableList<CartItemAddedModel>
                            Log.d("FragCartAllLoad!List!",sharedPreferences.getString("orderItems","")!!)
                           val list2=Gson().fromJson(sharedPreferences.getString("dataCart",""),type1 )as MutableList<CartItemAddedModel>
                            Log.d("FragCartAllLoad!List2",sharedPreferences.getString("dataCart","")!!)
                            dataList1.addAll(list2)
                            val storeData=Gson().toJson(dataList1)


                            sharedPreferences.edit().putString("orderItems",storeData).apply()
                            Log.d("FragCartAllLoad!",storeData!!)

                        }else {
                            sharedPreferences.edit().putString("orderItems", sharedPreferences.getString("dataCart","")).apply()
                            Log.d("FragCartAllLoad22",sharedPreferences.getString("orderItems","")!!)
                        }

                        showNotification()
                        sharedPreferences.edit().putString("totalAmount","0.0$").apply()
                        ( requireActivity() as HomeActivity).supportActionBar?.subtitle="Total Amount:".plus(sharedPreferences.getString("totalAmount","$0.0"))
                        sharedPreferences.edit().putString("dataCart","").apply()
                        sharedPreferences.edit().putBoolean("ordered",true).apply()
                        dataList.clear()
                        adapter.notifyDataSetChanged()


                       // sharedPreferences.edit().putString("orderItem",sharedPreferences.getString("data","")).apply()


                    }
                })

                alertDialog.create().show()
            }

        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CartFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CartFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
        private  var dataFrag:String=""
      fun getData(data:String){
         dataFrag=data
      }


    }

    override fun addCart(cartItemAddedModel: CartItemAddedModel) {
        Log.d("FragCart",cartItemAddedModel.productQuantity)
        val dataList= mutableListOf<CartItemAddedModel>()
        dataList.add(cartItemAddedModel)
        val storeData=Gson().toJson(dataList)
        Log.d("FragCart",storeData!!+dataList.size.toString())

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun  showNotification(){
        val notificationManager=requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel=NotificationChannel(requireActivity().packageName,"MyChannel",NotificationManager.IMPORTANCE_HIGH)
        val notificationBuilder=NotificationCompat.Builder(requireContext(),requireActivity().packageName)
        val intent= Intent(requireActivity(),HomeActivity::class.java)
   val pendingIntent=PendingIntent.getActivity(requireContext(),0,intent,0)
        notificationManager.createNotificationChannel(notificationChannel)
        notificationBuilder.setSmallIcon(R.drawable.ic_baseline_shopping_cart_24)
        notificationBuilder.setContentTitle("Order Notification")
        notificationBuilder.setContentText("Successfully ordered product total amount ${ sharedPreferences.getString("totalAmount","0.0$")}")
        notificationBuilder.setContentIntent(pendingIntent)
        notificationManager.notify(0,notificationBuilder.build())
    }

}