package com.ecomerce.shoponline.app.adapter

import android.app.Activity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ecomerce.shoponline.app.fragment.CartFragment
import com.ecomerce.shoponline.app.fragment.OrdersFragment
import com.ecomerce.shoponline.app.fragment.ProfileFragment
import com.ecomerce.shoponline.app.fragment.ShopFragment

class CustomViewPagerAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->{
                ShopFragment()
            }
            1->{
                CartFragment()
            }
            2->{
                OrdersFragment()
            }
            3->{
                ProfileFragment()
            }
            else-> Fragment()
        }
       }

}