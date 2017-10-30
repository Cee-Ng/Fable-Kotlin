package io.intrepid.fablekotlin.models

import com.google.gson.annotations.SerializedName

class LogInUserRequest {
    @SerializedName("access_token")
    private lateinit var accessToken : String

    fun LogInUserRequest(accessToken: String) {
        this.accessToken = accessToken
    }

}
