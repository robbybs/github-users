package com.rbs.githubuser.ui.favorite.detail

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.rbs.githubuser.ui.favorite.detail.followers.DetailFollowersFragment
import com.rbs.githubuser.ui.favorite.detail.following.DetailFollowingFragment

class FavoritePagerAdapter(activity: AppCompatActivity, private val username: String) : FragmentStateAdapter(activity) {

    override fun createFragment(position: Int): Fragment = when (position) {
        0 -> DetailFollowersFragment.newInstance(username)
        1 -> DetailFollowingFragment.newInstance(username)
        else -> Fragment()
    }

    override fun getItemCount(): Int = 2
}