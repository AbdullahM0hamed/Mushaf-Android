package com.mushaf.android.ui.surah

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.GenericFastAdapter
import com.mikepenz.fastadapter.adapters.GenericItemAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter.Companion
import com.mushaf.android.Mushaf
import com.mushaf.android.databinding.SurahListBinding
import com.mushaf.android.ui.MainActivity
import com.mushaf.android.ui.base.BaseController

data class SurahListController(
    val mushaf: Mushaf
) : BaseController<SurahListBinding>() {

    private var itemAdapter: GenericItemAdapter = Companion.items()
    private lateinit var adapter: GenericFastAdapter
    private var surahRowItems = (1..114).map { SurahRowItem(it, mushaf.ayaat_per_surah.get(it - 1)) }

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

        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recycler: RecyclerView, dx: Int, dy: Int) {
                val activityBinding = (activity as MainActivity).binding
                if (dy > 0 && activityBinding.bottomNavigation.getVisibility() == View.VISIBLE) {
                    activityBinding.bottomNavigation.setVisibility(View.GONE)
                } else {
                    activityBinding.bottomNavigation.setVisibility(View.VISIBLE)
                }
            }
        })
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }
}
