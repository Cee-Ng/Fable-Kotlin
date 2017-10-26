package io.intrepid.fablekotlin.screens.homescreen

import android.content.Intent
import butterknife.OnClick
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration

class HomescreenActivity : BaseMvpActivity<HomescreenContract.Presenter>(), HomescreenContract.View {

    override val layoutResourceId: Int = R.layout.activity_homescreen

    override fun createPresenter(configuration: PresenterConfiguration): HomescreenContract.Presenter {
        return HomescreenPresenter(this, configuration)
    }

/*    @OnClick(R.id.example1_button)
    internal fun onButtonClick() {
        presenter.onButtonClick()
    }

    override fun gotoExample2() {
        val intent = Intent(this, Example2Activity::class.java)
        startActivity(intent)
    }*/
}