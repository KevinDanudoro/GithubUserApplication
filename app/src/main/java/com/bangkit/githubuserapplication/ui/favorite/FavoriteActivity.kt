package com.bangkit.githubuserapplication.ui.favorite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapplication.core.adapter.FavoriteUserAdapter
import com.bangkit.githubuserapplication.core.database.FavoriteUser
import com.bangkit.githubuserapplication.databinding.ActivityFavoriteBinding
import com.bangkit.githubuserapplication.model.FavoriteViewModel
import com.bangkit.githubuserapplication.ui.detail.DetailActivity

class FavoriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Favorite User"

        initRecyclerView()
        subscribe()
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvFavorite.layoutManager = layoutManager
    }

    private fun subscribe() {
        favoriteViewModel.getAllFavoriteUser().observe(this) { favoriteUserList ->
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

    private fun setListFavoriteUser(favoriteUser: List<FavoriteUser>) {
        val adapter = FavoriteUserAdapter(favoriteUser)
        adapter.setOnItemClickCallback(object : FavoriteUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: FavoriteUser) {
                moveToDetailActivity(data)
            }
        })
        binding.rvFavorite.adapter = adapter
    }

    private fun moveToDetailActivity(data: FavoriteUser) {
        val toDetailActivityIntent = Intent(this, DetailActivity::class.java)
        toDetailActivityIntent.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
        startActivity(toDetailActivityIntent)
    }
}