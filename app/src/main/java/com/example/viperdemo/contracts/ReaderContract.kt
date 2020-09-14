package com.example.viperdemo.contracts

import com.example.viperdemo.entity.Article

interface ReaderContract {

    interface View {
        fun loadArticle(url: String)
        fun showError(msg: String)
    }

    interface Presenter {
        fun onBackPressed()
        fun onViewCreated(data: Article)
        fun onDestroy()
    }
}