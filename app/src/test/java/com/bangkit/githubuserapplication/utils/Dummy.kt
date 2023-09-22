package com.bangkit.githubuserapplication.utils

import com.bangkit.core.domain.model.GithubUser

object Dummy {
    fun generateDummyGithubUsers(): List<GithubUser> {
        val newsList = ArrayList<GithubUser>()
        for (i in 0..10) {
            val news = GithubUser(
                "Kevin $i",
                "Kevin Danudoro",
                "https://avatar_url.com",
                i,
                i,
            )
            newsList.add(news)
        }
        return newsList
    }
}