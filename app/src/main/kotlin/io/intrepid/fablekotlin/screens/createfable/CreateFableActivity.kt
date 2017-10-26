package io.intrepid.fablekotlin.screens.createfable

import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration

class CreateFableActivity : BaseMvpActivity<CreateFableContract.Presenter>(), CreateFableContract.View {

    override val layoutResourceId: Int = R.layout.activity_createfable

    override fun createPresenter(configuration: PresenterConfiguration): CreateFableContract.Presenter {
        return CreateFablePresenter(this, configuration)
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