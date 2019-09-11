package com.example.vk_friends.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vk_friends.R
import com.example.vk_friends.presentation.presenter.LoginPresenter
import com.example.vk_friends.presentation.view.LoginView
import com.vk.api.sdk.VK
import com.vk.api.sdk.auth.VKScope
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : MvpAppCompatActivity(), LoginView {
    @InjectPresenter
    lateinit var loginPresenter: LoginPresenter

    private val TAG = LoginActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (VK.isLoggedIn()) {
            openFriends()
            return
        }

        setContentView(R.layout.activity_login)

        btn_login.setOnClickListener {
            //            loginPresenter.login(true)
            VK.login(this, arrayListOf(VKScope.FRIENDS))
        }

//        val fingerprints = VKUtils.getCertificateFingerprint(this, this.packageName)
//        Log.d(TAG, "fingerprints: ${fingerprints?.get(0)}")
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (loginPresenter.loginVK(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data)
        }

    }

    override fun startLoading() {
        btn_login.visibility = View.GONE
        cpv_login_progress.visibility = View.VISIBLE
        cpv_login_progress.startAnimation()
    }

    override fun endLoading() {
        btn_login.visibility = View.VISIBLE
        cpv_login_progress.visibility = View.GONE
        cpv_login_progress.stopAnimation()
    }

    override fun openFriends() {
        FriendsActivity.startFrom(this)
        finish()
    }

    override fun showError(textResource: Int) {
        Toast.makeText(applicationContext, getString(textResource), Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun startFrom(context: Context) {
            val intent = Intent(context, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            context.startActivity(intent)
        }
    }
}
