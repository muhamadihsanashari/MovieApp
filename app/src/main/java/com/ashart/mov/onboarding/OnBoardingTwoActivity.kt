package com.ashart.mov.onboarding

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ashart.mov.R
import kotlinx.android.synthetic.main.activity_on_boarding_two.*

class OnBoardingTwoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_two)

        btn_home.setOnClickListener {
            finishAffinity()

            val intent = Intent(this@OnBoardingTwoActivity, OnBoardingThreeActivity::class.java)
            startActivity(intent)
        }

    }
}
