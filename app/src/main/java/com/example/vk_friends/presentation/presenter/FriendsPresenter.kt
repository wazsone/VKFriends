package com.example.vk_friends.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.vk_friends.R
import com.example.vk_friends.model.FriendModel
import com.example.vk_friends.presentation.view.FriendsView
import com.example.vk_friends.providers.FriendsProvider

@InjectViewState
class FriendsPresenter : MvpPresenter<FriendsView>() {
    fun loadFriends() {
        viewState.startLoading()
        FriendsProvider(this).loadFriends()
    }

    fun friendsLoaded(friendsList: ArrayList<FriendModel>) {
        viewState.endLoading()
        if (friendsList.size == 0) {
            viewState.setupEmptyList()
            viewState.showError(R.string.friends_no_items)
        } else {
            viewState.setupFriendsList(friendsList)
        }

    }
}