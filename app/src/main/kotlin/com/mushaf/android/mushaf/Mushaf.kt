package com.mushaf.android

data class Mushaf(
    val riwaayah: String,
    // To distinguish different masaahif of the same riwaayah
    val type: Int,
    val location: String,
    val downloaded: Boolean,
    val db_name: String
)
