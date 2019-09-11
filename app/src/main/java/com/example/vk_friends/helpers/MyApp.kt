package com.example.vk_friends.helpers

import android.app.Application
import com.example.vk_friends.ui.activity.LoginActivity
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKTokenExpiredHandler

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        VK.initialize(applicationContext)
        VK.addTokenExpiredHandler(tokenTracker)
    }

    private val tokenTracker = object : VKTokenExpiredHandler {
        override fun onTokenExpired() {
            LoginActivity.startFrom(this@MyApp)
        }
    }
}