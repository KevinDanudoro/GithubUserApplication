package com.bangkit.githubuserapplication.core.utils

import com.bangkit.githubuserapplication.data.source.local.entity.FollowerEntity
import com.bangkit.githubuserapplication.data.source.local.entity.FollowingEntity
import com.bangkit.githubuserapplication.data.source.local.entity.GithubUserEntity
import com.bangkit.githubuserapplication.data.source.remote.response.DetailGithubUserResponse
import com.bangkit.githubuserapplication.data.source.remote.response.GithubUserResponse
import com.bangkit.githubuserapplication.domain.model.GithubUser

object DataMapper {
    fun mapResponsesToEntities(input: List<GithubUserResponse>): List<GithubUserEntity> {
        val githubUserList = ArrayList<GithubUserEntity>()
        input.map {
            val user = GithubUserEntity(
                name = null,
                username = it.username,
                avatarUrl = it.avatarUrl,
                following = null,
                followers = null,
                isFavorite = 0
            )
            githubUserList.add(user)
        }
        return githubUserList
    }

    fun mapResponsesToEntities(input: DetailGithubUserResponse): GithubUserEntity =
        GithubUserEntity(
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

    fun mapEntitiesToDomain(input: List<GithubUserEntity>): List<GithubUser> =
        input.map {
            GithubUser(
                name = it.name,
                username = it.username,
                avatarUrl = it.avatarUrl,
                following = it.following,
                followers = it.followers,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntitiesToDomain(input: GithubUserEntity): GithubUser =
        GithubUser(
            name = input.name,
            username = input.username,
            avatarUrl = input.avatarUrl,
            following = input.following,
            followers = input.followers,
            isFavorite = input.isFavorite
        )

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

    fun mapDomainToEntity(input: GithubUser) = GithubUserEntity(
        name = input.name,
        username = input.username,
        avatarUrl = input.avatarUrl,
        following = input.following,
        followers = input.followers,
        isFavorite = input.isFavorite
    )
}