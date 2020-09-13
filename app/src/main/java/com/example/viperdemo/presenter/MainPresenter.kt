package com.example.viperdemo.presenter

import android.content.Context
import android.util.Log
import com.example.viperdemo.contracts.MainContract
import com.example.viperdemo.entity.Article
import com.example.viperdemo.interactor.MainInteractor

class MainPresenter(private var view: MainContract.View?, private val context: Context)
    : MainContract.Presenter {

    private var interactor: MainContract.Interactor? = MainInteractor()

    override fun onViewCreated() {
        view?.showLoading()
        interactor?.loadArticles(context, object : MainContract.InteractorOutput {
            override fun onFetchSuccess(data: List<Article>) {
                view?.hideLoading()
                view?.showArticles(data)
            }

            override fun onFetchError(t: Throwable) {
                view?.hideLoading()
                view?.showError(t.message.toString())
            }
        })
    }

    override fun onDestroy() {
        interactor?.onCleared()
        view = null
        interactor = null
    }

}