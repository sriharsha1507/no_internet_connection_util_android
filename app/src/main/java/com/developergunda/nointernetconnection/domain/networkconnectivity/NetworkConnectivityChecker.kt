package com.developergunda.nointernetconnection.domain.networkconnectivity

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.util.Log
import com.developergunda.nointernetconnection.domain.networkconnectivity.util.NetworkConnectivityUtil
import com.developergunda.nointernetconnection.domain.networkconnectivity.util.SingleLiveEvent

object NetworkConnectivityChecker : SingleLiveEvent<Boolean>() {

    private lateinit var networkConnectivityUtil: NetworkConnectivityUtil
    private lateinit var connectivityManager: ConnectivityManager

    override fun onActive() {
        registerCallback()
        Log.d("DG", "Network Connectivity Registered")
        super.onActive()
    }

    override fun onInactive() {
        removeCallback()
        Log.d("DG", "Network Connectivity Unregistered")
        super.onInactive()
    }

    fun checkForConnection() {
        value = networkConnectivityUtil.isConnected()
    }

    fun hasConnection():Boolean = networkConnectivityUtil.isConnected()

    //Should be injected in Application class : )
    fun init(context: Context) {
        networkConnectivityUtil = NetworkConnectivityUtil(context)
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }


    private fun notifyObservers(connectionStatus: Boolean) {
        postValue(connectionStatus)
    }

    private fun registerCallback() {
        val networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun removeCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            notifyObservers(true)
            super.onAvailable(network)
        }

        override fun onLost(network: Network) {
            notifyObservers(false)
            super.onLost(network)
        }
    }
}