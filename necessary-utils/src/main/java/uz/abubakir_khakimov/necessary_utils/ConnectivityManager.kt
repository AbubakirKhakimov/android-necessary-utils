package uz.abubakir_khakimov.necessary_utils

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.view.isVisible
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.HttpException
import retrofit2.Retrofit
import uz.abubakir_khakimov.necessary_utils.databinding.NoConnectedDialogLayoutBinding
import uz.abubakir_khakimov.necessary_utils.models.ResponseDetail
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketTimeoutException
import java.util.concurrent.TimeoutException


class ConnectivityManager(
    private val context: Context,
    private val retrofit: Retrofit,
    private val callBack: CallBack,
    private val dialogCloseVisible: Boolean
) {

    interface CallBack{
        fun refreshClicked(toWhereRefresh: String)
    }

    private var toWhereRefresh: String = "entryData"
    private var customDialog: AlertDialog? = null

    private fun show(){
        if (customDialog == null){
            createDialog()
        }
    }

    private fun createDialog(){
        customDialog = AlertDialog.Builder(context).create()
        val dialogBinding = NoConnectedDialogLayoutBinding.inflate(LayoutInflater.from(context))
        customDialog?.setView(dialogBinding.root)

        dialogBinding.refresh.setOnClickListener {
            callBack.refreshClicked(toWhereRefresh)
            dismiss()
        }

        dialogBinding.close.setOnClickListener {
            dismiss()
        }

        dialogBinding.close.isVisible = dialogCloseVisible
        customDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog?.setCancelable(false)
        customDialog?.show()
    }

    private fun dismiss(){
        customDialog?.dismiss()
        customDialog = null
    }

    fun checkConnectionError(t: Throwable?, toWhereRefresh: String){
        when {
            t is HttpException -> {
                Log.d("testResponse", t.response().toString())

                val errorMassage = parseError(t.response()?.errorBody())
                if (errorMassage.isEmpty()){
                    Toast.makeText(
                        context,
                        context.getString(R.string.responseError),
                        Toast.LENGTH_SHORT
                    ).show()
                }else {
                    Toast.makeText(
                        context,
                        "${context.getString(R.string.error).replace("!", ":")} "
                                + errorMassage,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            t is SocketTimeoutException || t is InterruptedIOException || t is TimeoutException -> {
                Toast.makeText(context, context.getString(R.string.connection_timeout), Toast.LENGTH_SHORT).show()
            }
            !isNetworkConnected() -> {
                this.toWhereRefresh = toWhereRefresh
                show()
            }
            else -> {
                t?.printStackTrace()
                Toast.makeText(context, context.getString(R.string.error), Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun parseError(errorBody: ResponseBody?): String {
        val converter: Converter<ResponseBody, ResponseDetail> = retrofit
            .responseBodyConverter(ResponseDetail::class.java, arrayOfNulls<Annotation>(0))
        return try {
            if (errorBody.isNull()){
                ""
            }else{
                converter.convert(errorBody!!)!!.detail
            }
        } catch (e: IOException) {
            return ""
        }
    }

    private fun isNetworkConnected():Boolean{
        var result = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork ?: return false
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false
            result = when{
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        }else{
            connectivityManager.run {
                connectivityManager.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        else -> false
                    }
                }
            }
        }
        return result
    }

}