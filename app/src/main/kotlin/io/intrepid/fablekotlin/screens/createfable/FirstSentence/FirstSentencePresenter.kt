package io.intrepid.fablekotlin.screens.createfable.FirstSentence

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration

class FirstSentencePresenter(view: FirstSentenceContract.View, configuration: PresenterConfiguration)
    : BasePresenter<FirstSentenceContract.View>(view, configuration), FirstSentenceContract.Presenter
