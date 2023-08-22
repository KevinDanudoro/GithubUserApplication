package com.bangkit.githubuserapplication.ui.detail

import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.bangkit.githubuserapplication.R
import com.bangkit.githubuserapplication.core.adapter.SectionPagerAdapter
import com.bangkit.githubuserapplication.core.database.FavoriteUser
import com.bangkit.githubuserapplication.databinding.ActivityDetailBinding
import com.bangkit.githubuserapplication.core.gson.DetailGithubUserResponse
import com.bangkit.githubuserapplication.model.DetailViewModel
import com.bangkit.githubuserapplication.model.FavoriteViewModel
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    private lateinit var binding: ActivityDetailBinding
    private var favoriteUser: FavoriteUser? = null

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Detail User"

        initTabLayout()
        getUsernameExtra()
        subscribe()

        binding.fabLike.setOnClickListener {
            val isLoading = detailViewModel.detailLoading.value ?: true
            if(!isLoading){
                val user = detailViewModel.detailGithubUser.value
                val currentUser: FavoriteUser = favoriteUser ?: FavoriteUser(
                    0,
                    user?.login ?: "",
                    user?.avatarUrl ?: ""
                )
                if (favoriteUser == null) {
                    favoriteViewModel.insert(currentUser)
                    Toast.makeText(
                        this,
                        "${currentUser.username} ditambahkan ke favorit",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    favoriteViewModel.delete(currentUser)
                    Toast.makeText(
                        this,
                        "${currentUser.username} dihapus dari favorit",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun initTabLayout() {
        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun getUsernameExtra() {
        val usernameExtra = intent.extras?.getString(EXTRA_USERNAME)
        if (usernameExtra != null) {
            detailViewModel.setUsername(usernameExtra)
        }
    }

    private fun subscribe() {
        detailViewModel.username.observe(this) { usernameViewModel ->
            if (usernameViewModel != null) {
                detailViewModel.getDetailGithubUser(usernameViewModel)
            }
        }
        detailViewModel.detailGithubUser.observe(this) { githubUser ->
            setProfileData(githubUser)
        }
        detailViewModel.detailLoading.observe(this) { isLoading ->
            setShowProgressbar(isLoading)
        }
        detailViewModel.detailError.observe(this) { isError ->
            setShowErrorMessage(isError)
        }

        favoriteViewModel
            .getOneFavoriteUser(detailViewModel.username.value.toString())
            .observe(this) { oneFavoriteUser ->
                favoriteUser = if (oneFavoriteUser.isNotEmpty()) {
                    oneFavoriteUser.first()
                } else {
                    null
                }
                setFabLikeIcon(oneFavoriteUser)
            }
    }

    private fun setProfileData(githubUser: DetailGithubUserResponse?) {
        if (githubUser != null) {
            Glide.with(this@DetailActivity)
                .load(githubUser.avatarUrl)
                .into(binding.imgDetailAvatar)

            with(binding) {
                tvDetailUsername.text = githubUser.login
                tvDetailName.text = githubUser.name
                tvDetailFollower.text = githubUser.followers.toString()
                tvDetailFollowing.text = githubUser.following.toString()
            }
        }
    }

    private fun setShowErrorMessage(error: Boolean?) {
        if (error != null) {
            val errorVisibility = if (error) View.VISIBLE else View.GONE
            val visibility = if (!error) View.VISIBLE else View.GONE

            with(binding) {
                tvDisplayFollower.visibility = visibility
                tvDisplayFollowing.visibility = visibility
                imgDetailAvatar.visibility = visibility
                imgDetailError.visibility = errorVisibility
                tvDetailError.visibility = errorVisibility
            }
        }
    }

    private fun setShowProgressbar(loading: Boolean) {
        binding.progressBarDetail.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setFabLikeIcon(oneFavoriteUser: List<FavoriteUser>) {
        if (oneFavoriteUser.isNotEmpty()) {
            val fabIcon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_fill)
            fabIcon?.mutate()?.colorFilter = PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
            binding.fabLike.setImageDrawable(fabIcon)
        } else {
            val fabIcon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
            binding.fabLike.setImageDrawable(fabIcon)
        }
    }
}