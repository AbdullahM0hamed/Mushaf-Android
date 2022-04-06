package com.mushaf.android.ui.surah

import android.app.AlertDialog
import android.content.Context
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
import com.mushaf.android.RiwaayahState
import com.mushaf.android.appStore
import com.mushaf.android.data.PreferenceHelper.getAyaatCount
import com.mushaf.android.databinding.DialogBinding
import com.mushaf.android.databinding.SurahListBinding
import com.mushaf.android.ui.MainActivity
import com.mushaf.android.ui.base.BaseController
import okhttp3.Request
import org.reduxkotlin.StoreSubscription

class SurahListController(
    val context: Context
) : BaseController<SurahListBinding>() {

    private var itemAdapter: GenericItemAdapter = Companion.items()
    private lateinit var adapter: GenericFastAdapter
    private lateinit var mushaf: Mushaf
    private lateinit var surahRowItems: List<SurahRowItem>
    private lateinit var storeSubscription: StoreSubscription
    private lateinit var dialogView: View

    override fun inflateView(
        inflater: LayoutInflater,
        container: ViewGroup
    ): View {
        binding = SurahListBinding.inflate(inflater)
        dialogView = DialogBinding.inflate(inflater).root
        return binding.root
    }

    override fun onViewCreated(view: View) {
        super.onViewCreated(view)
        storeSubscription = appStore.subscribe {
            newState(appStore.state.riwaayahState)
        }

        adapter = FastAdapter.with(listOf(itemAdapter))
        binding.recycler.layoutManager = LinearLayoutManager(view.context)
        binding.recycler.adapter = adapter

        surahRowItems = (1..114).map { surah ->
            SurahRowItem(this, surah, 0)
        }
        
        itemAdapter.set(surahRowItems)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
        storeSubscription()
    }

    fun newState(state: RiwaayahState) {
        //TODO: handle state
    }

    fun downloadMushaf() {
        val url = "https://raw.githubusercontent.com/AbdullahM0hamed/Masaahif/master/masaahif.json"
        AlertDialog.Builder(context)
            .setView(dialogView)
            .show()
    }
}
