package com.mushaf.android.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.mushaf.android.Mushaf
import com.mushaf.android.R
import com.mushaf.android.databinding.MainBinding
import com.mushaf.android.data.PreferenceHelper.getCurrentMushaf
import com.mushaf.android.ui.surah.SurahListController

class MainActivity : AppCompatActivity() {

    public lateinit var binding: MainBinding
    lateinit var router: Router

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        if (!isTaskRoot) {
            finish()
            return
        }

        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        router = Conductor.attachRouter(this, binding.controllerContainer, savedInstance)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val id = item.itemId

            val currentRoot = router.backstack.firstOrNull()

            if (currentRoot?.tag()?.toIntOrNull() != id) {
                when (id) {
                    R.id.nav_read -> router.setRoot(RouterTransaction.with(SurahListController(getCurrentMushaf()!!)))
                }
            }
            true
        }

        if (!router.hasRootController()) {
            binding.bottomNavigation.selectedItemId = R.id.nav_read
        }

    }

    override fun onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed()
        }
    }


    fun downloadMushaf(): Mushaf = throw Exception("Placeholder")
}
