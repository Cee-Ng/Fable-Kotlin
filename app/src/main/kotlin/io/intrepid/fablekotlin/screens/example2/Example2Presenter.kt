package io.intrepid.fablekotlin.screens.example2

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.utils.RxUtils
import io.reactivex.functions.Consumer

internal class Example2Presenter(view: Example2Contract.View, configuration: PresenterConfiguration)
    : BasePresenter<Example2Contract.View>(view, configuration), Example2Contract.Presenter {

    override fun onViewCreated() {
        super.onViewCreated()

        val disposable = restApi.getMyIp()
                .subscribeOnIoObserveOnUi()
                .subscribe(Consumer {
                    val ip = it.ip
                    view?.showCurrentIpAddress(ip)
                    userSettings.lastIp = ip
                }, RxUtils.logError())
        disposables.add(disposable)

        val lastIp = userSettings.lastIp
        if (lastIp.isEmpty()) {
            view?.hidePreviousIpAddress()
        } else {
            view?.showPreviousIpAddress(lastIp)
        }
    }
}
