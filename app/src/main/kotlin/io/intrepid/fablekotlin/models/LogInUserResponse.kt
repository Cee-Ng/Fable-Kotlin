package io.intrepid.fablekotlin.models

import com.google.gson.annotations.SerializedName

class LogInUserResponse {
    @SerializedName("authentication")
    lateinit var authentication: Authentication

    fun LogInUserResponse(authToken: String, id: String, image: String, name: String) {
        this.authentication = Authentication()
        this.authentication.user = User()
        this.authentication.authToken = authToken
        this.authentication.user!!.id = id
        this.authentication.user!!.image = image
        this.authentication.user!!.name = name
    }

    inner class Authentication {
        @SerializedName("auth_token")
        var authToken: String? = null
        @SerializedName("user")
        var user: User? = null
    }

    inner class User {
        @SerializedName("id")
        var id: String? = null
        @SerializedName("image")
        var image: String? = null
        @SerializedName("name")
        var name: String? = null
    }
}
