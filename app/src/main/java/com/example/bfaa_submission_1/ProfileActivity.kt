package com.example.bfaa_submission_1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.bfaa_submission_1.databinding.ActivityProfileBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private lateinit var profileViewModel: ProfileViewModel

    companion object{
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
        const val EXTRA_USER = "extra_user"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        profileViewModel = ProfileViewModel()
        profileViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(ProfileViewModel::class.java)
        val userData = intent.getParcelableExtra<User>(EXTRA_USER) as User



        profileViewModel.setDetailUser(userData.name!!)
        profileViewModel.getDetailUser().observe(this,{
            binding.apply {
                Glide.with(this@ProfileActivity)
                    .load(it.photo)
                    .into(imgProfile)

                tvNameProfile.text = it.name
                tvEmailProfile.text = it.html
                tvRepos.text = it.repository
                tvFollower.text = it.followers_num
                tvFollowing.text = it.following_num
            }
            showLoading(false)
        })

        tabLayout(param = String())
        userData.name?.let {
            tabLayout(it)
        }

        supportActionBar!!.title = "Profile"
    }

    private fun tabLayout(param: String) {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        sectionPagerAdapter.username = param

        val viewPager2: ViewPager2 = binding.viewPager
        viewPager2.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tab
        TabLayoutMediator(tabs, viewPager2) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}