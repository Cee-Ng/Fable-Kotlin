package io.intrepid.fablekotlin.screens.login

import io.intrepid.fablekotlin.base.BaseContract

interface LoginContract {

    interface View : BaseContract.View {
        fun showErrorMessage()

        fun redirectToHomeScreen()
    }

    interface Presenter : BaseContract.Presenter {
        fun getAndStoreUserToken(tempToken: String)

    }
}
