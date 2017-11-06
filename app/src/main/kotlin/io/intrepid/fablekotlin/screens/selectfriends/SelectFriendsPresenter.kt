package io.intrepid.fablekotlin.screens.selectfriends

import io.intrepid.fablekotlin.base.BasePresenter
import io.intrepid.fablekotlin.base.PresenterConfiguration
import io.intrepid.fablekotlin.models.GetUserFriendsResponse
import java.util.*

class SelectFriendsPresenter(view: SelectFriendsContract.View, configuration: PresenterConfiguration)
    : BasePresenter<SelectFriendsContract.View>(view, configuration), SelectFriendsContract.Presenter{

    private lateinit var allFriends: List<GetUserFriendsResponse.Friend>
    private lateinit var selectedFriends: MutableList<GetUserFriendsResponse.Friend>
    private val letterMap = HashMap<Int, Char>()

    override fun onViewCreated() {
        super.onViewCreated()
        allFriends = ArrayList()
        selectedFriends = ArrayList()
    }

    override fun fetchUserFriends() {
        val getUserFriendsResponseObservable = restApi.getUserFriends(userSettings.token)

        val disposable = getUserFriendsResponseObservable
                .subscribeOnIoObserveOnUi()
                .subscribe({ getUserFriendsResponse ->
                    allFriends = getUserFriendsResponse.friends
                    if (allFriends.isEmpty()) {
                        view?.showNoFriendsText()
                    }
                    sortAllFriends()
                    view?.updateFriendsList()
                    view?.hideLoadingSpinner()
                }, {
                    view?.showErrorMessage()
                    view?.hideLoadingSpinner()
                })
        disposables.add(disposable)    }

    override fun onClickedFriend(idx: Int) {
        if (selectedFriends.size >= 5 || idx >= allFriends.size) {
            return
        }
        val toAdd = allFriends[idx]

        if (indexInSelected(toAdd) == -1) {
            selectedFriends.add(toAdd)
            view?.setCircleImage(selectedFriends.size, toAdd.image)
        }    }

    override fun onClickedCheck(fableTitle: String) {
        val enoughFriends = selectedFriends.size >= 2
        val validTitle = fableTitle.length in 1..50

        if (!enoughFriends) {
            view?.showNotEnoughFriendsMessage()
        } else if (!validTitle) {
            //Go back so we can enter a title
            view?.goBackToCreateFableScreen()
        } else {
            view?.goToFirstSentenceScreen()
        }    }

    override fun setAllFriends(allFriends: List<GetUserFriendsResponse.Friend>) {
        this.allFriends = allFriends
    }

    override fun getAllFriends(): List<GetUserFriendsResponse.Friend> {
        return allFriends
    }

    override fun getSelectedFriends(): List<GetUserFriendsResponse.Friend> {
        return selectedFriends
    }

    override fun getLetterMap(): Map<Int, Char> {
        return letterMap
    }

    override fun setSelectedFriends(selectedFriends: List<GetUserFriendsResponse.Friend>) {
        this.selectedFriends = selectedFriends as MutableList<GetUserFriendsResponse.Friend>
        for (i in selectedFriends.indices) {
            val friend = selectedFriends[i]
            view?.setCircleImage(i + 1, friend.image)
        }
    }

    override fun removeFriendFromSelected(idx: Int) {
        if (idx >= selectedFriends.size) {
            return
        }
        selectedFriends.removeAt(idx)
        for (i in 0..4) {
            if (i < selectedFriends.size) {
                view?.setCircleImage(i + 1, selectedFriends[i].image)
            } else {
                view?.setCircleImage(i + 1, null)
            }
        }
        view?.updateFriendsList()    }

    override fun indexInSelected(selectedFriend: GetUserFriendsResponse.Friend): Int {
        for (i in selectedFriends.indices) {
            val friend = selectedFriends[i]
            if (selectedFriend.id == friend.id) {
                return i
            }
        }
        return -1
    }

    override fun searchFabler(text: String) {
        if (text.isEmpty()) {
            return
        }
        for (i in allFriends.indices) {
            val friend = allFriends[i]
            if (text.toLowerCase().compareTo(friend.name.toLowerCase()) < 0) {
                view?.scrollToFabler(i)
                return
            }
        }
        view?.scrollToFabler(allFriends.size - 1)    }

    private fun sortAllFriends() {
        // Maintain alphabetical ordering
        Collections.sort(allFriends) { f1, f2 -> f1.name.compareTo(f2.name) }

        // Build map from letters to list positions
        var prevLetter = ('a' - 1).toChar()
        for (i in allFriends.indices) {
            val friend = allFriends[i]
            val nameStartsWith = friend.name.toLowerCase()[0]
            if (nameStartsWith > prevLetter) {
                letterMap.put(i, nameStartsWith)
            }
            prevLetter = nameStartsWith
        }
    }

}
