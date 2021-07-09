package com.mushaf.android.data

import android.content.SharedPreferences
import com.mushaf.android.Mushaf
import com.mushaf.android.R

class PreferenceHelper {

    val context = App.applicationContext()

    val CURRENT_MUSHAF_KEY = "current_mushaf"
    val AYAAT_COUNT = "ayaat_count"
    val LOCATION_SUFFIX = "_location"
    val DOWNLOADED_SUFFIX = "_downloaded"
    val DB_SUFFIX = "_db"

    fun getPreferences(): SharedPreferences {
        return context.getSharedPreferences(context.pkgName, context.MODE_PRIVATE)
    }

    fun getCurrentMushaf(): Mushaf? {
        val prefs = getPreferenfes()
        val currentMushaf = prefs.getString(CURRENT_MUSHAF_KEY, null)

        return if (currentMushaf != null) {
            Mushaf(
                currentMushaf.split("_").get(0),
                currentMushaf.split("_").get(1) as Int,
                prefs.getString(currentMushaf + LOCATION_SUFFIX, ""),
                prefs.getBoolean(currentMushaf + DOWNLOADED_SUFFIX, false),
                prefs.getString(currentMushaf + DB_SUFFIX, "")
            )
        } else {
            null
        }
    }

    fun Mushaf.getAyaatCount: List<Int> = getPreferences().getString(riwaayah + "_" + type, "").split(",") as List<Int>
}
