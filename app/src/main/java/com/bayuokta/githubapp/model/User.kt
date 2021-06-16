package com.bayuokta.githubapp.model

import com.google.gson.annotations.SerializedName

data class User (
    @field:SerializedName("items")
    val items: List<ItemsItem?>? = null
)
data class ItemsItem(
    @field:SerializedName("login")
    val login: String? = null,
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null

)