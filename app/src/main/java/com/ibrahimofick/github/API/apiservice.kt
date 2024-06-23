package com.ibrahimofick.github.API

import com.ibrahimofick.github.DATA.datauser
import com.ibrahimofick.github.DATA.response
import com.ibrahimofick.github.DATA.user
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface apiservice {
    @GET("search/users")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<response>

    @GET("users/{username}")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<datauser>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<user>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<user>>
}