package com.example.vk_friends.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.vk_friends.R
import com.example.vk_friends.adapters.FriendsAdapter
import com.example.vk_friends.model.FriendModel
import com.example.vk_friends.presentation.presenter.FriendsPresenter
import com.example.vk_friends.presentation.view.FriendsView
import com.vk.api.sdk.VK
import kotlinx.android.synthetic.main.activity_friends.*

class FriendsActivity : MvpAppCompatActivity(), FriendsView {
    @InjectPresenter
    lateinit var friendsPresenter: FriendsPresenter

    private var adapter = FriendsAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_friends)

        btn_logout.setOnClickListener {
            VK.logout()
            LoginActivity.startFrom(this)
            finish()
        }

        et_friends_search.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                adapter.filter(s.toString())
            }

        })

        friendsPresenter.loadFriends()
        rv_friends_list.adapter = adapter
        rv_friends_list.layoutManager =
            LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        rv_friends_list.setHasFixedSize(true)

    }

    override fun startLoading() {
        rv_friends_list.visibility = View.GONE
        tv_no_items.visibility = View.GONE
        cpv_friends_progress.visibility = View.VISIBLE

    }

    override fun endLoading() {
        cpv_friends_progress.visibility = View.GONE
    }

    override fun showError(textResource: Int) {
        tv_no_items.text = getText(textResource)
    }

    override fun setupEmptyList() {
        rv_friends_list.visibility = View.GONE
        tv_no_items.visibility = View.VISIBLE
    }

    override fun setupFriendsList(friendsList: ArrayList<FriendModel>) {
        rv_friends_list.visibility = View.VISIBLE
        tv_no_items.visibility = View.GONE

        adapter.setupFriends(friendsList)
    }

    companion object {
        fun startFrom(context: Context) {
            val intent = Intent(context, FriendsActivity::class.java)
            context.startActivity(intent)
        }
    }
}
