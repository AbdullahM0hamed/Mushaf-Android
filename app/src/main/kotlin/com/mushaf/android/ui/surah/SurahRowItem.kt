package com.mushaf.android.ui.surah

import android.view.LayoutInflater
import android.view.ViewGroup
import com.mikepenz.fastadapter.binding.AbstractBindingItem
import com.mushaf.android.App
import com.mushaf.android.R
import com.mushaf.android.databinding.RootSurahRowBinding

data class SurahRowItem(
    val surah: Int
) :
    AbstractBindingItem<RootSurahRowBinding>() {

    override val type: Int = R.id.fastadapter_extension_item_id
    public lateinit var binding: RootSurahRowBinding

    override fun bindView(binding: RootSurahRowBinding, payloads: List<Any>) {
        binding.surahNumber.text = surah.toString()

        val context = App.applicationContext()
        binding.surahName.text = context.getString(context.resources.getIdentifier("surah_name_$surah", "string", context.packageName))
        binding.surahImg.setImageResource(context.resources.getIdentifier("surah_name_$surah", "drawable", context.packageName))
    }


    override fun createBinding(
        inflater: LayoutInflater,
        parent: ViewGroup?
    ): RootSurahRowBinding {
        binding = RootSurahRowBinding.inflate(inflater, parent, false)
        return binding
    }

    fun setAyahCount(count: Int) {
        binding.surahTypeCount.text = count.toString()
    }
}
