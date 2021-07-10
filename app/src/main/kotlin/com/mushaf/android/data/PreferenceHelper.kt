package com.mushaf.android.data

import android.content.Context
import androidx.preference.PreferenceManager
import com.mushaf.android.Mushaf
import com.mushaf.android.App
import com.mushaf.android.R

object PreferenceHelper {

    val context = App.applicationContext()

    val CURRENT_MUSHAF_KEY = "current_mushaf"
    val AYAAT_COUNT = "ayaat_count"
    val LOCATION_SUFFIX = "_location"
    val DOWNLOADED_SUFFIX = "_downloaded"
    val DB_SUFFIX = "_db"

    fun getCurrentMushaf(): Mushaf? {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val currentMushaf = prefs.getString(CURRENT_MUSHAF_KEY, "")
        val locationKey = currentMushaf + LOCATION_SUFFIX
        val dbKey = currentMushaf + DB_SUFFIX

        return if (currentMushaf != "") {
            Mushaf(
                currentMushaf!!.split("_").get(0),
                currentMushaf!!.split("_").get(1).toInt(),
                prefs.getString(locationKey, "")!!,
                prefs.getBoolean(currentMushaf + DOWNLOADED_SUFFIX, false),
                prefs.getString(dbKey, "")!!
            )
        } else {
            null
        }
    }

    fun Mushaf.getAyaatCount(): List<Int> = PreferenceManager.getDefaultSharedPreferences(context).getString(riwaayah + "_" + type, "")!!.split(",").map { it.toInt() }
}
