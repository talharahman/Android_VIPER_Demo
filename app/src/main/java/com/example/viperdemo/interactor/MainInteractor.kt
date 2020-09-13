package com.example.viperdemo.interactor

import android.content.Context
import com.example.viperdemo.R
import com.example.viperdemo.contracts.MainContract
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class MainInteractor : MainContract.Interactor {

    private val interactorJob = Job()
    private val coroutineScope = CoroutineScope(interactorJob + Dispatchers.Main)

    override fun loadArticles(context: Context, output: MainContract.InteractorOutput) {
        coroutineScope.launch {

            val getArticlesDeferred =
                NewsApi.retrofitService.getArticlesAsync("us",
                    context.resources.getString(R.string.news_api_key))
            try {
                // Calling await() on the Deferred object returns the result from the network call
                // when the value is ready. The await() method is non-blocking.
                val listResult = getArticlesDeferred.await().articles.toList()
                output.onFetchSuccess(listResult)
            } catch (t: Throwable) {
                t.printStackTrace()
                output.onFetchError(t)
            }
        }
    }

    override fun onCleared() {
        interactorJob.cancel()
    }

}