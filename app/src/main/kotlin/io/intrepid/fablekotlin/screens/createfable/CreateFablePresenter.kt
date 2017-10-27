package io.intrepid.fablekotlin.screens.createfable

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration

class CreateFablePresenter (view: CreateFableContract.View, configuration: PresenterConfiguration)
    : BasePresenter<CreateFableContract.View>(view, configuration), CreateFableContract.Presenter
