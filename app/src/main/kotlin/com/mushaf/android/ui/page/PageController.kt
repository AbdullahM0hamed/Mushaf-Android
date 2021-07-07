package com.mushaf.android.ui.page

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mushaf.android.databinding.QuranPageBinding
import com.mushaf.android.ui.base.BaseController

data class PageController(
    val page_num: Int
) : BaseController<QuranPageBinding>() {

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View {
        binding = QuranPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }
}
