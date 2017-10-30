package io.intrepid.fablekotlin.screens.login

import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration

class LoginActivity : BaseMvpActivity<LoginContract.Presenter>(), LoginContract.View  {

    override val layoutResourceId: Int = R.layout.activity_homescreen

    override fun createPresenter(configuration: PresenterConfiguration): LoginContract.Presenter =
            LoginPresenter(this, configuration)
}
