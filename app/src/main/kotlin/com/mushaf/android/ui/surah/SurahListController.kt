package com.mushaf.android.ui.surah

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

data class SurahListController(
    val mushaf: Mushaf
) : BaseController<SurahListBinding>() {

    private var itemAdapter: GenericItemAdapter = Companion.items()
    private lateinit var adapter: GenericFastAdapter
    private var surahRowItems = (1..114).map { SurahRowItem(it) }

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View {
        binding = SurahListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)

        itemAdapter.set(surahRowItems)
        adapter = FastAdapter.with(listOf(itemAdapter))
        binding.recycler.layoutManager = LinearLayoutManager(view.context)
        binding.recycler.adapter = adapter

        thread {
            for (i in 0 until surahRowItems.size) {
                activity!!.runOnUiThread {
                    (surahRowItems.get(i) as SurahRowItem).ayahCount = mushaf.getAyaatCount().get(i) 
                }
            }
        }
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }
}
