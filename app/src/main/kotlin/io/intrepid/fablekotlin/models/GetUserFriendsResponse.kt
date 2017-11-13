package io.intrepid.fablekotlin.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class GetUserFriendsResponse {

    @SerializedName("friends")
    lateinit var friends: List<Friend>

    class Friend(@field:SerializedName("id")
                 var id: String,
                 @field:SerializedName("image")
                 var image: String,
                 @field:SerializedName("name")
                 var name: String) : Serializable
}
