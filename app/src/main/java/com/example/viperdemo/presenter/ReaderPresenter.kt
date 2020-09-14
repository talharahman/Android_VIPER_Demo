package com.example.viperdemo.presenter

import android.widget.Toolbar
import com.example.viperdemo.BaseApplication
import com.example.viperdemo.contracts.ReaderContract
import com.example.viperdemo.entity.Article
import ru.terrakok.cicerone.Router

class ReaderPresenter(private var view: ReaderContract.View?)
    : ReaderContract.Presenter {

    private val router: Router? by lazy { BaseApplication.INSTANCE.baseRouter.router }

    override fun onBackPressed() {
        router?.exit()
    }

    override fun onViewCreated(data: Article) {
        view?.loadArticle(data.url)
    }

    override fun onDestroy() {
        view = null
    }
}