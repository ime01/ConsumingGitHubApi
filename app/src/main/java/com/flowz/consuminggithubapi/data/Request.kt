package com.flowz.consuminggithubapi.data

class Request {

    companion object{
        private const val URL = "https://api.github.com/search/repositories"
        private const val SEARCH = "q=language:kotlin&sort=stars&order=desc&?per_page=50"
        private const val COMPLETE_URL = "$URL?$SEARCH"

    }

//    fun run (): RepoResult{
//
////        val repoListJsonStr = URI(COMPLETE_URL).readText()
////
////        return Gson().fromJson(repoListJsonStr, RepoResult::class.java)
//
//    }
}