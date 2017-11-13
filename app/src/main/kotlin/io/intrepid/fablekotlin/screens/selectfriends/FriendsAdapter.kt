package io.intrepid.fablekotlin.screens.selectfriends

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import com.squareup.picasso.Picasso
import io.intrepid.fablekotlin.R
import jp.wasabeef.picasso.transformations.CropCircleTransformation

class FriendsAdapter(view: SelectFriendsContract.View, private var presenter: SelectFriendsContract.Presenter) : RecyclerView.Adapter<FriendsAdapter.FriendAdapterViewHolder>() {

    private var context: Context = view as Context
    private val selectionColor = arrayOf("#FFB717", "#F66517", "#FF5B82", "#00796C", "#9292AF")

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): FriendAdapterViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.list_item_select_friend, viewGroup, false)
        return FriendAdapterViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: FriendAdapterViewHolder, position: Int) {
        val friendListItem = presenter.getAllFriends().get(position)

        var letter = '0'
        if (presenter.getLetterMap().containsKey(position)) {
            letter = presenter.getLetterMap()[position]!!
        }
        val separator = presenter.getLetterMap().containsKey(position + 1)
        val selectedIndex = presenter.indexInSelected(friendListItem)
        setViewData(viewHolder, friendListItem.name, friendListItem.image, letter, separator, selectedIndex)
    }

    override fun getItemCount(): Int = presenter.getAllFriends().size

    private fun setViewData(viewHolder: FriendAdapterViewHolder, name: String, imageSrc: String,
                            letter: Char, separator: Boolean, selectedIdx: Int) {

        if (letter.toInt() != 0) {
            viewHolder.letter.text = letter.toString().toUpperCase()
        } else {
            viewHolder.letter.text = ""
        }

        viewHolder.friendName.text = name
        Picasso.with(context).load(imageSrc).transform(CropCircleTransformation())
                .fit()
                .into(viewHolder.friendImage)

        if (separator) {
            viewHolder.separator.visibility = View.VISIBLE
        } else {
            viewHolder.separator.visibility = View.GONE
        }

        if (selectedIdx >= 0) {
            viewHolder.selectedBorder.visibility = View.VISIBLE

            val outline = viewHolder.selectedBorder.background as GradientDrawable
            outline.setColor(Color.parseColor(selectionColor[selectedIdx]))
        } else {
            viewHolder.selectedBorder.visibility = View.GONE
        }
    }

    inner class FriendAdapterViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        @BindView(R.id.letter)
        lateinit var letter: TextView
        @BindView(R.id.friend_name)
        lateinit var friendName: TextView
        @BindView(R.id.friend_image)
        lateinit var friendImage: ImageView
        @BindView(R.id.selected_border)
        lateinit var selectedBorder: ImageView
        @BindView(R.id.separator)
        lateinit var separator: View

        init {
            view.setOnClickListener(this)
            ButterKnife.bind(this, view)
        }

        override fun onClick(v: View) {
            presenter.onClickedFriend(adapterPosition)
            notifyItemChanged(adapterPosition)
        }
    }

    fun updateFriendList() {
        notifyDataSetChanged()
    }
}
