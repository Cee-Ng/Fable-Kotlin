package io.intrepid.fablekotlin.screens.login

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.models.LogInUserRequest
import io.intrepid.fablekotlin.models.LogInUserResponse

class LoginPresenter(view: LoginContract.View, configuration: PresenterConfiguration)
    : BasePresenter<LoginContract.View>(view, configuration), LoginContract.Presenter {

    override fun getAndStoreUserToken(tempToken: String) {

        val logInUserObservable = restApi.logInUser(LogInUserRequest(tempToken))

        val disposable = logInUserObservable
                .subscribeOnIoObserveOnUi()
                .subscribe({ logInUserResponse: LogInUserResponse ->
                        userSettings.token = (logInUserResponse.authentication.authToken!!)
                        userSettings.userId = (logInUserResponse.authentication.user!!.id!!)
                        userSettings.userImage = (logInUserResponse.authentication.user!!.image!!)
                        userSettings.userName = (logInUserResponse.authentication.user!!.name!!)
                        view?.redirectToHomeScreen()
                }, { error(view?.showErrorMessage()!!) })
        disposables.add(disposable)
    }



}
