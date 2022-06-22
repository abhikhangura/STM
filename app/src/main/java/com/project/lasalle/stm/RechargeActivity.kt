package com.project.lasalle.stm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import api.APIServices
import api.RetrofitClient
import model.PlanAdapter
import model.PlanViewModel
import model.Plans
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RechargeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
        )
        setContentView(R.layout.activity_recharge)

        val plansList = MutableLiveData<List<Plans>>()
        val retrofitService = RetrofitClient.getRetrofitInstance().create(APIServices::class.java)

        retrofitService.getPlans().enqueue(object : Callback<List<Plans>>{
            override fun onResponse(call: Call<List<Plans>>, response: Response<List<Plans>>) {
                plansList.value = response.body()
                plansList.observe(this@RechargeActivity, Observer {
                    val plansRecyclerView : RecyclerView = findViewById(R.id.plansRecyclerView)
                    plansRecyclerView.layoutManager = LinearLayoutManager(this@RechargeActivity)
                    plansRecyclerView.adapter = PlanAdapter(it)
                })
            }

            override fun onFailure(call: Call<List<Plans>>, t: Throwable) {
                Log.e("Error", t.message.toString())
            }

        })

    }

}