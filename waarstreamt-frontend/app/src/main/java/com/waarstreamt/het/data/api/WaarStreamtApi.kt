package com.waarstreamt.het.data.api

import com.waarstreamt.het.data.model.ContentDetail
import com.waarstreamt.het.data.model.SearchResult
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WaarStreamtApi {

    @GET("search")
    suspend fun search(@Query("q") query: String): List<SearchResult>

    @GET("content/{id}")
    suspend fun getDetail(@Path("id") id: String): ContentDetail
}
