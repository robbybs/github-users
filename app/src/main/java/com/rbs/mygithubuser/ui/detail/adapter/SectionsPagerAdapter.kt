package com.rbs.mygithubuser.ui.detail.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rbs.mygithubuser.ui.detail.fragment.FollowerFragment
import com.rbs.mygithubuser.ui.detail.fragment.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FollowerFragment.newInstance(username)
        1 -> FollowingFragment.newInstance(username)
        else -> Fragment()
    }
}