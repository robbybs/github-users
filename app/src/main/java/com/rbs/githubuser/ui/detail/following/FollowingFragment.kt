package com.rbs.githubuser.ui.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rbs.githubuser.databinding.FragmentFollowingBinding

class FollowingFragment : Fragment() {

    private lateinit var binding: FragmentFollowingBinding
    private lateinit var username: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        username = arguments?.getString(USERNAME).toString()
        return binding.root
    }

    companion object {
        const val USERNAME = "username"

        fun newInstance(username: String): FollowingFragment {
            return FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }
        }
    }
}