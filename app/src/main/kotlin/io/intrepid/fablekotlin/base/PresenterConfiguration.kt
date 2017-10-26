package io.intrepid.fablekotlin.base

import io.intrepid.fablekotlin.logging.CrashReporter
import io.intrepid.fablekotlin.rest.RestApi
import io.intrepid.fablekotlin.settings.UserSettings
import io.reactivex.Scheduler

/**
 * Wrapper class for common dependencies that all presenters are expected to have
 */
open class PresenterConfiguration(open val ioScheduler: Scheduler,
                                  open val uiScheduler: Scheduler,
                                  val userSettings: UserSettings,
                                  val restApi: RestApi,
                                  val crashReporter: CrashReporter)
