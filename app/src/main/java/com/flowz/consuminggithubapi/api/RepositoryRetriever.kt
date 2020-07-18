package com.flowz.consuminggithubapi.api

import com.flowz.consuminggithubapi.data.RepoResult
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RepositoryRetriever {

    private val service: ApiServiceCalls

    companion object{

        const val BASE_URL = "https://api.github.com/"
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ApiServiceCalls::class.java)
    }

   suspend  fun getRepositories(): RepoResult{

      return service.searchRepositories()
    }
}