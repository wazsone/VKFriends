package com.example.vk_friends.presentation.presenter

import android.content.Intent
import android.os.Handler
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vk_friends.R
import com.example.vk_friends.presentation.view.LoginView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKAccessToken
import com.vk.api.sdk.auth.VKAuthCallback

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>() {
    fun loginTest(isSuccess: Boolean) {
        viewState.startLoading()
        Handler().postDelayed({
            viewState.endLoading()
            if (isSuccess) {
                viewState.openFriends()
            } else {
                viewState.showError(R.string.login_error)
            }
        }, 500)
    }

    fun loginVK(requestCode: Int, resultCode: Int, data: Intent?): Boolean {
        val callback = object : VKAuthCallback {
            override fun onLogin(token: VKAccessToken) {
                viewState.openFriends()
            }

            override fun onLoginFailed(errorCode: Int) {
                viewState.showError(R.string.login_error)
            }
        }
        return data == null || !VK.onActivityResult(requestCode, resultCode, data, callback)
    }
}