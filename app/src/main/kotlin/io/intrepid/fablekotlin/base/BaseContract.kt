package io.intrepid.fablekotlin.base

interface BaseContract {

    interface View {
    }

    interface Presenter {
        fun bindView(view: View)

        fun unbindView()

        fun onViewCreated()

        fun onViewDestroyed()
    }
}
