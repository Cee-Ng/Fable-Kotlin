package io.intrepid.fablekotlin.screens.homescreen

import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration

class HomescreenActivity : BaseMvpActivity<HomescreenContract.Presenter>(), HomescreenContract.View {

    override val layoutResourceId: Int = R.layout.activity_homescreen

    override fun createPresenter(configuration: PresenterConfiguration): HomescreenContract.Presenter {
        return HomescreenPresenter(this, configuration)
    }

}