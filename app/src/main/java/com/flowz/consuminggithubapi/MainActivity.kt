package com.flowz.consuminggithubapi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.flowz.consuminggithubapi.api.RepositoryRetriever
import com.flowz.consuminggithubapi.data.RepoListAdapter
import com.flowz.consuminggithubapi.data.RepoResult
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val repoRetriever = RepositoryRetriever()


    private val callback = object : Callback<RepoResult> {

        override fun onFailure(call: Call<RepoResult>, t: Throwable) {
            Log.e("MainActivity", "Problem calling Github API {${t?.message}}")
        }

        override fun onResponse(call: Call<RepoResult>, response: Response<RepoResult>) {

            response?.isSuccessful.let {

                val resultList = RepoResult(
                    response?.body()?.items ?: emptyList()
                )
                repoList.adapter = RepoListAdapter(resultList)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        val networkResult = getConnectionType(this)

        repoList.layoutManager = LinearLayoutManager(this)
//        repoList.adapter = RepoListAdapter()

        if (getConnectionType(context = this)) {

//                repoRetriever.getRepositories(callback)
            retrieveRepositories()

        } else {

            AlertDialog.Builder(this).setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try aagain")
                .setPositiveButton(getString(R.string.ok)) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show()
        }
    }

    @Suppress("DEPRECATION")
    fun getConnectionType(context: Context): Boolean {
        var result = false
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm?.run {
                cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                    if (hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        result = true
                    } else if (hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        result = true
                    }
                }
            }
        } else {
            cm?.run {
                cm.activeNetworkInfo?.run {
                    if (type == ConnectivityManager.TYPE_WIFI) {
                        result = true
                    } else if (type == ConnectivityManager.TYPE_MOBILE) {
                        result = true
                    }
                }
            }
        }
        return result
    }

    fun retrieveRepositories() {

        val mainActivityJob = Job()

        val errorHandler = CoroutineExceptionHandler { _, exception ->
            AlertDialog.Builder(this).setTitle("Error")
                .setMessage(exception.message)
                .setPositiveButton(android.R.string.ok) { _, _ -> }
                .setIcon(android.R.drawable.ic_dialog_alert).show()
        }


        val coroutineScope = CoroutineScope(mainActivityJob + Dispatchers.Main)
        coroutineScope.launch(errorHandler) {

            val resultList = RepositoryRetriever().getRepositories()
//            Log.d("result", resultList.toString())

            repoList.adapter = RepoListAdapter(resultList)


        }

    }

}
