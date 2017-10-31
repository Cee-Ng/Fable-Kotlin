package io.intrepid.fablekotlin.models

import com.google.gson.annotations.SerializedName

class LogInUserRequest(token: String) {
    @SerializedName("access_token")
    private var accessToken : String = token

}
