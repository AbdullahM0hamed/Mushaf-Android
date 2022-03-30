package com.mushaf.android.ui

import android.content.pm.PackageManager
import android.Manifest.permission
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.bluelinelabs.conductor.RouterTransaction
import com.mushaf.android.Mushaf
import com.mushaf.android.R
import com.mushaf.android.databinding.MainBinding
import com.mushaf.android.data.PreferenceHelper.getCurrentMushaf
import com.mushaf.android.ui.surah.SurahListController
import kotlin.concurrent.thread
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import org.json.JSONArray

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

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            if (ContextCompat.checkSelfPermission(this, permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, arrayOf(permission.READ_EXTERNAL_STORAGE), 0)
            }
        }

        router = Conductor.attachRouter(this, binding.controllerContainer, savedInstance)

        binding.bottomNavigation.setOnNavigationItemSelectedListener { item ->
            val id = item.itemId

            val currentRoot = router.backstack.firstOrNull()

            if (currentRoot?.tag()?.toIntOrNull() != id) {
                when (id) {
                    R.id.nav_read -> {
                        router.setRoot(RouterTransaction.with(SurahListController()))
                    }
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
}
