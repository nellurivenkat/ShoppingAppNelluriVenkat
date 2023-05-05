package com.ecomerce.shoponline.app.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.adapter.CustomViewPagerAdapter
import com.ecomerce.shoponline.app.models.CartItemAddedModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*

class HomeActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var viewPager2: ViewPager2
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var dataList: MutableList<CartItemAddedModel>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        if (getSharedPreferences(packageName, MODE_PRIVATE).getString("userStoreName", "")
                ?.isNotEmpty()?.equals(true) == true
        ) {
            if (getSharedPreferences(packageName, MODE_PRIVATE).getString(
                    "userAccName",
                    ""
                ) != getSharedPreferences(packageName, MODE_PRIVATE).getString("userStoreName", "")
            ) {
                getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                    .putString("dataCart", "").apply()
                getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                    .putString("orderItems", "").apply()

            }
        }

        init()
    }

    //function to initialize views
    private fun init() {
        bottomNavigationView = findViewById(R.id.home_bottom_nav)
        viewPager2 = findViewById(R.id.home_viewpager)
        performLogic()
    }

    //function to perform logic
    private fun performLogic() {
        sharedPreferences = getSharedPreferences(packageName, MODE_PRIVATE)
        dataList = mutableListOf()
        val customViewPagerAdapter = CustomViewPagerAdapter(this@HomeActivity)
        viewPager2.adapter = customViewPagerAdapter
        viewPager2.isUserInputEnabled = false
        bottomNavigationView.setOnItemSelectedListener(this@HomeActivity)

        if (sharedPreferences.getBoolean("ordered", false)) {
            bottomNavigationView.selectedItemId = R.id.nav_bar_order_icon
            viewPager2.setCurrentItem(2, false)
            sharedPreferences.edit().putBoolean("ordered", false).apply()
        }
        val data = intent.getStringExtra("toCart")

        if (data?.isNotEmpty() == true) {

            if (sharedPreferences.getString("dataCart", "")?.isNotEmpty() == true) {

                val type = object : TypeToken<MutableList<CartItemAddedModel>>() {}.type
                dataList = Gson().fromJson(
                    sharedPreferences.getString("dataCart", ""),
                    type
                ) as MutableList<CartItemAddedModel>
                dataList.add(Gson().fromJson(data, CartItemAddedModel::class.java))
                val storeData = Gson().toJson(dataList)
                Log.d("FragCart", storeData!!)
                sharedPreferences.edit().putString("dataCart", storeData).apply()


                var totalAmount = 0F
                for (mData in dataList) {
                    totalAmount += (mData.amount.replace(Regex("[^0-9 .]"), "").toFloat())
                    Log.d("FragCart", totalAmount.toString())
                }
                sharedPreferences.edit()
                    .putString("totalAmount", totalAmount.toString().plus("$").format("%.2f"))
                    .apply()


            } else {
                dataList.add(Gson().fromJson(data, CartItemAddedModel::class.java))
                val storeData = Gson().toJson(dataList)
                sharedPreferences.edit().putString("dataCart", storeData).apply()
                var totalAmount = 0F
                for (mData in dataList) {
                    totalAmount += (mData.amount.replace(Regex("[^0-9 .]"), "").toFloat())
                }
                sharedPreferences.edit().putString(
                    "totalAmount", totalAmount.toString().format(
                        Locale.UK, ".%2f"
                    ).plus("$")
                ).apply()
                Log.d("FragCart", totalAmount.toString())

                //  Log.d("FragCart",totalAmount.toString())

            }


            // Log.d("FragCart",storeData!!)


            //   Log.d("Act",dataList.size.toString())
            bottomNavigationView.selectedItemId = R.id.nav_bar_cart_icon
            viewPager2.setCurrentItem(1, false)

        }
    }

    //a listener to listen to item selected on the bottom bar
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.nav_bar_shop_icon -> {
                supportActionBar?.title = "Categories"
                supportActionBar?.subtitle = ""
                viewPager2.setCurrentItem(0, false)

                true
            }
            R.id.nav_bar_cart_icon -> {
                supportActionBar?.title = "Cart"
                supportActionBar?.subtitle =
                    "Total Amount:".plus(sharedPreferences.getString("totalAmount", "$0.0"))
                viewPager2.setCurrentItem(1, false)

                true
            }
            R.id.nav_bar_order_icon -> {
                supportActionBar?.title = "Orders"
                supportActionBar?.subtitle = ""
                viewPager2.setCurrentItem(2, false)
                true
            }
            R.id.nav_bar_profile_icon -> {
                supportActionBar?.title = "Profile"
                supportActionBar?.subtitle = ""
                viewPager2.setCurrentItem(3, false)
                true
            }
            else -> {
                false
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = MenuInflater(this@HomeActivity)
        inflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                getSharedPreferences(packageName, MODE_PRIVATE).edit().putString("userToken", "")
                    .apply()
                getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                    .putString("userStoreName", sharedPreferences.getString("userAccName", ""))
                    .apply()
                getSharedPreferences(packageName, Context.MODE_PRIVATE).edit()
                    .putString("userAccName", "").apply()
                startActivity(Intent(this@HomeActivity, SignInActivity::class.java))
                finish()
                true
            }

            else -> {
                false
            }
        }
        //return super.onOptionsItemSelected(item)
    }
}