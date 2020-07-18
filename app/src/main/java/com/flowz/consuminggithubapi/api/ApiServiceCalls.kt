package com.flowz.consuminggithubapi.api

import com.flowz.consuminggithubapi.data.RepoResult
import retrofit2.http.GET

interface ApiServiceCalls {

    @GET("/repositories")
    suspend fun retrieveRepositories(): RepoResult

    @GET("/search/repositories?q=language:kotlin&sort=stars&order=desc&?per_page=50")
    //search
    suspend fun searchRepositories(): RepoResult
}