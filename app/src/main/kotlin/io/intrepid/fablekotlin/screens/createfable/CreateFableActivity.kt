package io.intrepid.fablekotlin.screens.createfable

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import butterknife.BindView
import butterknife.BindViews
import butterknife.OnClick
import com.squareup.picasso.Picasso
import io.intrepid.fablekotlin.HexColor
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.models.GetUserFriendsResponse
import io.intrepid.fablekotlin.screens.createfable.FirstSentence.FirstSentenceActivity
import io.intrepid.fablekotlin.screens.homescreen.HomescreenActivity
import io.intrepid.fablekotlin.screens.selectfriends.SelectFriendsActivity
import jp.wasabeef.picasso.transformations.CropCircleTransformation
import me.relex.circleindicator.CircleIndicator
import java.io.Serializable
import java.util.*
import kotlin.collections.ArrayList

class CreateFableActivity : BaseMvpActivity<CreateFableContract.Presenter>(), CreateFableContract.View {

    @BindView(R.id.toolbar)
    lateinit var fableToolbar: Toolbar
    @BindView(R.id.enteredFableTitle)
    lateinit var enteredFableTitle: EditText
    @BindView(R.id.pickedColor)
    lateinit var pickedColor: ImageView
    @BindView(R.id.navy)
    lateinit var navy: ImageButton
    @BindView(R.id.forrestGreen)
    lateinit var forrestGreen: ImageButton
    @BindView(R.id.lavender)
    lateinit var lavender: ImageButton
    @BindView(R.id.darkTeal)
    lateinit var lightTeal: ImageButton
    @BindView(R.id.maroon)
    lateinit var maroon: ImageButton
    @BindView(R.id.mustard)
    lateinit var mustard: ImageButton
    @BindView(R.id.orange)
    lateinit var orange: ImageButton
    @BindView(R.id.pink)
    lateinit var pink: ImageButton
    @BindView(R.id.illustrationPager)
    lateinit var pager: ViewPager
    @BindView(R.id.illustrationCircleIcon)
    lateinit var circleIndicator: CircleIndicator

    @BindViews(R.id.circle_button1,
               R.id.circle_button2,
               R.id.circle_button3,
               R.id.circle_button4,
               R.id.circle_button5,
               R.id.circle_button6)
    lateinit var circleButtons: List<@JvmSuppressWildcards ImageButton>

    private lateinit var illustrationsList: List<Int>

    internal var selectedFriends: ArrayList<GetUserFriendsResponse.Friend> = ArrayList()
    val SELECTED_FRIENDS = "selected_friends"
    val FABLE_TITLE = "fable_title"
    val COLOR_THEME = "color_theme"
    val ICON = "icon"
    val PASS_FABLE_INFO = 1

    override val layoutResourceId: Int = R.layout.activity_createfable

    override fun createPresenter(configuration: PresenterConfiguration): CreateFableContract.Presenter =
            CreateFablePresenter(this, configuration)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        illustrationsList = Arrays.asList(R.drawable.artboard_1,
                R.drawable.artboard_2,
                R.drawable.artboard_3,
                R.drawable.artboard_4,
                R.drawable.artboard_5,
                R.drawable.artboard_6,
                R.drawable.artboard_7,
                R.drawable.artboard_8)

        setColorTags()
        setupToolbar()
        setUpIllustrationPager()
    }

    @Suppress("unused")
    @OnClick(R.id.navy, R.id.forrestGreen, R.id.lavender, R.id.darkTeal, R.id.maroon, R.id.mustard, R.id.orange, R.id.pink)
    internal fun onClick(color: ImageButton) {
        val clickedColor = color.tag as HexColor
        pickedColor.setImageResource(R.drawable.ic_cover_photo)
        pickedColor.setColorFilter(Color.parseColor(clickedColor.hexColor))
    }

    @OnClick(R.id.circle_button1, R.id.circle_button2, R.id.circle_button3, R.id.circle_button4, R.id.circle_button5, R.id.circle_button6)
    fun onClickCircleButton() {
        val intent = Intent(this, SelectFriendsActivity::class.java)
        intent.putExtra(FABLE_TITLE, enteredFableTitle.text.toString())
        intent.putExtra(COLOR_THEME, presenter.getColorTheme())
        intent.putExtra(ICON, (pager.currentItem + 1).toString())
        intent.putExtra(SELECTED_FRIENDS, selectedFriends)
        startActivityForResult(intent, PASS_FABLE_INFO)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.create_fable_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val title = enteredFableTitle.text.toString()
        val itemClicked = item.itemId
        when (itemClicked) {
            R.id.createFable -> {
                presenter.onContinueClicked(title)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        startActivity(Intent(this, HomescreenActivity::class.java))
        return false
    }

    // Grabs stored invitees from the other screen, fills the circles with their profile pictures
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PASS_FABLE_INFO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    selectedFriends = data.getSerializableExtra(SELECTED_FRIENDS) as java.util.ArrayList<GetUserFriendsResponse.Friend>
                }
                presenter.setSelectedFriends(selectedFriends)
                for (i in 2..5) {
                    setCircleImage(i, null)
                }
                for (i in selectedFriends.indices) {
                    setCircleImage(i + 1, selectedFriends[i].image)
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                showErrorMessage()
            }
        }
    }

    override fun showTitleShortMessage() {
        val snackbar = Snackbar.make(findViewById(R.id.newFableLayout),
                R.string.no_title_warning,
                Snackbar.LENGTH_SHORT)
        setSnackTextColor(snackbar.view)
        snackbar.show()
    }

    override fun showTitleLongMessage() {
        val snackbar = Snackbar.make(findViewById(R.id.newFableLayout),
                R.string.long_title_warning,
                Snackbar.LENGTH_LONG)
        setSnackTextColor(snackbar.view)
        snackbar.show()
    }

    override fun showNotEnoughFriendsMessage() {
        val snackbar = Snackbar.make(findViewById(R.id.newFableLayout),
                R.string.invite_more_friends,
                Snackbar.LENGTH_LONG)
        setSnackTextColor(snackbar.view)
        snackbar.show()    }

    override fun goToFirstSentenceScreen() {
        val intent = Intent(this, FirstSentenceActivity::class.java)
        intent.putExtra(FABLE_TITLE, enteredFableTitle.text.toString())
        intent.putExtra(COLOR_THEME, presenter.getColorTheme())
        intent.putExtra(ICON, (pager.currentItem + 1).toString())
        intent.putExtra(SELECTED_FRIENDS, presenter.getSelectedFriends() as Serializable)
        startActivity(intent)
    }

    //Set a circle to a profile image
    override fun setCircleImage(idx: Int, imageSrc: String?) {
        if (idx < 0 || idx >= circleButtons.size) {
            return
        }
        val circleButton = circleButtons.get(idx)
        if (imageSrc == null) {
            // Reset to empty (plus sign)
            if (idx < 3) {
                circleButton.setBackgroundResource(R.drawable.circle_dark)
            } else {
                circleButton.setBackgroundResource(R.drawable.circle_light)
            }
            circleButton.setImageResource(R.drawable.plus)
        } else {
            // Add the profile photo and the material design ripple
            val typedValue = TypedValue()
            this.theme.resolveAttribute(android.R.attr.selectableItemBackgroundBorderless, typedValue, true)
            circleButton.setBackgroundResource(typedValue.resourceId)
            Picasso.with(this).load(imageSrc).transform(CropCircleTransformation())
                    .fit()
                    .into(circleButton)
        }
    }

    private fun setColorTags() {
        forrestGreen.tag = HexColor.GREEN
        navy.tag = HexColor.NAVY
        lavender.tag = HexColor.LAVENDER
        lightTeal.tag = HexColor.DARKTEAL
        maroon.tag = HexColor.MAROON
        mustard.tag = HexColor.MUSTARD
        orange.tag = HexColor.ORANGE
        pink.tag = HexColor.PINK
    }

    private fun setupToolbar() {
        setSupportActionBar(fableToolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.close)
            setTitle(R.string.createFable)
        }
    }

    private fun setUpIllustrationPager() {
        pager.adapter = IllustrationPagerAdapter(this, illustrationsList)
        circleIndicator.setViewPager(pager)
    }

    private fun setSnackTextColor(snackbarView: View) {
        val snackbarTextId = android.support.design.R.id.snackbar_text
        val textView = snackbarView.findViewById<View>(snackbarTextId) as TextView
        textView.setTextColor(Color.WHITE)
    }

    private fun showErrorMessage() {
        Toast.makeText(this, R.string.failedToast, Toast.LENGTH_SHORT).show()
    }

    companion object {
        val SELECTED_FRIENDS = "selected_friends"
        val FABLE_TITLE = "fable_title"
        val COLOR_THEME = "color_theme"
        val ICON = "icon"
        val PASS_FABLE_INFO = 1
    }

}
