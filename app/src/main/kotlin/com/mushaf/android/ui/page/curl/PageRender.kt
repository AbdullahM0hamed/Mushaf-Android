package com.mushaf.android.ui.page.curl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.os.Handler
import com.eschao.android.widget.pageflip.OnPageFlipListener
import com.eschao.android.widget.pageflip.PageFlip
import com.mushaf.android.data.PreferenceHelper.getCurrentMushaf
import java.util.zip.ZipFile

abstract class PageRender(
    context: Context, 
    pageFlip: PageFlip, 
    handler: Handler, 
    pageNo: Int
) : OnPageFlipListener {

    protected companion object {
        const val DRAW_MOVING_FRAME = 0
        const val DRAW_ANIMATING_FRAME = 1
        const val DRAW_FULL_PAGE = 2
    }

    private var canvas: Canvas? = null
    private var bitmap: Bitmap? = null
    private var backgroundBitmap: Bitmap? = null
    
    private lateinit var drawCommand = DRAW_FULL_PAGE

    init { 
        canvas = Canvas()
        pageFlip.setListener(this)
    }

    fun release() {
        if (bitmap != null) {
            bitmap?.recycle()
            bitmap = null
        }

        pageFlip.setListener(null)
        canvas = null
        backgroundBitmap = null
    }

    fun onFingerMove(x: Float, y: Float): Boolean {
        drawCommand = DRAW_MOVING_FRAME
        return true
    }

    fun onFingerUp(x: Float, y: Float): Boolean {
        if (pageFlip.animating()) {
            drawCommand = DRAW_ANIMATING_FRAME
            return true
        }

        return false
    }

    private fun getImageFromZip(pageNo: Int): Bitmap {
        val mushaf = getCurrentMushaf()
        val page = "page${String.format("%03d", pageNo)}.png"
        val zip = ZipFile(mushaf.location)
        val entry = zip.getEntry(page)
        val stream = zip.getInputStream(entry)

        return BitmapFactory.decodeStream(stream)
    }

    abstract fun onDrawFrame()

    abstract fun onSurfaceChanged(width: Int, height: Int)

    abstract fun onEndedDrawing(what: Int): Boolean
}
