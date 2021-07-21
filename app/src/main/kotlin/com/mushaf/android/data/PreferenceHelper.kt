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
    val DURATION = "duration"
    val MESH_PIXELS = "mesh_pixels"
    val PAGE_MODE = "page_mode"
    val PAGE_COUNT = "page_count"

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

    fun Mushaf.getAyaatCount(): List<Int> = PreferenceManager.getDefaultSharedPreferences(context).getString("${riwaayah}_${type}_ayahs", "")!!.split(",").map { it.toInt() }

    fun Mushaf.getPageForSurahList(): List<Int> = PreferenceManager.getDefaultSharedPreferences(context).getString("${riwaayah}_${type}_pages", "")!!.split(",").map { it.toInt() }

    fun getAnimationDuration(): Int = PreferenceManager.getDefaultSharedPreferences(context).getInt(DURATION, 1000)

    fun getPixelsOfMesh(): Int = PreferenceManager.getDefaultSharedPreferences(context).getInt(MESH_PIXELS, 10)

    fun getPageMode(): Boolean = PreferenceManager.getDefaultSharedPreferences(context).getBoolean(PAGE_MODE, true)

    fun Mushaf.getPageCount(): Int = PreferenceManager.getDefaultSharedPreferences(context).getInt("${riwaayah}_${type}_$PAGE_COUNT", 604)
}
