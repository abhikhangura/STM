package com.project.lasalle.stm


import android.content.Intent
import api.RetrofitClient.service
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import kotlinx.android.synthetic.main.activity_main.*
import model.CardDetails
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

        val sharedUsername= getSharedPreferences("email", MODE_PRIVATE)
        val email = sharedUsername.getString("email", null)

        btnRecharge.setOnClickListener(){
            val intent = Intent(this@MainActivity, RechargeActivity::class.java)
            startActivity(intent)
        }
        if (email != null) {
            val user = service.getUser(email)

            user.enqueue(object: Callback<UserItem>{
                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {

                    val name = response.body()?.name
                    txtName.text = name
                    getcardDetails(email)
                }

                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                }

            })
        }
        card.setOnClickListener(){
            val intent = Intent(this@MainActivity, com.project.lasalle.stm.CardDetails::class.java)
            startActivity(intent)
        }
    }

    private fun getcardDetails(email :String){
        val card = service.getCardDetails(email)

        card.enqueue(object: Callback<CardDetails>{
            override fun onResponse(call: Call<CardDetails>, response: Response<CardDetails>) {
               val cardNumber = response.body()?.cardNumber
                val expDate = response.body()?.expDate
                val startDate = response.body()?.StartDate
                txtCardNumber.text = cardNumber
                txtExpDate.text = expDate
                txtActivePlan.text = startDate
            }

            override fun onFailure(call: Call<CardDetails>, t: Throwable) {
            }

        })
    }
}