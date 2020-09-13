package com.example.viperdemo.contracts

import android.content.Context
import com.example.viperdemo.entity.Article

interface MainContract {

    interface View {
        fun showLoading()
        fun hideLoading()
        fun showArticles(data: List<Article>)
        fun showError(msg: String)
    }

    interface Presenter {
        fun onViewCreated()
        fun onDestroy()
    }

    interface Interactor {
        fun loadArticles(context: Context, output: InteractorOutput)
        fun onCleared()
    }

    interface InteractorOutput {
        fun onFetchSuccess(data: List<Article>)
        fun onFetchError(t: Throwable)
    }

}