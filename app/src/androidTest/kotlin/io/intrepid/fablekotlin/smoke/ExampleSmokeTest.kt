package io.intrepid.fablekotlin.smoke

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.filters.SmallTest
import android.support.test.rule.ActivityTestRule
import io.intrepid.fablekotlin.InstrumentationTestApplication
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.rest.TestRestClient
import io.intrepid.fablekotlin.rules.MockServerRule
import io.intrepid.fablekotlin.screens.example1.Example1Activity
import io.intrepid.fablekotlin.settings.UserSettings
import io.intrepid.fablekotlin.testutils.BaseUiTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`

@SmallTest
class ExampleSmokeTest : BaseUiTest() {
    @Rule
    @JvmField
    val mockServerRule = MockServerRule()
    @Rule
    @JvmField
    val activityTestRule = ActivityTestRule(Example1Activity::class.java, true, false)

    @Mock
    internal lateinit var mockUserSettings: UserSettings

    @Before
    fun setUp() {
        InstrumentationTestApplication.overrideRestApi(TestRestClient.getRestApi(mockServerRule))
        InstrumentationTestApplication.overrideUserSettings(mockUserSettings)
    }

    @Test
    fun smokeTest() {
        activityTestRule.launchActivity(null)
        mockServerRule.enqueueResponse(io.intrepid.fablekotlin.debug.test.R.raw.mock_ip)

        `when`(mockUserSettings.lastIp).thenReturn("127.0.0.2")

        onView(withId(R.id.example1_button)).perform(click())
        onView(withId(R.id.example2_current_ip)).check(matches(withText("Your current Ip address is 127.0.0.1")))
        onView(withId(R.id.example2_previous_ip)).check(matches(withText("Your previous Ip address is 127.0.0.2")))
    }
}
