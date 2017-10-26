package io.intrepid.fablekotlin.testutils

import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.logging.CrashReporter
import io.intrepid.fablekotlin.rest.RestApi
import io.intrepid.fablekotlin.settings.UserSettings
import io.reactivex.schedulers.TestScheduler
import org.mockito.Mockito

class TestPresenterConfiguration private constructor(userSettings: UserSettings, restApi: RestApi, crashReporter: CrashReporter)
    : PresenterConfiguration(TestScheduler(), TestScheduler(), userSettings, restApi, crashReporter) {

    override val ioScheduler: TestScheduler get() = super.ioScheduler as TestScheduler
    override val uiScheduler: TestScheduler get() = super.uiScheduler as TestScheduler

    companion object {
        fun createTestConfiguration(): TestPresenterConfiguration {
            val userSettings = Mockito.mock(UserSettings::class.java)
            val restApi = Mockito.mock(RestApi::class.java)
            val crashReporter = Mockito.mock(CrashReporter::class.java)
            return TestPresenterConfiguration(userSettings, restApi, crashReporter)
        }
    }

    fun triggerRxSchedulers() {
        ioScheduler.triggerActions()
        uiScheduler.triggerActions()
    }
}
