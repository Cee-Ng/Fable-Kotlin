package io.intrepid.fablekotlin.screens.homescreen

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration

class HomescreenPresenter(view: HomescreenContract.View, configuration: PresenterConfiguration)
    : BasePresenter<HomescreenContract.View>(view, configuration), HomescreenContract.Presenter {
    
    override fun onFabClick() {
        view?.gotoCreateFable()
    }


}
