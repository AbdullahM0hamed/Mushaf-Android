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

        val page = String.format("%03d", mushaf.getPageForSurahList().get(surah))
        binding.page.setImageBitmap(getImageFromZip(mushaf.location, "page$page.png"))
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }

    private fun getImageFromZip(location: String, page: String): Bitmap {
        val zip = ZipFile(location)
        val entry = zip.getEntry(page)
        val stream = zip.getInputStream(entry)

        return BitmapFactory.decodeStream(stream)
    }
}
