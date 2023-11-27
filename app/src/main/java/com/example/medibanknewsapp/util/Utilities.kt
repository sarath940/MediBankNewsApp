package com.example.medibanknewsapp.util


import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.medibanknewsapp.R


object Utilities {
    /**
     * checks if device has internet connection
     */
    fun hasInternetConnection(app: Context): Boolean {
        val connectivityManager = app.getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities =
            connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }


    /**
     * shows a dialog
     */
    fun showDialog(
        context: Context?,
        title: String?,
        message: String?,
        positive: DialogInterface.OnClickListener?,
        negative: DialogInterface.OnClickListener?
    ) {
        AlertDialog.Builder(context!!)
            .setTitle(title)
            .setMessage(message)
            .setPositiveButton(context.getString(R.string.ok), positive)
            .setNegativeButton(context.getString(R.string.cancel), negative)
            .create()
            .show()
    }


    /**
     * show toast
     */
    fun showToast(context: Context?, message: String?, length: Int = Toast.LENGTH_LONG) {
        Toast.makeText(context, message, length).show()
    }
}