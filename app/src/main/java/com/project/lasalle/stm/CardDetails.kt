package com.project.lasalle.stm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.APIServices
import api.RetrofitClient
import kotlinx.android.synthetic.main.activity_main.*
import model.CardDetails
import model.TransactionAdapter
import model.Transactions
import model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CardDetails : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_card_details)
        init()
    }

    private fun init(){
        val sharedUsername= getSharedPreferences("email", MODE_PRIVATE)
        val email = sharedUsername.getString("email", null)

        if (email != null) {
            val user = RetrofitClient.service.getUser(email)

            user.enqueue(object: Callback<UserItem> {
                override fun onResponse(call: Call<UserItem>, response: Response<UserItem>) {

                    val name = response.body()?.name
                    txtName.text = name
                    getcardDetails(email)

                }
                override fun onFailure(call: Call<UserItem>, t: Throwable) {
                }
            })
        }
    }
    private fun getcardDetails(email :String){
        val card = RetrofitClient.service.getCardDetails(email)

        card.enqueue(object: Callback<CardDetails>{
            override fun onResponse(call: Call<CardDetails>, response: Response<CardDetails>) {
                val cardNumber : String? = response.body()?.cardNumber
                val expDate = response.body()?.expDate
                val startDate = response.body()?.StartDate
                txtCardNumber.text = cardNumber
                txtExpDate.text = expDate
                txtActivePlan.text = startDate
                if (cardNumber != null) {
                    getTransactions(cardNumber)
                }
            }

            override fun onFailure(call: Call<CardDetails>, t: Throwable) {
            }

        })
    }

    private fun getTransactions(cardNumber : String){
        val transactionList = MutableLiveData<List<Transactions>>()

            val retrofitService = RetrofitClient.getRetrofitInstance().create(APIServices::class.java)

            retrofitService.getTransactions(cardNumber).enqueue(object :Callback<List<Transactions>>{
                override fun onResponse(
                    call: Call<List<Transactions>>,
                    response: Response<List<Transactions>>
                ) {
                    transactionList.value = response.body()
                    transactionList.observe(this@CardDetails, Observer {
                        val transactionsRecyclerView :RecyclerView = findViewById(R.id.cardDetailsRecyclerView)
                        transactionsRecyclerView.layoutManager = LinearLayoutManager(this@CardDetails)
                        transactionsRecyclerView.adapter = TransactionAdapter(it)
                    })
                }

                override fun onFailure(call: Call<List<Transactions>>, t: Throwable) {
                    Log.e("Data", t.message + " " + cardNumber)
                }
            })

    }
}