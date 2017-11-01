package io.intrepid.fablekotlin.screens.login

import android.content.Intent
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.screens.homescreen.HomescreenActivity
import java.util.*

class LoginActivity : BaseMvpActivity<LoginContract.Presenter>(), LoginContract.View {

    @BindView(R.id.title_text)
    lateinit var titleText: TextView
    @BindView(R.id.login_button)
    lateinit var loginButton: LoginButton

    private var callbackManager: CallbackManager? = null

    override fun createPresenter(config: PresenterConfiguration): LoginContract.Presenter = LoginPresenter(this, config)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        styleTitleText()
        setupFacebookLoginButton()
    }

    override val layoutResourceId: Int = R.layout.activity_login

    private fun setupFacebookLoginButton() {
        callbackManager = CallbackManager.Factory.create()
        loginButton.setReadPermissions(Arrays.asList("email", "user_about_me", "user_friends"))
        loginButton.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        presenter.getAndStoreUserToken(loginResult.accessToken.token)
                    }

                    override fun onCancel() {}

                    override fun onError(error: FacebookException) {}
                })
    }

    private fun styleTitleText() {
        val titleColorSpan = SpannableStringBuilder("FABLE")
        val titleColors = intArrayOf(ContextCompat.getColor(this, R.color.titleLetterF),
                ContextCompat.getColor(this, R.color.titleLetterA),
                ContextCompat.getColor(this, R.color.titleLetterB),
                ContextCompat.getColor(this, R.color.titleLetterL),
                ContextCompat.getColor(this, R.color.titleLetterE))
        for (letter in 0..4) {
            titleColorSpan.setSpan(ForegroundColorSpan(titleColors[letter]),
                    letter,
                    letter + 1,
                    Spannable.SPAN_INCLUSIVE_INCLUSIVE)
        }
        titleText.text = titleColorSpan
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }

    override fun redirectToHomeScreen() {
        startActivity(Intent(this, HomescreenActivity::class.java))
    }

    @OnClick(R.id.custom_login_button)
    fun onClick() {
        loginButton.performClick()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, R.string.toast_error_msg, Toast.LENGTH_SHORT).show()
    }

    companion object {

        val isLoggedInToFacebook: Boolean
            get() {
                val accessToken = AccessToken.getCurrentAccessToken()
                return accessToken != null
            }
    }
}
