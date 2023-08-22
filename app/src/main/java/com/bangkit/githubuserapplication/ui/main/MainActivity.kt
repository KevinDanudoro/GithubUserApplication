package com.bangkit.githubuserapplication.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.bangkit.githubuserapplication.R
import com.bangkit.githubuserapplication.core.adapter.GithubUserAdapter
import com.bangkit.githubuserapplication.databinding.ActivityMainBinding
import com.bangkit.githubuserapplication.core.datastore.SettingPreferences
import com.bangkit.githubuserapplication.core.gson.GithubUserResponse
import com.bangkit.githubuserapplication.core.gson.ItemsItem
import com.bangkit.githubuserapplication.core.helper.ViewModelFactory
import com.bangkit.githubuserapplication.model.MainViewModel
import com.bangkit.githubuserapplication.ui.detail.DetailActivity
import com.bangkit.githubuserapplication.ui.favorite.FavoriteActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var toolbarMenu: Menu? = null

    private val mainViewModel: MainViewModel by viewModels {
        val pref = SettingPreferences.getInstance(dataStore)
        ViewModelFactory(pref)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()
        subscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)
        toolbarMenu = menu

        val isDarkMode = mainViewModel.isDarkMode.value
        if (isDarkMode != null) {
            if (isDarkMode)
                menu?.findItem(R.id.theme_menu)?.setIcon(R.drawable.ic_moon)
            else
                menu?.findItem(R.id.theme_menu)?.setIcon(R.drawable.ic_sun)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search_menu)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.menu_search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    mainViewModel.getGithubUserData(query)
                }
                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String?) = false
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu -> {
                val toFavoriteActivity = Intent(this, FavoriteActivity::class.java)
                startActivity(toFavoriteActivity)
            }
            R.id.theme_menu -> {
                val isDarkModeActive = mainViewModel.isDarkMode.value
                if (isDarkModeActive != null) {
                    mainViewModel.saveThemeSetting(!isDarkModeActive)
                }
            }
        }
        return true
    }

    private fun initRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
    }

    private fun subscribe() {
        mainViewModel.githubUser.observe(this) { githubUsers ->
            if (githubUsers != null) {
                setShowErrorMessage(githubUsers.totalCount)
                setListGithubUser(githubUsers)
            }
        }
        mainViewModel.isLoading.observe(this) { isLoading ->
            if (isLoading != null) {
                binding.imgError.visibility = View.GONE
                binding.tvError.visibility = View.GONE
                setShowProgressbar(isLoading)
            }
        }
        mainViewModel.isError.observe(this) { isError ->
            if (isError != null) {
                setShowErrorMessage(isError)
            }
        }
        mainViewModel.isDarkMode.observe(this) { isDarkModeActive ->
            if (isDarkModeActive != null) {
                handleThemeChanges(isDarkModeActive)
            }
        }
    }

    private fun setListGithubUser(githubUsers: GithubUserResponse) {
        val adapter = GithubUserAdapter(githubUsers.items as ArrayList<ItemsItem>)
        adapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: ItemsItem) {
                moveToDetailActivity(data)
            }
        })
        binding.rvUser.adapter = adapter
    }

    private fun moveToDetailActivity(data: ItemsItem) {
        val toDetailActivityIntent = Intent(
            this@MainActivity,
            DetailActivity::class.java
        )
        toDetailActivityIntent.putExtra(DetailActivity.EXTRA_USERNAME, data.username)
        startActivity(toDetailActivityIntent)
    }

    private fun setShowProgressbar(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    private fun setShowErrorMessage(githubUsersCount: Int) {
        val visible = if (githubUsersCount == 0) View.VISIBLE else View.GONE
        with(binding) {
            tvError.text = resources.getString(R.string.search_not_found)
            imgError.visibility = visible
            tvError.visibility = visible
        }
    }

    private fun setShowErrorMessage(error: Boolean) {
        val visible = if (error) View.VISIBLE else View.GONE
        with(binding) {
            tvError.text = resources.getString(R.string.search_error)
            imgError.visibility = visible
            tvError.visibility = visible
        }
    }

    private fun handleThemeChanges(darkModeActive: Boolean) {
        if (darkModeActive)
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}