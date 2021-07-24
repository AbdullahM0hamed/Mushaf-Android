package com.mushaf.android.ui.page.curl

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Paint
import android.graphics.Rect
import android.os.Handler
import android.os.Message
import com.eschao.android.widget.pageflip.PageFlip
import com.eschao.android.widget.pageflip.PageFlipState
import com.mushaf.android.data.PreferenceHelper.getCurrentMushaf
import com.mushaf.android.data.PreferenceHelper.getPageCount

class SinglePageRender(
    context: Context,
    pageFlip: PageFlip,
    handler: Handler,
    pageNo: Int
) : PageRender(context, pageFlip, handler, pageNo) {

    override fun onDrawFrame() {
        pageFlip.deleteUnusedTextures()
        val page = pageFlip.getFirstPage()

        if (drawCommand == DRAW_MOVING_FRAME || drawCommand == DRAW_ANIMATING_FRAME) {
            if (pageFlip.getFlipState() == PageFlipState.FORWARD_FLIP) {
                if (!page.isSecondTextureSet()) {
                    drawPage(pageNo - 1)
                    page.setSecondTexture(bitmap)
                }
            } else if (!page.isFirstTextureSet()) {
                drawPage(pageNo + 1)
                page.setFirstTexture(bitmap)
            }

            pageFlip.drawFlipFrame()
        } else if (drawCommand == DRAW_FULL_PAGE) {
            if (!page.isFirstTextureSet()) {
                drawPage(pageNo)
                page.setFirstTexture(bitmap)
            }

            pageFlip.drawPageFrame()
        }

        val message = Message.obtain()
        message.what = 1
        message.arg1 = drawCommand
        handler.sendMessage(message)
    }

    override fun onSurfaceChanged(width: Int, height: Int) {
        if (backgroundBitmap != null) {
            backgroundBitmap?.recycle()
        }

        if (bitmap != null) {
            bitmap?.recycle()
        }

        val page = pageFlip.getFirstPage()
        bitmap = Bitmap.createBitmap(page.width().toInt(), page.height().toInt(), Bitmap.Config.ARGB_8888)
        canvas?.setBitmap(bitmap)
        canvas?.drawColor(0xFFFEFFFA.toInt())
    }

    override fun onEndedDrawing(what: Int): Boolean {
        if (what == DRAW_ANIMATING_FRAME) {
            if (pageFlip.animating()) {
                drawCommand = DRAW_ANIMATING_FRAME
                return true
            } else {
                val state = pageFlip.getFlipState()
                if (state == PageFlipState.END_WITH_BACKWARD) {
                    pageFlip.getFirstPage().setSecondTextureWithFirst()
                    pageNo++
                }

                if (state == PageFlipState.END_WITH_FORWARD) {
                    pageNo--
                }

                drawCommand = DRAW_FULL_PAGE
                return true
            }
        }

        return false
    }

    fun drawPage(number: Int) {
        canvas?.drawColor(0xFFFEFFFA.toInt())
        var background: Bitmap? = getImageFromZip(number)
        val rect = Rect(0, 30, canvas!!.width, canvas!!.height - 30)
        canvas?.drawBitmap(background!!, null, rect, Paint())
        background?.recycle()
        background = null
    }

    override fun canFlipForward(): Boolean = (pageNo > 1)

    override fun canFlipBackward(): Boolean {
        val mushaf = getCurrentMushaf()
        val pageCount = mushaf!!.getPageCount()

        if (pageNo < pageCount) {
            //pageFlip.getFirstPage().setSecondTextureWithFirst()
            return true
        }

        return false
    }
}
