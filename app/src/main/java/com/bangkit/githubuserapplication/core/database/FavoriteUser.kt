package com.bangkit.githubuserapplication.core.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity("favorite_user_table")
@Parcelize
data class FavoriteUser(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo("id")
    val id: Int = 0,

    @ColumnInfo("username")
    val username: String,

    @ColumnInfo("photo_url")
    val photoUrl: String
): Parcelable