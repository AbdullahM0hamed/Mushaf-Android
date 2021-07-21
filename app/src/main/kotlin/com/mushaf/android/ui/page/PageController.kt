package com.mushaf.android.ui.page

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mushaf.android.Mushaf
import com.mushaf.android.databinding.QuranPageBinding
import com.mushaf.android.data.PreferenceHelper.getPageForSurahList
import com.mushaf.android.ui.base.BaseController
import com.mushaf.android.ui.MainActivity
import java.util.zip.ZipFile

class PageController : BaseController<QuranPageBinding> {

    private lateinit var mushaf: Mushaf
    private var surah: Int = 1

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
        (activity as MainActivity).binding.bottomNavigation.setVisibility(View.GONE)
        binding.root.setBackgroundColor(0xFFFEFFFA.toInt())
        binding.page.pageNo = mushaf.getPageForSurahList().get(surah - 1)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        (activity as MainActivity).binding.bottomNavigation.setVisibility(View.VISIBLE)
    }
}
