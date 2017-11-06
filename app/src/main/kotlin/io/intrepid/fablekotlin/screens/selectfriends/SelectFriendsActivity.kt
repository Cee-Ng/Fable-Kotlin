package io.intrepid.fablekotlin.screens.selectfriends

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.TypedValue
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.BindViews
import butterknife.OnClick
import butterknife.OnTextChanged
import com.squareup.picasso.Picasso
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.models.GetUserFriendsResponse
import io.intrepid.fablekotlin.screens.createfable.CreateFableActivity
import io.intrepid.fablekotlin.screens.createfable.FirstSentence.FirstSentenceActivity
import io.intrepid.fablekotlin.settings.SharedPreferencesManager
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import java.io.Serializable
import java.util.ArrayList

class SelectFriendsActivity : BaseMvpActivity<SelectFriendsContract.Presenter>(), SelectFriendsContract.View {

    @BindView(R.id.friends_recycler_view)
    lateinit var friendsRecyclerView: RecyclerView
    @BindViews(R.id.circle_button1, R.id.circle_button2, R.id.circle_button3, R.id.circle_button4, R.id.circle_button5, R.id.circle_button6)
    lateinit var circleButtons: List<@JvmSuppressWildcards ImageButton>
    @BindViews(R.id.circle_x2, R.id.circle_x3, R.id.circle_x4, R.id.circle_x5, R.id.circle_x6)
    lateinit var circleExitButtons: List<@JvmSuppressWildcards ImageButton>
    @BindView(R.id.no_friends)
    lateinit var noFriendsText: TextView
    @BindView(R.id.progress_spinner)
    lateinit var progressSpinner: ProgressBar
    @BindView(R.id.search_text)
    lateinit var searchText: EditText
    @BindView(R.id.invite_fablers_text)
    lateinit var inviteFablersText: TextView

    private lateinit var friendsAdapter: FriendsAdapter
    private var layoutManager: LinearLayoutManager? = null
    private var fableTitle: String? = null
    private var colorTheme: String? = null
    private var icon: String? = null
    override fun createPresenter(config: PresenterConfiguration): SelectFriendsContract.Presenter = SelectFriendsPresenter(this, config)

    override val layoutResourceId: Int = R.layout.activity_select_friends

    override fun onViewCreated(savedInstanceState: Bundle?) {
        setUpRecyclerView()
        showLoadingSpinner()

        //Get friends, sorts them and, issues callback to updateFriendsList
        presenter.fetchUserFriends()

        //Initialize the circles. The first circle always has the user's face.
        setCircleImage(0, SharedPreferencesManager.getInstance(this).userImage)
        for (i in 1..5) {
            setCircleImage(i, null)
        }

        //Grab fable title and selected friends from create fable screen
        val receivedIntent = intent
        fableTitle = receivedIntent.getStringExtra(CreateFableActivity.FABLE_TITLE)
        colorTheme = receivedIntent.getStringExtra(CreateFableActivity.COLOR_THEME)
        icon = receivedIntent.getStringExtra(CreateFableActivity.ICON)
        val passedInSelectedFriends = receivedIntent
                .getSerializableExtra(CreateFableActivity.SELECTED_FRIENDS) as ArrayList<GetUserFriendsResponse.Friend>
        presenter.setSelectedFriends(passedInSelectedFriends)
    }

    override fun updateFriendsList() {
        friendsAdapter.updateFriendList()
    }

    override fun showErrorMessage() {
        Toast.makeText(this, R.string.toast_error_msg, Toast.LENGTH_SHORT).show()
    }

    override fun setCircleImage(idx: Int, imageSrc: String?) {
        if (idx < 0 || idx >= circleButtons.size) {
            return
        }
        val circleButton = circleButtons[idx]
        if (imageSrc == null) {
            //Reset to empty (plus sign)
            circleButton.setImageResource(0)
            if (idx < 3) {
                circleButton.setBackgroundResource(R.drawable.circle_dark)
            } else {
                circleButton.setBackgroundResource(R.drawable.circle_light)
            }
            circleExitButtons[idx - 1].visibility = View.INVISIBLE
        } else {
            //Add the profile photo and material design ripple
            val typedValue = TypedValue()
            this.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true)
            circleButton.setBackgroundResource(typedValue.resourceId)
            Picasso.with(this).load(imageSrc).transform(CropCircleTransformation())
                    .fit()
                    .into(circleButton)
            if (idx > 0) {
                circleExitButtons[idx - 1].visibility = View.VISIBLE
            }
        }
    }

    override fun showLoadingSpinner() {
        progressSpinner.visibility = View.VISIBLE
    }

    override fun hideLoadingSpinner() {
        progressSpinner.visibility = View.GONE
    }

    override fun showNoFriendsText() {
        noFriendsText.visibility = View.VISIBLE
    }

    override fun showNotEnoughFriendsMessage() {
        val snackbar = Snackbar.make(findViewById(R.id.selectFriendsLayout),
                R.string.invite_more_friends,
                Snackbar.LENGTH_LONG)
        setSnackTextColor(snackbar.view)
        snackbar.show()
    }

    override fun goToFirstSentenceScreen() {
        val intent = Intent(this, FirstSentenceActivity::class.java)
        intent.putExtra(CreateFableActivity.Companion.FABLE_TITLE, fableTitle)
        intent.putExtra(CreateFableActivity.Companion.COLOR_THEME, colorTheme)
        intent.putExtra(CreateFableActivity.Companion.ICON, icon)
        intent.putExtra(CreateFableActivity.Companion.SELECTED_FRIENDS, presenter.getSelectedFriends() as Serializable)
        startActivity(intent)
    }

    override fun goBackToCreateFableScreen() {
        val returnIntent = Intent()
        returnIntent.putExtra(CreateFableActivity.SELECTED_FRIENDS, presenter.getSelectedFriends() as Serializable)
        setResult(Activity.RESULT_OK, returnIntent)
        finish()    }

    override fun scrollToFabler(idx: Int) {
        layoutManager?.scrollToPositionWithOffset(idx, 0)
    }

    private fun setSnackTextColor(snackbarView: View) {
        val snackbarTextId = android.support.design.R.id.snackbar_text
        val textView = snackbarView.findViewById<View>(snackbarTextId) as TextView
        textView.setTextColor(Color.WHITE)
    }

    override fun onBackPressed() {
        goBackToCreateFableScreen()
    }

    @OnTextChanged(value = R.id.search_text, callback = OnTextChanged.Callback.TEXT_CHANGED)
    internal fun onTextChanged() {
        //Get position based on what's entered
        presenter.searchFabler(searchText.text.toString())
    }

    private fun setUpRecyclerView() {
        layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        friendsRecyclerView.layoutManager = layoutManager
        friendsAdapter = FriendsAdapter(this, presenter)
        friendsRecyclerView.adapter = friendsAdapter
    }

    @OnClick(R.id.circle_button2,
            R.id.circle_button3,
            R.id.circle_button4,
            R.id.circle_button5,
            R.id.circle_button6,
            R.id.circle_x2,
            R.id.circle_x3,
            R.id.circle_x4,
            R.id.circle_x5,
            R.id.circle_x6)
    fun onClick(imageButton: ImageButton) {
        when (imageButton.id) {
            R.id.circle_button2, R.id.circle_x2 -> presenter.removeFriendFromSelected(0)
            R.id.circle_button3, R.id.circle_x3 -> presenter.removeFriendFromSelected(1)
            R.id.circle_button4, R.id.circle_x4 -> presenter.removeFriendFromSelected(2)
            R.id.circle_button5, R.id.circle_x5 -> presenter.removeFriendFromSelected(3)
            R.id.circle_button6, R.id.circle_x6 -> presenter.removeFriendFromSelected(4)
        }
    }


}
