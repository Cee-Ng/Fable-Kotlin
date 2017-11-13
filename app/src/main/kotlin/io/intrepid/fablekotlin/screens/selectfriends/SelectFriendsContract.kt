package io.intrepid.fablekotlin.screens.selectfriends

import io.intrepid.fablekotlin.base.BaseContract
import io.intrepid.fablekotlin.models.GetUserFriendsResponse

interface SelectFriendsContract {
    interface View : BaseContract.View {
        fun updateFriendsList()

        fun showErrorMessage()

        fun setCircleImage(idx: Int, imageSrc: String?)

        fun showLoadingSpinner()

        fun hideLoadingSpinner()

        fun showNoFriendsText()

        fun showNotEnoughFriendsMessage()

        fun goToFirstSentenceScreen()

        fun goBackToCreateFableScreen()

        fun scrollToFabler(idx: Int)
    }

    interface Presenter : BaseContract.Presenter {

        fun fetchUserFriends()

        fun onClickedFriend(idx: Int)

        fun onClickedCheck(fableTitle: String)

        fun setAllFriends(allFriends: List<GetUserFriendsResponse.Friend>)

        fun getAllFriends(): List<GetUserFriendsResponse.Friend>

        fun getSelectedFriends(): List<GetUserFriendsResponse.Friend>

        fun getLetterMap(): Map<Int, Char>

        fun setSelectedFriends(selectedFriends: List<GetUserFriendsResponse.Friend>)

        fun removeFriendFromSelected(idx: Int)

        fun indexInSelected(friend: GetUserFriendsResponse.Friend): Int

        fun searchFabler(text: String)
    }
}
