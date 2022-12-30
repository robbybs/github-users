package com.rbs.githubuser.ui.favorite.detail.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbs.githubuser.databinding.FragmentDetailFollowersBinding
import com.rbs.githubuser.ui.detail.followers.FollowersFragment
import com.rbs.githubuser.ui.favorite.detail.DetailFavoriteViewModel

class DetailFollowersFragment : Fragment() {

    private lateinit var binding: FragmentDetailFollowersBinding
    private lateinit var adapter: DetailFollowersAdapter
    private lateinit var username: String
    private val detailFavoriteViewModel by viewModels<DetailFavoriteViewModel> {
        activity?.let { DetailFavoriteViewModel.ViewModelFactory.getInstance(it.application) }
        DetailFavoriteViewModel.ViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailFollowersBinding.inflate(inflater, container, false)
        username = arguments?.getString(FollowersFragment.USERNAME).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializationAdapter()
        detailFavoriteViewModel.getData(username).observe(viewLifecycleOwner) {
            adapter.setListUsers(it.follower.userList)
        }
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        adapter = DetailFollowersAdapter()
        binding.rvUsers.adapter = adapter
    }

    companion object {
        const val USERNAME = "username"

        fun newInstance(username: String): DetailFollowersFragment {
            return DetailFollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }
        }
    }
}