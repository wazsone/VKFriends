package com.example.vk_friends.model

import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import org.json.JSONObject

class FriendModel(
    val id: Int = 0,
    var name: String,
    var surname: String,
    var city: String,
    var avatar: String,
    var isOnline: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readString() as String,
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(name)
        parcel.writeString(surname)
        parcel.writeString(city)
        parcel.writeString(avatar)
        parcel.writeByte(if (isOnline) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FriendModel> {
        private val TAG = FriendModel::class.java.simpleName
        override fun createFromParcel(parcel: Parcel): FriendModel {
            return FriendModel(parcel)
        }

        override fun newArray(size: Int): Array<FriendModel?> {
            return arrayOfNulls(size)
        }

        fun parse(json: JSONObject): FriendModel {
            val jsonCity = try {
                JSONObject(json.optString("city", ""))
            } catch (e: Exception) {
                Log.e(TAG, e.toString())
                JSONObject()
            }
            return FriendModel(
                id = json.optInt("id", 0),
                name = json.optString("first_name", ""),
                surname = json.optString("last_name", ""),
                city = jsonCity.optString("title", ""),
                avatar = json.optString("photo_200", ""),
                isOnline = json.optInt("online", 0) == 1
            )
        }

    }

}