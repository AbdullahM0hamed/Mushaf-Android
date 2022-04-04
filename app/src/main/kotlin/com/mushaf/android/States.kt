package com.mushaf.android

data class AppState(
    val riwaayahState: RiwaayahState = RiwaayahState()
)

data class RiwaayahState(
    val riwaayah: String = "",
    val riwaayah_id: Int? = null
)
