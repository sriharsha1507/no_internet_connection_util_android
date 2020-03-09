package com.developergunda.nointernetconnection

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.developergunda.nointernetconnection.domain.networkconnectivity.NetworkConnectivityChecker
import com.developergunda.nointernetconnection.domain.networkconnectivity.util.NetworkConnectivityUtil

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initNetworkConnectivityChecker()
    }

    private fun initNetworkConnectivityChecker() {
        NetworkConnectivityChecker.init(this.applicationContext)
    }
}