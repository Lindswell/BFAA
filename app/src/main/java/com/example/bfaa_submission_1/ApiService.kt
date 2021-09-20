package com.example.bfaa_submission_1

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getSearch(@Query("q") query: String): Call<UserResponse>
}