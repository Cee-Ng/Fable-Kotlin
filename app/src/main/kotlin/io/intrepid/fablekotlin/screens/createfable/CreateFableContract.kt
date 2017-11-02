package io.intrepid.fablekotlin.screens.createfable

import io.intrepid.fablekotlin.HexColor
import io.intrepid.fablekotlin.base.BaseContract
import io.intrepid.fablekotlin.models.GetUserFriendsResponse

interface CreateFableContract {
    interface View : BaseContract.View {
        fun showTitleShortMessage()

        fun showTitleLongMessage()

        fun showNotEnoughFriendsMessage()

        fun goToFirstSentenceScreen()

        fun setCircleImage(idx: Int, imageSrc: String?)
    }

    interface Presenter : BaseContract.Presenter {
        fun onContinueClicked(title: String)

        fun setColorTheme(color: HexColor)

        fun getColorTheme(): String

        fun getSelectedFriends(): List<GetUserFriendsResponse.Friend>

        fun setSelectedFriends(selectedFriends: List<GetUserFriendsResponse.Friend>)
    }
}
