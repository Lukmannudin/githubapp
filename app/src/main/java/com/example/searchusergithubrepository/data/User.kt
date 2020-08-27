package com.example.searchusergithubrepository.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(

    val gistsUrl: String,

    val reposUrl: String,

    val followingUrl: String,

    val starredUrl: String,

    val login: String,

    val followersUrl: String,

    val type: String,

    val url: String,

    val subscriptionsUrl: String,

    val score: Double,

    val receivedEventsUrl: String,

    val avatarUrl: String,

    val eventsUrl: String,

    val htmlUrl: String,

    val siteAdmin: Boolean,

    val id: Int,

    val gravatarId: String,

    val nodeId: String,

    val organizationsUrl: String,

    val following: Int,

    val followers: Int

) : Parcelable