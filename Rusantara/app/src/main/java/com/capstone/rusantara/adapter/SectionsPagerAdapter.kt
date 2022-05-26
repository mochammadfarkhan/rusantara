package com.capstone.rusantara.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.capstone.rusantara.activity.detail.fragment.InfoFragment
import com.capstone.rusantara.activity.detail.fragment.IngredientsFragment
import com.capstone.rusantara.activity.detail.fragment.NutrientFragment

class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 3
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null

        when (position) {
            0 -> fragment = InfoFragment()
            1 -> fragment = IngredientsFragment()
            2 -> fragment = NutrientFragment()
        }

        return fragment as Fragment
    }
}