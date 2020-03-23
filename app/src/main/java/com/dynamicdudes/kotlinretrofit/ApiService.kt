package com.dynamicdudes.kotlinretrofit

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("/api/quotes")
    fun fetchAllUser() : Call<List<User>>
}