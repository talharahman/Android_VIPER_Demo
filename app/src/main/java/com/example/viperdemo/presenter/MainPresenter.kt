package com.example.viperdemo.presenter

import android.content.Context
import android.util.Log
import com.example.viperdemo.BaseApplication
import com.example.viperdemo.contracts.MainContract
import com.example.viperdemo.entity.Article
import com.example.viperdemo.interactor.MainInteractor
import com.example.viperdemo.view.ReaderActivity
import ru.terrakok.cicerone.Router

class MainPresenter(private var view: MainContract.View?, private val context: Context)
    : MainContract.Presenter {

    private var interactor: MainContract.Interactor? = MainInteractor()
    private val router: Router? by lazy { BaseApplication.INSTANCE.baseRouter.router }

    override fun articleSelected(article: Article?) {
        router?.navigateTo(ReaderActivity.TAG, article)
    }

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