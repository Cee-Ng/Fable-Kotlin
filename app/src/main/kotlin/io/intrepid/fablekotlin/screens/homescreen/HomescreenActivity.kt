package io.intrepid.fablekotlin.screens.homescreen

import android.content.Intent
import android.os.Bundle
import butterknife.OnClick
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.screens.createfable.CreateFableActivity
import io.intrepid.fablekotlin.screens.login.LoginActivity
import io.intrepid.fablekotlin.settings.SharedPreferencesManager


class HomescreenActivity : BaseMvpActivity<HomescreenContract.Presenter>(), HomescreenContract.View {

    override val layoutResourceId: Int = R.layout.activity_homescreen

    override fun createPresenter(configuration: PresenterConfiguration): HomescreenContract.Presenter =
            HomescreenPresenter(this, configuration)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)

        if ((!getLoggedInStatus()) ||  SharedPreferencesManager.getInstance(this).token == null) run { startActivity(Intent(this, LoginActivity::class.java)) }
    }

    private fun getLoggedInStatus(): Boolean {
        return LoginActivity.isLoggedInToFacebook

    }

    @OnClick(R.id.go_to_new_fable_btn)
    internal fun onFabClick() {
        presenter.onFabClick()
    }

    override fun gotoCreateFable() {
        val intent = Intent(this, CreateFableActivity::class.java)
        startActivity(intent)
    }

}
