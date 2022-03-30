package com.mushaf.android.ui.surah

import android.view.LayoutInflater
import android.view.ViewGroup
import com.bluelinelabs.conductor.RouterTransaction
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mushaf.android.App
import com.mushaf.android.Mushaf
import com.mushaf.android.R
import com.mushaf.android.databinding.RootSurahRowBinding
import com.mushaf.android.ui.page.PageController

data class SurahRowItem(
    val controller: SurahListController,
    val surah: Int,
    val ayahCount: Int
) :
    AbstractBindingItem<RootSurahRowBinding>() {

    override val type: Int = R.id.fastadapter_extension_item_id

    override fun bindView(binding: RootSurahRowBinding, payloads: List<Any>) {
        binding.surahNumber.text = surah.toString()

        val context = App.applicationContext()
        binding.surahTypeCount.text = context.resources.getString(R.string.ayah_count, ayahCount)
        binding.surahName.text = context.getString(context.resources.getIdentifier("surah_name_$surah", "string", context.packageName))
        binding.surahImg.setImageResource(context.resources.getIdentifier("surah_name_$surah", "drawable", context.packageName))

        binding.root.setOnClickListener {
            //controller.router.pushController(RouterTransaction.with(PageController(mushaf, surah)))
        }
    }


    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): RootSurahRowBinding {
        return RootSurahRowBinding.inflate(inflater, parent, false)
    }
}
