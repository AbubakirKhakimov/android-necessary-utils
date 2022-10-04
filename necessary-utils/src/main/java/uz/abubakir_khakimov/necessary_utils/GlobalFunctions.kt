package uz.abubakir_khakimov.necessary_utils

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.util.*

fun <T> T?.isNull() = (this == null)

fun <T> T?.isNotNull() = (this != null)

inline fun <T> T?.ifNull(body: () -> T): T{
    return this ?: body()
}

inline fun <T, A> T?.getNotNullValue(body: (T?) -> A): A{
    return body(this)
}

fun <T> T.isZero() = (this == 0)

fun <T> T.isNotZero() = (this != 0)

fun checkLoadedAllData(dataList: List<Any?>): Boolean {
    dataList.forEach {
        if (it.isNull()) return false
    }

    return true
}

fun TextInputEditText.closeKeyboardWithFocus(context: Context) {
    this.clearFocus()
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun TextInputEditText.openKeyboardWithFocus(context: Context){
    this.requestFocus()
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun TextInputEditText.closeKeyboard(context: Context) {
    val inputMethodManager =
        context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
}

fun TextInputEditText.openKeyboard(context: Context){
    val inputMethodManager =
        context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
}

fun Long.toStringDate(): String{
    return SimpleDateFormat("dd.MM.yyyy", Locale.getDefault()).format(Date(this))
}

fun String.toServerDateStructure(): String {
    val dateArray = this.split(".") //dd.MM.yyyy
    return "${dateArray[2]}-${dateArray[1]}-${dateArray[0]}" //yyyy-MM-dd
}

fun String.toMobileDateAndTimeStructure(): String{
    val date = this.substring(0, this.indexOf("T"))
    val time = this.substring(this.indexOf("T") + 1)
    return "${date.toMobileDateStructure()} $time" //dd.MM.yyyy hh:mm
}

fun String.toMobileDateStructure(): String{
    val dateArray = this.split("-") //yyyy-MM-dd
    return "${dateArray[2]}.${dateArray[1]}.${dateArray[0]}" //dd.MM.yyyy
}

fun String.showToast(view: View){
    Toast.makeText(view.context, this, Toast.LENGTH_SHORT).show()
}

fun String.showToast(context: Context){
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}

fun String.showSnackBar(view: View){
    Snackbar.make(view, this, Snackbar.LENGTH_SHORT).show()
}