package io.intrepid.fablekotlin.screens.example1

import io.intrepid.fablekotlin.base.BaseContract

interface Example1Contract {
    interface View : BaseContract.View {

        fun gotoExample2()
    }

    interface Presenter : BaseContract.Presenter {

        fun onButtonClick()
    }
}
