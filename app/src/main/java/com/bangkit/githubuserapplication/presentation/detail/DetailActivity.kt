package com.bangkit.githubuserapplication.presentation.detail

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
import com.bangkit.core.data.Resource
import com.bangkit.core.domain.model.GithubUser
import com.bangkit.core.ui.ViewModelFactory
import com.bangkit.githubuserapplication.MyApplication
import com.bangkit.githubuserapplication.R
import com.bangkit.githubuserapplication.databinding.ActivityDetailBinding
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import javax.inject.Inject

class DetailActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val detailViewModel by viewModels<DetailViewModel> { factory }

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Detail User"

        getUsernameExtra()
    }

    private fun getUsernameExtra() {
        val usernameExtra = intent.extras?.getString(EXTRA_USERNAME) ?: return
        subscribe(usernameExtra)
        initTabLayout(usernameExtra)
    }

    private fun initTabLayout(username: String) {
        val sectionPagerAdapter =
            SectionPagerAdapter(
                this,
                username
            )
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter

        val tabs: TabLayout = binding.tabLayout
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    private fun subscribe(username: String) {
        detailViewModel.getDetailGithubUser(username).observe(this) { githubUser ->
            if (githubUser != null) {
                when (githubUser) {
                    is Resource.Error -> {
                        setShowProgressbar(false)
                        setShowErrorMessage(true)
                        binding.fabLike.isActivated = false
                    }
                    is Resource.Loading -> {
                        setShowProgressbar(true)
                        setShowErrorMessage(false)
                        binding.fabLike.isActivated = false
                    }
                    is Resource.Success -> {
                        setShowProgressbar(false)
                        setShowErrorMessage(githubUser.data == null)
                        setFabLikeIcon(githubUser.data?.isFavorite == 1)
                        setProfileData(githubUser.data)
                        setFabOnClickListener(githubUser.data)
                        binding.fabLike.isActivated = true
                    }
                }
            }
        }
    }

    private fun setFabOnClickListener(githubUser: GithubUser?) {
        if (githubUser == null) return

        binding.fabLike.setOnClickListener {
            checkFavoriteModule(githubUser)
        }
    }

    private fun checkFavoriteModule(githubUser: GithubUser) {
        val splitInstallManager = SplitInstallManagerFactory.create(this)
        val module = "favorite"
        if (splitInstallManager.installedModules.contains(module)) {
            setFavoriteGithubUser(githubUser)
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(module)
                .build()
            splitInstallManager.startInstall(request)
                .addOnSuccessListener {
                    Toast.makeText(
                        this,
                        "Success installing favorite module",
                        Toast.LENGTH_SHORT
                    ).show()
                    setFavoriteGithubUser(githubUser)
                }
                .addOnFailureListener {
                    Toast.makeText(
                        this,
                        "Error installing favorite module",
                        Toast.LENGTH_SHORT
                    ).show()
                }
        }
    }

    private fun setFavoriteGithubUser(githubUser: GithubUser) {
        val newFavorite = if(githubUser.isFavorite == 1) 0 else 1
        detailViewModel.setFavoriteGithubUser(githubUser, newFavorite)
        if (newFavorite == 1)
            Toast.makeText(
                this,
                "${githubUser.username} ditambahkan ke favorit",
                Toast.LENGTH_SHORT
            ).show()
        else
            Toast.makeText(
                this,
                "${githubUser.username} dihapus dari favorit",
                Toast.LENGTH_SHORT
            ).show()
    }

    private fun setProfileData(githubUser: GithubUser?) {
        if (githubUser != null) {
            Glide.with(this@DetailActivity)
                .load(githubUser.avatarUrl)
                .into(binding.imgDetailAvatar)

            with(binding) {
                tvDetailUsername.text = githubUser.username
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

    private fun setFabLikeIcon(show: Boolean?) {
        if(show == null) return
        if (show) {
            val fabIcon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_fill)
            fabIcon?.mutate()?.colorFilter =
                PorterDuffColorFilter(Color.RED, PorterDuff.Mode.MULTIPLY)
            binding.fabLike.setImageDrawable(fabIcon)
        } else {
            val fabIcon = ContextCompat.getDrawable(this, R.drawable.ic_favorite_border)
            binding.fabLike.setImageDrawable(fabIcon)
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}