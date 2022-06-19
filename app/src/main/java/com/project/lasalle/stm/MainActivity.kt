package com.project.lasalle.stm


import api.RetrofitClient.service
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init(){

        val sharedUsername= getSharedPreferences("username", MODE_PRIVATE)
        val username = sharedUsername.getString("username", null)

        if (username != null) {
            val user = service.getUser(username)

            user.enqueue(object: Callback<UserItem>{
                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {


                }

                override fun onFailure(call: Call<UserItem>, t: Throwable) {

                }

            })
        }

    }
}