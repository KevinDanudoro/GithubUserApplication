package com.bangkit.githubuserapplication.presentation.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapplication.MyApplication
import com.bangkit.githubuserapplication.R
import com.bangkit.githubuserapplication.core.adapter.FollowAdapter
import com.bangkit.githubuserapplication.core.helper.ViewModelFactory
import com.bangkit.githubuserapplication.data.Resource
import com.bangkit.githubuserapplication.databinding.FragmentFollowBinding
import com.bangkit.githubuserapplication.domain.model.GithubUser
import javax.inject.Inject

class FollowFragment : Fragment() {
    private lateinit var _binding: FragmentFollowBinding
    private val binding get() = _binding

    @Inject
    lateinit var factory: ViewModelFactory
    private val detailViewModel: DetailViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as MyApplication).appComponent.inject(this)
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
        val username = arguments?.getString(USERNAME_EXTRA)

        if(tabSection == FOLLOWER_PAGE){
            subscribeFollower(username)
        } else if(tabSection == FOLLOWING_PAGE){
            subscribeFollowing(username)
        }
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(activity)
        binding.rvFollow.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(activity, layoutManager.orientation)
        binding.rvFollow.addItemDecoration(itemDecoration)
    }

    private fun subscribeFollower(username: String?) {
        if(username == null) return
        Log.d("DebugFollowFragment", "follower")

        detailViewModel.githubUserFollowers(username).observe(viewLifecycleOwner){followers ->
            if(followers != null){
                when(followers){
                    is Resource.Error -> {
                        Log.d("DebugFollowFragment", "error")
                        setShowErrorMessage(true)
                        setShowProgressbar(false)
                    }
                    is Resource.Loading -> {
                        Log.d("DebugFollowFragment", "loading")
                        setShowErrorMessage(false)
                        setShowProgressbar(true)
                    }
                    is Resource.Success -> {
                        Log.d("DebugFollowFragment", followers.data.toString())
                        setShowErrorMessage(false)
                        setShowProgressbar(false)
                        setFollowData(followers.data)
                    }
                }
            }
        }
    }

    private fun subscribeFollowing(username: String?) {
        if(username == null) return
        Log.d("DebugFollowFragment", "following")

        detailViewModel.githubUserFollowing(username).observe(viewLifecycleOwner){followers ->
            if(followers != null){
                when(followers){
                    is Resource.Error -> {
                        setShowErrorMessage(true)
                        setShowProgressbar(false)
                    }
                    is Resource.Loading -> {
                        setShowErrorMessage(false)
                        setShowProgressbar(true)
                    }
                    is Resource.Success -> {
                        setShowErrorMessage(false)
                        setShowProgressbar(false)
                        setFollowData(followers.data)
                    }
                }
            }
        }
    }

    private fun setShowProgressbar(loading: Boolean?) {
        binding.progressBarFollow.visibility = if(loading == true) View.VISIBLE else View.GONE
    }

    private fun setShowErrorMessage(error: Boolean) {
        val visible = if(error) View.VISIBLE else View.GONE
        binding.tvFollowError.text = resources.getString(R.string.search_error)
        binding.imgFollowError.visibility = visible
        binding.tvFollowError.visibility = visible
    }

    private fun setFollowData(follow: List<GithubUser>?) {
        if(follow != null){
            val adapter = FollowAdapter(follow)
            Log.d("DebugFollowFragment", "adapter follow")
            binding.rvFollow.adapter = adapter
        }
    }

    companion object{
        const val SECTION_NUMBER = "section_number"
        const val USERNAME_EXTRA = "username_extra"
        const val FOLLOWER_PAGE = 1
        const val FOLLOWING_PAGE = 2
    }
}