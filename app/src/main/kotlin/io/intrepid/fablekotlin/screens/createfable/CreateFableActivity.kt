package io.intrepid.fablekotlin.screens.createfable

import android.graphics.Color
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.ImageButton
import android.widget.ImageView
import butterknife.BindView
import butterknife.OnClick
import io.intrepid.fablekotlin.HexColor
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration
import me.relex.circleindicator.CircleIndicator
import java.util.*

class CreateFableActivity : BaseMvpActivity<CreateFableContract.Presenter>(), CreateFableContract.View {

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

    private lateinit var illustrationsList: List<Int>

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

        setUpIllustrationPager()
    }

    @Suppress("unused")
    @OnClick(R.id.navy, R.id.forrestGreen, R.id.lavender, R.id.darkTeal, R.id.maroon, R.id.mustard, R.id.orange, R.id.pink)
    internal fun onClick(color: ImageButton) {
        val clickedColor = color.tag as HexColor
        pickedColor.setImageResource(R.drawable.ic_cover_photo)
        pickedColor.setColorFilter(Color.parseColor(clickedColor.hexColor))
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

    private fun setUpIllustrationPager() {
        pager.adapter = IllustrationPagerAdapter(this, illustrationsList)
        circleIndicator.setViewPager(pager)
    }
}
