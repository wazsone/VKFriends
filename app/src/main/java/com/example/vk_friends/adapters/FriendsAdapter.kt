package com.example.vk_friends.adapters

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vk_friends.R
import com.example.vk_friends.model.FriendModel
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class FriendsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    class FriendsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var avatar: CircleImageView = itemView.findViewById(R.id.civ_friend_avatar)
        private var name: TextView = itemView.findViewById(R.id.tv_friend_name)
        private var city: TextView = itemView.findViewById(R.id.tv_friend_city)
        private var online: View = itemView.findViewById(R.id.iv_friend_online)
        fun bind(friendModel: FriendModel) {
            if (!TextUtils.isEmpty(friendModel.avatar)) {
                Picasso.get().load(friendModel.avatar).error(R.drawable.user_placeholder)
                    .into(avatar)
            } else {
                avatar.setImageResource(R.drawable.user_placeholder)
            }
            name.text = "${friendModel.name} ${friendModel.surname}"
//            city.text = friendModel.id.toString()
            city.text =
                if (friendModel.city.isEmpty()) itemView.context.getString(R.string.friend_no_city) else friendModel.city
            online.visibility = if (friendModel.isOnline) View.VISIBLE else View.GONE
        }

    }

    private var sourceList: ArrayList<FriendModel> = ArrayList()
    private var friendsList: ArrayList<FriendModel> = ArrayList()

    fun filter(query: String) {
        friendsList.clear()
        sourceList.forEach {
            if (it.name.contains(query, true) ||
                it.surname.contains(query, true)
            ) {
                friendsList.add(it)
            } else {
                it.city.let { city ->
                    if (city.contains(query, true)) {
                        friendsList.add(it)
                    }
                }
            }
        }
        friendsList.sortByDescending { it.isOnline }
        notifyDataSetChanged()
    }

    fun setupFriends(friendsList: ArrayList<FriendModel>) {
        sourceList.clear()
        sourceList.addAll(friendsList)
        filter("")
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemView = layoutInflater.inflate(R.layout.cell_friend, parent, false)
        return FriendsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return friendsList.count()
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is FriendsViewHolder) {
            holder.bind(friendsList[position])
        }
    }

}