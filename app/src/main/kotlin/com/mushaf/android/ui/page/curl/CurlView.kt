package com.mushaf.android.ui.page.curl

import android.content.Context
import android.opengl.GLSurfaceView
import android.opengl.GLSurfaceView.Renderer
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.AttributeSet
import android.util.Log
import com.eschao.android.widget.pageflip.PageFlip
import com.eschao.android.widget.pageflip.PageFlipException
import com.mushaf.android.data.PreferenceHelper.getAnimationDuration
import com.mushaf.android.data.PreferenceHelper.getPageMode
import com.mushaf.android.data.PreferenceHelper.getPixelsOfMesh
import java.util.concurrent.locks.ReentrantLock
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class CurlView : GLSurfaceView, Renderer {

    private val TAG = "CurlView"
    private var duration: Int = 1000
    private lateinit var pageFlip: PageFlip

    //Pain, naming it handler conflicts with some getHandler() method I can't find
    private lateinit var mHandler: Handler
    private lateinit var drawLock: ReentrantLock

    private var pageRender: PageRender? = null

    constructor(context: Context) : super(context) {
        initialise(context)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        initialise(context)
    }

    private fun initialise(context: Context) {
        newHandler()

        duration = getAnimationDuration()
        val pixelsOfMesh = getPixelsOfMesh()
        val isAuto = getPageMode()

        pageFlip = PageFlip(context)
        pageFlip.setSemiPerimeterRatio(0.8f)
            .setShadowWidthOfFoldEdges(5f, 60f, 0.3f)
            .setShadowWidthOfFoldBase(5f, 80f, 0.4f)
            .setPixelsOfMesh(pixelsOfMesh)
            .enableAutoPage(isAuto)

        setEGLContextClientVersion(2)

        drawLock = ReentrantLock()

        setRenderer(this)
        setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY)
    }

    fun enableAutoPage(enable: Boolean) {
        if (pageFlip.enableAutoPage(enable)) {
            try {
                drawLock.lock()
                if (pageFlip.getSecondPage() != null /* && pageRender instanceOf SinglePageRender */) {
                    // Double Page Render
                } else if (pageFlip.getSecondPage() == null /*&& pageRender instanceOf DoublePagesRender */) {
                    // Single Page Render
                }

                requestRender()
            } finally {
                drawLock.unlock()
            }
        }
    }

    fun isAutoPageEnabled(): Boolean = pageFlip.isAutoPageEnabled()

    fun getAnimateDuration(): Int = duration

    fun setAnimateDuration(newDuration: Int) {
        duration = newDuration
    }

    fun getPixelsOfMesh(): Int = pageFlip.getPixelsOfMesh()

    fun onFingerDown(x: Float, y: Float) {
        if (!pageFlip.isAnimating() && pageFlip.getFirstPage() != null) {
            pageFlip.onFingerDown(x, y)
        }
    }

    fun onFingerMove(x: Float, y: Float) {
        if (pageFlip.isAnimating()) {
            return
        }

        if (pageFlip.canAnimate(x, y)) {
            onFingerUp(x, y)
        } else if (pageFlip.onFingerMove(x, y)) {
            try {
                drawLock.lock()
                val render = pageRender
                if (render != null && render.onFingerMove(x, y)) {
                    requestRender()
                }
            } finally {
                drawLock.unlock()
            }
        }
    }

    fun onFingerUp(x: Float, y: Float) {
        if (!pageFlip.isAnimating()) {
            pageFlip.onFingerUp(x, y, duration)
            try {
                drawLock.lock()
                val render = pageRender
                if (render != null && render.onFingerUp(x, y)) {
                    requestRender()
                }
            } finally {
                drawLock.unlock()
            }
        }
    }

    override fun onDrawFrame(gl: GL10) {
        try {
            drawLock.lock()
            if (pageRender != null) {
                pageRender!!.onDrawFrame()
            }
        } finally {
            drawLock.unlock()
        }
    }

    override fun onSurfaceChanged(gl: GL10, width: Int, height: Int) {
        try {
            pageFlip.onSurfaceChanged(width, height)

            val pageNo = pageRender?.pageNo
            if (pageFlip.getSecondPage() != null && width > height) {
                // Double Page Render
            } else {
                // Single Page Render
            }

            pageRender?.onSurfaceChanged(width, height)
        } catch (e: PageFlipException) {
            Log.e(TAG, "Failed to run PageFlipFlipRender:onSurfaceChanged")
        }
    }

    override fun onSurfaceCreated(gl: GL10, config: EGLConfig) {
        try {
            pageFlip.onSurfaceCreated()
        } catch (e: PageFlipException) {
            Log.e(TAG, "Failed to run PageFlipFlipRender:onSurfaceCreated")
        }
    }

    private fun newHandler() {
        mHandler = object : Handler(Looper.getMainLooper()) {
            override fun handleMessage(msg: Message?) {
                when (msg?.what ?: 0) {
                    1 -> {
                        try {
                            drawLock.lock()
                            val render = pageRender
                            if (render != null && render.onEndedDrawing(msg!!.arg1)) {
                                requestRender()
                            }
                        } finally {
                            drawLock.unlock()
                        }
                    }
                }
            }
        }
    }
}
