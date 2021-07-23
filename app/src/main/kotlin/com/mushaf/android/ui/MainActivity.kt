package com.mushaf.android.ui

import android.content.pm.PackageManager
import android.Manifest.permission
import android.os.Build
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
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
import com.mushaf.android.ui.page.curl.CurlView
import com.mushaf.android.ui.surah.SurahListController

class MainActivity : AppCompatActivity() {

    public lateinit var binding: MainBinding
    lateinit var router: Router
    lateinit var gestureDetector: GestureDetector

    public var curlView: CurlView? = null

    override fun onCreate(savedInstance: Bundle?) {
        super.onCreate(savedInstance)

        if (!isTaskRoot) {
            finish()
            return
        }

        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        gestureDetector = GestureDetector(this, this)

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

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP && curlView != null) {
            curlView.onFingerUp(event.X, event.Y)
            return true
        }

        return gestureDetector.onTouchEvent(event)
    }

    override fun onDown(event: MotionEvent): Boolean {
        if (curlView != null) {
            curlView.onFingerDown(event.X, event.Y)
            return true
        }

        return super.onDown(event)
    }

    override fun onScroll(event: MotionEvent, event2: MotionEvent, distanceX: Float, distanceY: Float): Boolean {
        if (curlView != null) {
            curlView.onFingerMove(event2.X, event2.Y)
            return true
        }

        return super.onScroll(event, event2, distanceX, distanceY)
    }

    fun downloadMushaf(): Mushaf = throw Exception("Placeholder")
}
