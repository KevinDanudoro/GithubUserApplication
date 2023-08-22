package com.bangkit.githubuserapplication.ui.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapplication.R
import com.bangkit.githubuserapplication.core.adapter.FollowAdapter
import com.bangkit.githubuserapplication.databinding.FragmentFollowBinding
import com.bangkit.githubuserapplication.core.gson.GithubFollowResponseItem
import com.bangkit.githubuserapplication.model.DetailViewModel

class FollowFragment : Fragment() {
    private lateinit var _binding: FragmentFollowBinding
    private val binding get() = _binding

    private val detailViewModel: DetailViewModel by activityViewModels()

    companion object{
        const val SECTION_NUMBER = "section_number"
        const val FOLLOWER_PAGE = 1
        const val FOLLOWING_PAGE = 2
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()

        val tabSection = arguments?.getInt(SECTION_NUMBER, 0)
        if(tabSection == FOLLOWER_PAGE){
            subscribeFollower()
        } else if(tabSection == FOLLOWING_PAGE){
            subscribeFollowing()
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)
    }

    private fun subscribeFollower() {
        detailViewModel.username.observe(viewLifecycleOwner){username ->
            if(username != null){
                detailViewModel.getGithubUserFollowers(username)
            }
        }
        detailViewModel.followersLoading.observe(viewLifecycleOwner){isLoading ->
            setShowProgressbar(isLoading)
        }
        detailViewModel.githubUserFollowers.observe(viewLifecycleOwner){follow ->
            setFollowData(follow)
        }
        detailViewModel.followersError.observe(viewLifecycleOwner){isError ->
            setShowErrorMessage(isError)
        }
    }

    private fun subscribeFollowing() {
        detailViewModel.username.observe(viewLifecycleOwner){username ->
            if(username != null){
                detailViewModel.getGithubUserFollowing(username)
            }
        }
        detailViewModel.followingLoading.observe(viewLifecycleOwner){isLoading ->
            setShowProgressbar(isLoading)
        }
        detailViewModel.githubUserFollowing.observe(viewLifecycleOwner){follow ->
            setFollowData(follow)
        }
        detailViewModel.followingError.observe(viewLifecycleOwner){isError ->
            setShowErrorMessage(isError)
        }
    }

    private fun setShowProgressbar(loading: Boolean?) {
        binding.progressBarFollow.visibility = if(loading == true) View.VISIBLE else View.GONE
    }

    private fun setFollowData(follow: ArrayList<GithubFollowResponseItem>?) {
        if(follow != null){
            val adapter = FollowAdapter(follow)
            binding.rvFollow.adapter = adapter
        }
    }

    private fun setShowErrorMessage(error: Boolean) {
        val visible = if(error) View.VISIBLE else View.GONE
        binding.tvFollowError.text = resources.getString(R.string.search_error)
        binding.imgFollowError.visibility = visible
        binding.tvFollowError.visibility = visible
    }
}