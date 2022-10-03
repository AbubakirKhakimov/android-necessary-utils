package uz.abubakir_khakimov.necessary_utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class LocaleManager {
    /**
     * set current locale
     */
    public static Context setLocale(Context mContext) {
        String lang = getLanguagePref(mContext);
        if (lang.equals("empty")){
            return mContext;
        }
        return updateResources(mContext, lang);
    }
    /**
     * Get saved Locale from Hawk
     *
     * @param mContext current context
     * @return current locale key by default return english locale
     */
    private static String getLanguagePref(Context mContext) {
        return Hawk.get("selectedLanguage", "empty");
    }
    /**
     * update resource
     */
    public static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();

        Configuration config = new Configuration(res.getConfiguration());
        config.setLocale(locale);
        context = context.createConfigurationContext(config);
        res.updateConfiguration(config, res.getDisplayMetrics());
        return context;
    }
}