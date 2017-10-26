package io.intrepid.fablekotlin.rest

import io.intrepid.fablekotlin.models.IpModel
import io.reactivex.Single
import retrofit2.http.GET

interface RestApi {
    @GET("/?format=json")
    fun getMyIp(): Single<IpModel>
}
