package com.developergunda.nointernetconnection.ui.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.developergunda.nointernetconnection.domain.networkconnectivity.NetworkConnectivityChecker
import com.google.android.material.snackbar.Snackbar


open class BaseFragment : Fragment() {
    var snackbar: Snackbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setInternetConnectivityObserver()
    }

    override fun onResume() {
        super.onResume()
        NetworkConnectivityChecker.checkForConnection()
    }

    private fun setInternetConnectivityObserver() {
        NetworkConnectivityChecker.observe(this, liveDataObserver)
    }

    private val liveDataObserver: Observer<Boolean> = Observer { isConnected ->
        if (!isConnected) {
            //Can use your own logic later -- Using snackbar as default. Build your own listener to create custom view
            view?.let {
                snackbar = Snackbar.make(it, "No Internet Connection", Snackbar.LENGTH_LONG)
                snackbar?.show()
            }
        } else {
            snackbar?.dismiss()
        }
    }
}