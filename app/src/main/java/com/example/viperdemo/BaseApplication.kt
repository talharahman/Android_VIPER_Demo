package com.example.viperdemo

import android.app.Application
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.Router

class BaseApplication : Application() {

    companion object {
        lateinit var INSTANCE: BaseApplication
    }

    init {
        INSTANCE = this
    }

    lateinit var baseRouter: Cicerone<Router>

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        this.initCicerone()
    }

    private fun BaseApplication.initCicerone() {
        this.baseRouter = Cicerone.create()
    }

}