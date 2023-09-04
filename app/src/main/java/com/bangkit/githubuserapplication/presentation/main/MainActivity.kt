package com.bangkit.githubuserapplication.presentation.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
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
import com.bangkit.githubuserapplication.MyApplication
import com.bangkit.githubuserapplication.R
import com.bangkit.githubuserapplication.core.adapter.GithubUserAdapter
import com.bangkit.githubuserapplication.databinding.ActivityMainBinding
import com.bangkit.githubuserapplication.data.source.local.datastore.SettingPreferences
import com.bangkit.githubuserapplication.core.helper.ViewModelFactory
import com.bangkit.githubuserapplication.data.Resource
import com.bangkit.githubuserapplication.domain.model.GithubUser
import com.bangkit.githubuserapplication.presentation.detail.DetailActivity
import com.bangkit.githubuserapplication.presentation.favorite.FavoriteActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var toolbarMenu: Menu? = null

    @Inject
    lateinit var factory: ViewModelFactory
    private val mainViewModel: MainViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as MyApplication).appComponent.inject(this)
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
                    mainViewModel.getAllGithubUser(query)
                    Log.d("DebugMain", "pass viewmodel")
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
        mainViewModel.isDarkMode.observe(this) { isDarkModeActive ->
            if (isDarkModeActive != null) {
                handleThemeChanges(isDarkModeActive)
            }
        }

        Log.d("DebugMain", "pass get")
        mainViewModel.githubUser.observe(this@MainActivity){githubUser ->
            Log.d("DebugMain", "obs $githubUser")
            if(githubUser != null){
                when(githubUser){
                    is Resource.Error -> {
                        setShowErrorMessage(true)
                        setShowProgressbar(false)
                    }
                    is Resource.Loading -> {
                        setShowErrorMessage(false)
                        setShowProgressbar(true)
                    }
                    is Resource.Success -> {
                        setShowProgressbar(false)
                        setShowErrorMessage(githubUser.data?.size ?: 0)
                        setListGithubUser(githubUser.data)
                    }
                }
            }
        }
    }

    private fun setListGithubUser(githubUsers: List<GithubUser>?) {
        if(githubUsers == null) return
        val adapter = GithubUserAdapter(githubUsers as ArrayList<GithubUser>)
        adapter.setOnItemClickCallback(object : GithubUserAdapter.OnItemClickCallback {
            override fun onItemClicked(data: GithubUser) {
                moveToDetailActivity(data)
            }
        })
        binding.rvUser.adapter = adapter
    }

    private fun moveToDetailActivity(data: GithubUser) {
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