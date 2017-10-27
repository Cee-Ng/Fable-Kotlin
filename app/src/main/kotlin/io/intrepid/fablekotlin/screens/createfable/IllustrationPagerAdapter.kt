package io.intrepid.fablekotlin.screens.createfable

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import io.intrepid.fablekotlin.R

class IllustrationPagerAdapter(private val context: Context, private val illustrations: List<Int>) : PagerAdapter() {

    override fun getCount(): Int = illustrations.size

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(view: ViewGroup, position: Int): View {
        val inflater = LayoutInflater.from(view.context)
        val illustrationLayout = inflater.inflate(R.layout.illustration_picker_slide, view, false)
        val currentImage : ImageView = illustrationLayout.findViewById(R.id.illustration)
        currentImage.setImageResource(illustrations[position])
        view.addView(illustrationLayout, 0)
        return illustrationLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean = view == `object`

}
