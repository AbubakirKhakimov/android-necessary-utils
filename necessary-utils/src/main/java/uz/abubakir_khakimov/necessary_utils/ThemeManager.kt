package uz.abubakir_khakimov.necessary_utils

import androidx.appcompat.app.AppCompatDelegate
import com.orhanobut.hawk.Hawk

class ThemeManager {

    fun changeTheme(mode: Int){
        Hawk.put("selectedTheme", mode)
        setTheme(mode)
    }

    fun runtimeRestoreTheme(){
        val mode: Int? = getSelectedTheme()

        if (mode == AppCompatDelegate.MODE_NIGHT_NO || mode == AppCompatDelegate.MODE_NIGHT_YES){
            setTheme(mode)
        }
    }

    private fun setTheme(mode: Int){
        AppCompatDelegate.setDefaultNightMode(mode)
    }

    private fun getSelectedTheme(): Int? {
        return Hawk.get("selectedTheme", null)
    }

}