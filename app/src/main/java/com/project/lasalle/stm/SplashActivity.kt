@file:Suppress("DEPRECATION")

package com.project.lasalle.stm

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity


private const val SPLASH_SCREEN = 4000

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_splash)
        init()
    }

    private fun init(){

        val imgStmLogo : ImageView = findViewById(R.id.stm_logo)
        val imgStmNameLogo : ImageView = findViewById(R.id.stm_name_logo)
        val stmLogoAnimation : Animation = AnimationUtils.loadAnimation(this@SplashActivity,
            R.anim.sidelogo_animation)
        val stmNameLogoAnimation : Animation = AnimationUtils.loadAnimation(this@SplashActivity,
            R.anim.side_name_logo_animation)

        imgStmLogo.startAnimation(stmLogoAnimation)
        imgStmNameLogo.startAnimation(stmNameLogoAnimation)

        val loginIntent = Intent(this@SplashActivity,
            LoginActivity::class.java)
        val activityOptions = ActivityOptions.makeSceneTransitionAnimation(this@SplashActivity,
            android.util.Pair(imgStmNameLogo,"logo_name"))

        Handler().postDelayed({
            startActivity(loginIntent,
                activityOptions.toBundle())
            finish()
        }, SPLASH_SCREEN.toLong())
    }
}