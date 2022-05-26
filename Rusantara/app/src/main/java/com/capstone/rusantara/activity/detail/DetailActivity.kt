package com.capstone.rusantara.activity.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.capstone.rusantara.R
import com.capstone.rusantara.adapter.SectionsPagerAdapter
import com.capstone.rusantara.databinding.ActivityDetailBinding
import com.capstone.rusantara.models.ImageData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewPager()
        setupData()
    }

    private fun setupViewPager() {
        val sectionPagerAdapter = SectionsPagerAdapter(this)
//        val viewPager: ViewPager2 = binding.viewPager
//        viewPager.adapter = sectionPagerAdapter
//        val tabs: TabLayout = binding.tabs
//
//        TabLayoutMediator(tabs, viewPager) { tab, position ->
//            tab.text = resources.getString(TAB_TITLES[position])
//        }.attach()
    }

    private fun setupData() {
        val imageData = getImageData()

//        Glide.with(applicationContext)
//            .load(imageData?.photoUrl)
//            .info(binding.imgItemPhoto)
//        binding.apply {
//
//        }
    }

    private fun getImageData(): ImageData {
        return intent.getParcelableExtra<ImageData>(EXTRA_DATA) as ImageData
    }

    companion object {
        const val EXTRA_DATA = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(

        )
    }
}