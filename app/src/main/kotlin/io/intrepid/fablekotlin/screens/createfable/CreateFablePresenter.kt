package io.intrepid.fablekotlin.screens.createfable

import android.app.Activity
import android.content.Intent
import io.intrepid.fablekotlin.HexColor
import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.models.GetUserFriendsResponse
import io.intrepid.fablekotlin.screens.createfable.CreateFableActivity.Companion.SELECTED_FRIENDS
import java.util.ArrayList

class CreateFablePresenter(view: CreateFableContract.View, configuration: PresenterConfiguration)
    : BasePresenter<CreateFableContract.View>(view, configuration), CreateFableContract.Presenter {

    private val PASS_FABLE_INFO = 1
    private val MAX_LENGTH = 50
    private lateinit var colorTheme: String
    private lateinit var selectedFriends: List<GetUserFriendsResponse.Friend>

    override fun onViewCreated() {
        super.onViewCreated()
        setColorTheme(HexColor.DARKTEAL)
        selectedFriends = ArrayList()
    }

    override fun onContinueClicked(title: String) {
        if (!isTitleValid(title)) {
            callErrorMessage(title)
        } else if (selectedFriends.size < 2) {
            view?.showNotEnoughFriendsMessage()
        } else {
            view?.goToFirstSentenceScreen()
        }
    }

    private fun isTitleValid(title: String): Boolean = !title.isEmpty() && title.length <= MAX_LENGTH

    private fun callErrorMessage(title: String) {
        if (title.isEmpty()) {
            view?.showTitleShortMessage()
        } else if (title.length > 50) {
            view?.showTitleLongMessage()
        }
    }

    override fun setColorTheme(color: HexColor) {
        colorTheme = color.hexColor
    }

    override fun getColorTheme(): String = colorTheme

    override fun getSelectedFriends(): List<GetUserFriendsResponse.Friend> = selectedFriends

    override fun setSelectedFriends(selectedFriends: List<GetUserFriendsResponse.Friend>) {
        this.selectedFriends = selectedFriends

        // Overwrite the circle images with the list of selected friends
        for (i in 2..5) {
            view?.setCircleImage(i, null)
        }
        for (i in selectedFriends.indices) {
            view?.setCircleImage(i + 1, selectedFriends[i].image)
        }
    }

    override fun returnFromPage(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PASS_FABLE_INFO) {
            if (resultCode == Activity.RESULT_OK) {
                if (data != null) {
                    selectedFriends = data.getSerializableExtra(SELECTED_FRIENDS) as ArrayList<GetUserFriendsResponse.Friend>
                }
                for (i in 2..5) {
                    view?.setCircleImage(i, null)
                }
                for (i in selectedFriends.indices) {
                    view?.setCircleImage(i + 1, selectedFriends[i].image)
                }
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                view?.showErrorMessage()
            }
        }
    }

}
