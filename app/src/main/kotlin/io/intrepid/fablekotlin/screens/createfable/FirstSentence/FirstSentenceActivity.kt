package io.intrepid.fablekotlin.screens.createfable.FirstSentence

import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.screens.createfable.CreateFablePresenter

class FirstSentenceActivity : BaseMvpActivity<FirstSentenceContract.Presenter>(), FirstSentenceContract.View {
    override val layoutResourceId: Int = R.layout.activity_first_sentence

    override fun createPresenter(configuration: PresenterConfiguration): FirstSentenceContract.Presenter =
            FirstSentencePresenter(this, configuration)

}
