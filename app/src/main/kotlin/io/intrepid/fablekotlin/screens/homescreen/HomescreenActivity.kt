package io.intrepid.fablekotlin.screens.homescreen

import android.content.Intent
import butterknife.OnClick
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.screens.createfable.CreateFableActivity

class HomescreenActivity : BaseMvpActivity<HomescreenContract.Presenter>(), HomescreenContract.View {

    override val layoutResourceId: Int = R.layout.activity_homescreen

    override fun createPresenter(configuration: PresenterConfiguration): HomescreenContract.Presenter =
            HomescreenPresenter(this, configuration)

}