package com.mushaf.android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bluelinelabs.conductor.Conductor
import com.bluelinelabs.conductor.Router
import com.mushaf.android.databinding.MainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: MainBinding
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
    }
}
