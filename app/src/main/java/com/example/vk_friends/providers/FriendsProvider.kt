package com.example.vk_friends.providers

import android.util.Log
import com.example.vk_friends.model.FriendModel
import com.example.vk_friends.presentation.presenter.FriendsPresenter
import com.vk.api.sdk.VK
import com.vk.api.sdk.VKApiCallback
import com.vk.api.sdk.exceptions.VKApiExecutionException
import com.vk.api.sdk.requests.VKRequest
import org.json.JSONObject

class FriendsProvider(var presenter: FriendsPresenter) {
//    fun testLoadFriends(hasFriends: Boolean) {
//        Handler().postDelayed({
//            val friendsList = ArrayList<FriendModel>()
//            if (hasFriends) {
//                val friend1 = FriendModel("Ruslan", "Baiandinov", "NSK", null, true)
//                val friend2 = FriendModel(
//                    "Test1",
//                    "Test1",
//                    null,
//                    "https://image.winudf.com/v2/image/Y29tLnNoYXJlLnRlc3QxX2ljb25fMTUxNzAxNzAwMl8wNjA/icon.png?w=170&fakeurl=1",
//                    false
//                )
//                val friend3 = FriendModel(
//                    "Test2",
//                    "Test2",
//                    null,
//                    "https://smoothpay.com/wp-content/uploads/2014/03/test1.jpg",
//                    false
//                )
//                friendsList.addAll(arrayListOf(friend1, friend2, friend3))
//            }
//            presenter.friendsLoaded(friendsList)
//        }, 2000)
//    }

    private val TAG = FriendsProvider::class.java.simpleName
    fun loadFriends() {
        VK.execute(VKFriendsRequest(), object : VKApiCallback<List<FriendModel>> {
            override fun success(result: List<FriendModel>) {
                presenter.friendsLoaded(result as ArrayList<FriendModel>)
            }

            override fun fail(error: VKApiExecutionException) {
                Log.e(TAG, error.toString())
            }
        })
    }

    inner class VKFriendsRequest(uid: Int = 0) : VKRequest<List<FriendModel>>("friends.get") {
        init {
            if (uid != 0) {
                addParam("user_id", uid)
            }
            addParam("fields", arrayOf("photo_200", "city"))
        }

        override fun parse(r: JSONObject): List<FriendModel> {
            val users = r.getJSONObject("response").getJSONArray("items")
            val result = ArrayList<FriendModel>()
            for (i in 0 until users.length()) {
                result.add(FriendModel.parse(users.getJSONObject(i)))
            }
            return result
        }
    }

}
