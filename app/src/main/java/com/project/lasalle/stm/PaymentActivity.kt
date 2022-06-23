package com.project.lasalle.stm

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.EditText
import android.widget.TextView
import api.RetrofitClient.service
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_payment.*
import model.TransactionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.exp

class PaymentActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_payment)
        init()
    }

    @SuppressLint("SetTextI18n")
    private fun init(){
        val sharedUsername2= getSharedPreferences("amount", MODE_PRIVATE)
        val sharedUsername = getSharedPreferences("email", MODE_PRIVATE)
        val cardPref = getSharedPreferences("card", MODE_PRIVATE)
        val cardNumber: String? = cardPref.getString("cardNumber", null)
        val email : String? = sharedUsername.getString("email", null)
        val amount: String? = sharedUsername2.getString("amount", null)
        val duration = sharedUsername2.getString("duration", null)

        val tdAmount : TextView = findViewById(R.id.tdAmount)
        val tdDuration :TextView = findViewById(R.id.tdDuration)

        val card = tdCard.editText?.text.toString().trim()
        val name = tdName.editText?.text.toString().trim()
        val expDate = tdExp.editText?.text.toString().trim()
        val cvv = tdCvv.editText?.text.toString().trim()


        tdAmount.text = "Amount: $" + amount.toString()
        tdDuration.text = "Duration: " + duration.toString() + " Days"

        Log.d("Data", amount.toString())

        btnPay.setOnClickListener(){

            if (card.isEmpty()){
                tdCard.error = "Please enter name"
            }
            else if(name.isEmpty()){
                tdName.error= "Please enter card number"
            }
            else if(expDate.isEmpty()){
                tdExp.error= "Please enter card number"
            }
            else if(cvv.isEmpty()){
                tdCvv.error= "Please enter card number"
            }else{
                val transaction = service.createTransaction(amount,email.toString(),cardNumber.toString())

                transaction.enqueue(object :Callback<TransactionResponse>{
                    override fun onResponse(
                        call: Call<TransactionResponse>,
                        response: Response<TransactionResponse>
                    ) {
                        val status = response.body()?.status
                        Snackbar.make(it,"Transaction Successfully Done!!!",Snackbar.LENGTH_LONG).show()
                        val intent = Intent(this@PaymentActivity, MainActivity::class.java)
                        startActivity(intent)


                    }
                    override fun onFailure(call: Call<TransactionResponse>, t: Throwable) {
                        Snackbar.make(it," Error in database !!!",Snackbar.LENGTH_LONG).show()
                    }
                })
            }
        }
    }

}