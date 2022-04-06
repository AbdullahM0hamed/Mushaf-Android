package com.mushaf.android

import com.mushaf.android.AppState
import com.mushaf.android.DownloadRiwaayah
import com.mushaf.android.RiwaayahState

fun appStateReducer(state: AppState, action: Any) = AppState(
    riwaayahState = riwaayahStateReducer(state.riwaayahState, action)
)

fun riwaayahStateReducer(state: RiwaayahState, action: Any): RiwaayahState {
    var currentState = state

    if (action is DownloadRiwaayah) {
        currentState.copy(action.riwaayah, action.riwaayahId)
    }

    return currentState
}
