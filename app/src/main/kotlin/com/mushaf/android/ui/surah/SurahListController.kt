package com.mushaf.android.ui.surah

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericFastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter.Companion
import com.mushaf.android.Mushaf
import com.mushaf.android.data.PreferenceHelper.getAyaatCount
import com.mushaf.android.databinding.SurahListBinding
import com.mushaf.android.ui.MainActivity
import com.mushaf.android.ui.base.BaseController
import kotlin.concurrent.thread

class SurahListController : BaseController<SurahListBinding> {

    private var itemAdapter: GenericItemAdapter = Companion.items()
    private lateinit var adapter: GenericFastAdapter
    private lateinit var mushaf: Mushaf
    private lateinit var surahRowItems: List<SurahRowItem>

    @Suppress("unused")
    constructor(bundle: Bundle) : super(bundle)

    constructor(mushaf: Mushaf) : this(
        Bundle().apply {
            putString("current_mushaf", mushaf.riwaayah + "_" + mushaf.type.toString())
        }
    ) {
        this.mushaf = mushaf
    }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View {
        binding = SurahListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        adapter = FastAdapter.with(listOf(itemAdapter))
        binding.recycler.layoutManager = LinearLayoutManager(view.context)
        binding.recycler.adapter = adapter

        surahRowItems = (1..114).map { surah ->
            val count = mushaf.getAyaatCount().get(surah - 1)
            SurahRowItem(surah, count)
        }
        
        itemAdapter.set(surahRowItems)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }
}
