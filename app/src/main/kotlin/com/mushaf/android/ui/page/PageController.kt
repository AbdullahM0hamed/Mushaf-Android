package com.mushaf.android.ui.page

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mushaf.android.Mushaf
import com.mushaf.android.databinding.QuranPageBinding
import com.mushaf.android.data.PreferenceHelper.getPageForSurahList
import com.mushaf.android.ui.base.BaseController

class PageController : BaseController<QuranPageBinding> {

    private lateinit var mushaf: Mushaf
    private lateinit var surah: Int

    @Suppress("unused")
    constructor(bundle: Bundle) : super(bundle)

    constructor(mushaf: Mushaf, surah: Int) : this(
        Bundle().apply {
            putString("mushaf", mushaf.toString())
        }
    ) {
        this.mushaf = mushaf
        this.surah = surah
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View {
        binding = QuranPageBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        val page = String.format("%03d", mushaf.getPageForSurahList().get(surah))
        binding.page.setImageURI(Uri.parse("jar:file:/${mushaf.location}!/page$page.png"))
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }
}
