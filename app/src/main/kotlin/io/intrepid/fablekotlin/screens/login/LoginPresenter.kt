package io.intrepid.fablekotlin.screens.login

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration

class LoginPresenter(view: LoginContract.View, configuration: PresenterConfiguration)
    : BasePresenter<LoginContract.View>(view, configuration), LoginContract.Presenter
