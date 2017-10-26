package io.intrepid.fablekotlin

import android.app.Application
import com.squareup.leakcanary.LeakCanary
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.logging.CrashlyticsReporter
import io.intrepid.fablekotlin.logging.TimberConfig
import io.intrepid.fablekotlin.rest.RetrofitClient
import io.intrepid.fablekotlin.settings.SharedPreferencesManager
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import uk.co.chrisjenx.calligraphy.CalligraphyConfig

open class FableKotlinApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        setupLeakCanary()

        CrashlyticsReporter.init(this)

        TimberConfig.init(CrashlyticsReporter)

        initCalligraphy()
    }

    protected open fun setupLeakCanary() {
        LeakCanary.install(this)
    }

    private fun initCalligraphy() {
        val config = CalligraphyConfig.Builder()
                .setDefaultFontPath(getString(R.string.Roboto_Regular))
                .setFontAttrId(R.attr.fontPath)
                .build()
        CalligraphyConfig.initDefault(config)
    }

    open fun getPresenterConfiguration(): PresenterConfiguration {
        return PresenterConfiguration(
                Schedulers.io(),
                AndroidSchedulers.mainThread(),
                SharedPreferencesManager.getInstance(this),
                RetrofitClient.restApi,
                CrashlyticsReporter
        )
    }
}
