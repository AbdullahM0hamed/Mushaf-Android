package com.mushaf.android

data class Mushaf(
    val riwaayah: String,
    val ayaat_per_surah: List<Int>,
    val downloaded: Boolean,
    val location: String,
    val db_name: String
)
