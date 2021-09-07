package com.example.bfaa_submission_1

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? = null

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FragmentFollower.newInstance(username.toString())
            1 -> fragment = FragmentFollowing.newInstance(username.toString())
        }
        return fragment as Fragment
    }
    override fun getItemCount(): Int {
        return  2
    }
}