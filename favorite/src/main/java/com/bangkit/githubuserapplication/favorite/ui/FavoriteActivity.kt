package com.bangkit.githubuserapplication.favorite.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.core.di.module.CoreComponent
import com.bangkit.core.di.module.DaggerCoreComponent
import com.bangkit.core.domain.model.GithubUser
import com.bangkit.core.ui.FavoriteUserAdapter
import com.bangkit.githubuserapplication.databinding.ActivityFavoriteBinding
import com.bangkit.githubuserapplication.favorite.di.DaggerFavoriteComponent
import com.bangkit.githubuserapplication.presentation.detail.DetailActivity
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding

    @Inject
    lateinit var factory: ViewModelFactory
    private val favoriteViewModel: FavoriteViewModel by viewModels{factory}

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .appDependencies(
                DaggerCoreComponent.factory().create(applicationContext)
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Favorite User"

        initRecyclerView()
    }

    override fun onResume() {
        super.onResume()
        subscribe()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
    }

    private fun subscribe() {
        favoriteViewModel.getAllFavoriteUser().observe(this) { favoriteUserList ->
            Log.d("DebugFav", favoriteUserList.size.toString())
            setListFavoriteUser(favoriteUserList)
            if (favoriteUserList.isNotEmpty()) {
                with(binding) {
                    imgFavoriteEmpty.visibility = View.GONE
                    tvFavoriteEmpty.visibility = View.GONE
                }
            } else {
                with(binding) {
                    imgFavoriteEmpty.visibility = View.VISIBLE
                    tvFavoriteEmpty.visibility = View.VISIBLE
                }
            }
        }
    }

    private fun setListFavoriteUser(favoriteUser: List<GithubUser>?) {
        if(favoriteUser == null) return
        val adapter = FavoriteUserAdapter(favoriteUser)
        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                moveToDetailActivity(data)
            }
        })
        binding.rvFavorite.adapter = adapter
    }

    private fun moveToDetailActivity(data: GithubUser) {
        val toDetailActivityIntent = Intent(this, DetailActivity::class.java)
        toDetailActivityIntent.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
        startActivity(toDetailActivityIntent)
    }
}