package com.mushaf.android

import android.app.Application
import android.content.Context

import com.mushaf.android.AppState
import com.mushaf.android.appReducer

import org.reduxkotlin.createThreadSafeStore

val appStore = createThreadSafeStore(::appReducer, AppState())

class App : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: App? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}
