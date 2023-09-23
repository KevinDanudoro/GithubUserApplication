package com.bangkit.core.utils

import com.bangkit.core.data.source.local.entity.FollowerEntity
import com.bangkit.core.data.source.local.entity.FollowingEntity
import com.bangkit.core.data.source.local.entity.GithubUserDetailEntity
import com.bangkit.core.data.source.local.entity.GithubUserEntity
import com.bangkit.core.data.source.remote.response.DetailGithubUserResponse
import com.bangkit.core.data.source.remote.response.GithubUserResponse
import com.bangkit.core.domain.model.GithubUser

object DataMapper {
    fun mapGithubResponseToEntities(input: List<GithubUserResponse>): List<GithubUserEntity> {
        val githubUserList = ArrayList<GithubUserEntity>()
        input.map {
            val user = GithubUserEntity(
                name = null,
                username = it.username,
                avatarUrl = it.avatarUrl,
            )
            githubUserList.add(user)
        }
        return githubUserList
    }

    fun mapGithubDetailResponseToEntities(input: DetailGithubUserResponse): GithubUserDetailEntity =
        GithubUserDetailEntity(
            name = input.name,
            username = input.username,
            avatarUrl = input.avatarUrl,
            following = input.following,
            followers = input.followers,
        )

    fun mapResponsesToFollowerEntities(input: List<GithubUserResponse>): List<FollowerEntity> =
        input.map {
            FollowerEntity(
                username = it.username,
                avatarUrl = it.avatarUrl
            )
        }

    fun mapResponsesToFollowingEntities(input: List<GithubUserResponse>): List<FollowingEntity> =
        input.map {
            FollowingEntity(
                username = it.username,
                avatarUrl = it.avatarUrl
            )
        }

    fun mapGithubEntitiesToDomain(input: List<GithubUserEntity>): List<GithubUser> =
        input.map {
            GithubUser(
                name = it.name,
                username = it.username,
                avatarUrl = it.avatarUrl,
            )
        }

    fun mapGithubDetailEntitiesToDomain(input: GithubUserDetailEntity?): GithubUser {
        return if (input != null)
            GithubUser(
                name = input.name,
                username = input.username,
                avatarUrl = input.avatarUrl,
                following = input.following,
                followers = input.followers,
                isFavorite = input.isFavorite,
            )
        else GithubUser(null, "", "")
    }


    fun mapGithubDetailEntitiesToDomain(input: List<GithubUserDetailEntity>): List<GithubUser> =
        input.map {
            GithubUser(
                name = it.name,
                username = it.username,
                avatarUrl = it.avatarUrl,
                following = it.following,
                followers = it.followers,
                isFavorite = it.isFavorite,
            )
        }

    fun mapFollowerEntitiesToDomain(input: List<FollowerEntity>): List<GithubUser> =
        input.map {
            GithubUser(
                name = null,
                username = it.username,
                avatarUrl = it.avatarUrl,
                following = null,
                followers = null,
            )
        }

    fun mapFollowingEntitiesToDomain(input: List<FollowingEntity>): List<GithubUser> =
        input.map {
            GithubUser(
                name = null,
                username = it.username,
                avatarUrl = it.avatarUrl,
                following = null,
                followers = null,
            )
        }

    fun mapDomainToGithubDetailEntity(input: GithubUser) =
        GithubUserDetailEntity(
            name = input.name,
            username = input.username,
            avatarUrl = input.avatarUrl,
            following = input.following,
            followers = input.followers,
            isFavorite = input.isFavorite
        )
}