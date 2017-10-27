package io.intrepid.fablekotlin.screens.createfable

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import butterknife.BindView
import butterknife.OnClick
import io.intrepid.fablekotlin.HexColor
import io.intrepid.fablekotlin.R
import io.intrepid.fablekotlin.base.BaseMvpActivity
import io.intrepid.fablekotlin.base.PresenterConfiguration

class CreateFableActivity : BaseMvpActivity<CreateFableContract.Presenter>(), CreateFableContract.View {

    @BindView(R.id.pickedColor)
    lateinit var pickedColor : ImageView
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

    override val layoutResourceId: Int = R.layout.activity_createfable

    override fun createPresenter(configuration: PresenterConfiguration): CreateFableContract.Presenter =
            CreateFablePresenter(this, configuration)

    override fun onViewCreated(savedInstanceState: Bundle?) {
        super.onViewCreated(savedInstanceState)
        setColorTags()
    }

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
}
