package io.intrepid.fablekotlin.rest

import io.intrepid.fablekotlin.rules.MockServerRule

object TestRestClient {
    fun getRestApi(mockServer: MockServerRule): RestApi {
        return RetrofitClient.getTestApi(mockServer.serverUrl)
    }
}
