package com.capstone.rusantara.activity.detail

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import androidx.annotation.StringRes
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.capstone.rusantara.R
import com.capstone.rusantara.adapter.SectionsPagerAdapter
import com.capstone.rusantara.databinding.ActivityDetailBinding
import com.capstone.rusantara.models.ImageData
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupView()
        setupViewPager()
        setupData()
    }

    override fun onClick(view: View) {
        if (view.id == R.id.backIcon) {
            finish()
        }
    }

    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun setupViewPager() {
        val sectionPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs: TabLayout = binding.tabs

        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun setupData() {
        val imageData = getImageData()

        Glide.with(applicationContext)
            .load(imageData.img)
            .into(binding.imgFood)
        binding.foodName.text = imageData.name
    }

    private fun getImageData(): ImageData {
        return intent.getParcelableExtra<ImageData>(EXTRA_DATA) as ImageData
    }

    companion object {
        const val EXTRA_DATA = "extra_data"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.food_info,
            R.string.food_ingredients,
            R.string.food_nutrition
        )
    }
}