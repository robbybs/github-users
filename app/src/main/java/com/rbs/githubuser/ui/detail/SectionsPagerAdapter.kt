package com.rbs.githubuser.ui.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rbs.githubuser.ui.detail.followers.FollowersFragment
import com.rbs.githubuser.ui.detail.following.FollowingFragment

class SectionsPagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> FollowersFragment.newInstance(username)
        1 -> FollowingFragment.newInstance(username)
        else -> Fragment()
    }

    override fun getItemCount(): Int = 2
}