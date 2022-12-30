package com.rbs.githubuser.ui.favorite.detail.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.rbs.githubuser.databinding.FragmentDetailFollowingBinding
import com.rbs.githubuser.ui.favorite.detail.DetailFavoriteViewModel

class DetailFollowingFragment : Fragment() {

    private lateinit var binding: FragmentDetailFollowingBinding
    private lateinit var adapter: DetailFollowingAdapter
    private lateinit var username: String
    private val detailFavoriteViewModel by viewModels<DetailFavoriteViewModel> {
        DetailFavoriteViewModel.ViewModelFactory.getInstance(requireActivity().application)
        DetailFavoriteViewModel.ViewModelFactory(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailFollowingBinding.inflate(inflater, container, false)
        username = arguments?.getString(USERNAME).toString()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initializationAdapter()
        detailFavoriteViewModel.getData(username).observe(viewLifecycleOwner) {
            adapter.setListUsers(it.following.userList)
        }
    }

    private fun initializationAdapter() {
        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.layoutManager = LinearLayoutManager(context)
        adapter = DetailFollowingAdapter()
        binding.rvUsers.adapter = adapter
    }

    companion object {
        const val USERNAME = "username"

        fun newInstance(username: String): DetailFollowingFragment {
            return DetailFollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(USERNAME, username)
                }
            }
        }
    }
}