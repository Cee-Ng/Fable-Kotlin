package io.intrepid.fablekotlin.rest

import io.intrepid.fablekotlin.models.IpModel
import io.intrepid.fablekotlin.models.LogInUserRequest
import io.intrepid.fablekotlin.models.LogInUserResponse
import io.reactivex.Observable
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RestApi {
    @GET("/?format=json")
    fun getMyIp(): Single<IpModel>

    @POST("users")
    fun logInUser(@Body logInUserRequest: LogInUserRequest): Observable<LogInUserResponse>
}
