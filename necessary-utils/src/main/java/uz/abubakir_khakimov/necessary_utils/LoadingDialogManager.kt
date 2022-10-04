package uz.abubakir_khakimov.necessary_utils

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import uz.abubakir_khakimov.necessary_utils.databinding.LoadingDialogLayoutBinding

class LoadingDialogManager(private val context: Context, private val animType: Int) {

    companion object{
        const val TYPE_DEFAULT_LOADING = 0
        const val TYPE_SEARCHING = 1
        const val TYPE_CUSTOM = 2
    }

    private var customDialog: AlertDialog? = null
    private var customAnim: String? = null

    fun setAnim(anim: String){
        customAnim = anim
    }

    fun show(){
        if (customDialog == null){
            createDialog()
        }
    }

    private fun createDialog(){
        customDialog = AlertDialog.Builder(context).create()
        val dialogBinding = LoadingDialogLayoutBinding.inflate(LayoutInflater.from(context))
        customDialog?.setView(dialogBinding.root)

        dialogBinding.lottieAnimation.setAnimation(
            getAnimByType()
        )

        customDialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        customDialog?.setCancelable(false)
        customDialog?.show()
    }

    fun dismiss(){
        customDialog?.dismiss()
        customDialog = null
    }

    private fun getAnimByType(): String{
        return when(animType){
            0 -> "loading_anim.json"
            1 -> "searching.json"
            2 -> customAnim.ifNull {
                throw Exception("No animation provided! " +
                        "\"animType\" is set to \"TYPE_CUSTOM\", but no animation is provided!")
            }
            else -> "loading_anim.json"
        }
    }

}