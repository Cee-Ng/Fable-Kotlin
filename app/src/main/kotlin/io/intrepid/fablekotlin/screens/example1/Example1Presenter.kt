package io.intrepid.fablekotlin.screens.example1

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration

internal class Example1Presenter(view: Example1Contract.View, configuration: PresenterConfiguration)
    : BasePresenter<Example1Contract.View>(view, configuration), Example1Contract.Presenter {

    override fun onButtonClick() {
        view?.gotoExample2()
    }
}
