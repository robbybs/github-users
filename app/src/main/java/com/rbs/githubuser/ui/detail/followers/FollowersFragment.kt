package com.rbs.githubuser.ui.detail.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbs.githubuser.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    private lateinit var binding: FragmentFollowersBinding
    private lateinit var username: String
    private lateinit var adapter: FollowersAdapter
    private val followersViewModel by viewModels<FollowersViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        username = arguments?.getString(USERNAME).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializationAdapter()
        followersViewModel.getFollowers(username)
        followersViewModel.getData().observe(viewLifecycleOwner) {
            if (it != null) {
                adapter.setListUsers(it)
            }
        }
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        adapter = FollowersAdapter()
        binding.rvUsers.adapter = adapter
    }

    companion object {
        const val USERNAME = "username"

        fun newInstance(username: String): FollowersFragment {
            return FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }
        }
    }
}