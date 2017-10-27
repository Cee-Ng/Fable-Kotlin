package io.intrepid.fablekotlin.screens.homescreen

import io.intrepid.fablekotlin.base.BaseContract

interface HomescreenContract {
    interface View : BaseContract.View {

        fun gotoCreateFable()
    }

    interface Presenter : BaseContract.Presenter {

        fun onFabClick()
    }

}