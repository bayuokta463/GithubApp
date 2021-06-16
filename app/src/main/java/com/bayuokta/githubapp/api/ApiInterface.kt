package com.bayuokta.githubapp.api

import com.bayuokta.githubapp.BuildConfig
import com.bayuokta.githubapp.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ApiInterface {
    @GET("search/users")
    @Headers("Authorization: ${BuildConfig.GitHub_API_KEY}")
    fun getUsers(@Query("q")users:String): Call<User>

}
