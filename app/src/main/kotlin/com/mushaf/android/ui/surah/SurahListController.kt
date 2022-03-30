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

class SurahListController : BaseController<SurahListBinding>() {

    private var itemAdapter: GenericItemAdapter = Companion.items()
    private lateinit var adapter: GenericFastAdapter
    private lateinit var mushaf: Mushaf
    private lateinit var surahRowItems: List<SurahRowItem>

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
            SurahRowItem(this, surah, 0)
        }
        
        itemAdapter.set(surahRowItems)
    }

    override fun onDestroyView(view: View) {
        super.onDestroyView(view)
    }

    //fun downloadMushaf() {
        //thread {
            //val url = "https://raw.githubusercontent.com/AbdullahM0hamed/Masaahif/master/masaahif.json"
            //val client = OkHttpClient.Builder().build()
            //val request = Request.Builder()
                //.url(url)
                //.build()

            //val array = JSONArray(client.newCall(request).execute())
            //val default = array.getJSONObject(0)
            //android.widget.Toast.makeText(this, default.toString(), 5).show()
        //}
    //}
}
