package com.emengsoft.orderingapp.views.main

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import com.emengsoft.orderingapp.R
import kotlinx.android.synthetic.main.activity_loading.*
import org.jetbrains.anko.intentFor

/**
 * Created by Fajar Agung Pramana on 06 January 2019
 * - Emengsoft Studio
 * - Indonesia
 */
 
class LoadingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        // call function class
        setAnimate()
        startAnimate()
        endLoading()

    }

    // initially for animate
    private fun setAnimate() {

        textView1.alpha = 0F
        textView1.translationX = 400F

        textView2.alpha = 0F
        textView2.translationX = 400F

        textView3.alpha = 0F
        textView3.translationX = 400F

    }

    // run animate object on layout
    private fun startAnimate() {

        textView1.animate().alpha(1F).translationX(0F).setDuration(600).start()
        textView2.animate().alpha(1F).translationX(0F).setDuration(600).setStartDelay(300).start()
        textView3.animate().alpha(1F).translationX(0F).setDuration(600).setStartDelay(600).start()

    }

    // stop loading and move to main activity
    private fun endLoading() {

        val LOADING_TIME = 3000L

        Handler().postDelayed({

            startActivity(intentFor<MainActivity>()
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        }, LOADING_TIME)

    }

}