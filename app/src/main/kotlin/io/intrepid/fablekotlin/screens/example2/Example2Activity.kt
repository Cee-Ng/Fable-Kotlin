package io.intrepid.fablekotlin.screens.example2

import android.content.Intent
import android.support.v4.app.Fragment

import io.intrepid.fablekotlin.base.BaseFragmentActivity

class Example2Activity : BaseFragmentActivity() {

    override fun createFragment(intent: Intent?): Fragment {
        return Example2Fragment()
    }
}
