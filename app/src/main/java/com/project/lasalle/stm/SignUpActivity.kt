package com.project.lasalle.stm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import api.RetrofitClient.service
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_sign_up.*
import model.CardResponse
import model.RegisterResponse
import model.UserItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_sign_up)
        init()
    }

    private fun init(){

        btnSignUp.setOnClickListener(){
            val name = edName.editText?.text.toString().trim()
            val email = edEmail.editText?.text.toString().trim()
            val password = edPassword.editText?.text.toString().trim()
            val phone = edPhone.editText?.text.toString().trim()
            val street = edStreet.editText?.text.toString().trim()
            val city = edCity.editText?.text.toString().trim()
            val state = edState.editText?.text.toString().trim()
            val pin = edPin.editText?.text.toString().trim()
            validateUser(name, email, password, phone, street, city, state, pin, it)
        }
    }

    private fun validateUser(name:String, email:String, password:String, phone: String, street:String, city:String, state:String, pin:String, view: View){
        if(name.isEmpty()){
            edName.error ="Please enter your name."
            edName.requestFocus()
        }
        else if(email.isEmpty()){
            edEmail.error = "Please enter your email."
            edEmail.requestFocus()
        }
        else if(phone.isEmpty()){
            edPhone.error = "Please enter your email."
            edPhone.requestFocus()
        }
        else if(password.isEmpty()){
            edPassword.error = "Please enter your email."
            edPassword.requestFocus()
        }
        else if(street.isEmpty()){
            edStreet.error = "Please enter your email."
            edStreet.requestFocus()
        }
        else if(city.isEmpty()){
            edCity.error = "Please enter your email."
            edCity.requestFocus()
        }
        else if(state.isEmpty()){
            edState.error = "Please enter your email."
            edState.requestFocus()
        }
        else if(pin.isEmpty()){
            edPin.error = "Please enter your email."
            edPin.requestFocus()
        }
        else {
            val valid = service.registerUser(name,email,password,phone,city,state,street,pin,false)

           valid.enqueue(object: Callback<RegisterResponse>{
               override fun onResponse(
                   call: Call<RegisterResponse>,
                   response: Response<RegisterResponse>
               ) {
                   val success = response.body()?.success

                   if (success != false){
                       generateCard(email)
                       Snackbar.make(view,"User Registered!!!",Snackbar.LENGTH_LONG).show()
                       val sharedUsername = getSharedPreferences("email", MODE_PRIVATE)
                       val editor = sharedUsername.edit()
                       editor.putString("email",email)
                       editor.apply()
                       val intent = Intent(this@SignUpActivity, MainActivity::class.java)
                       startActivity(intent)
                   }
               }

               override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                   Snackbar.make(view,"User Registered failed!!!",Snackbar.LENGTH_LONG).show()
               }

           })
        }
    }
    private fun generateCard(email: String){
        val cardNumber = (123456789..987654321).random().toString()

        val createCard = service.createCard(cardNumber,email)

        createCard.enqueue(object :Callback<CardResponse>{
            override fun onResponse(call: Call<CardResponse>, response: Response<CardResponse>) {
                val status = response.body()?.status

                Log.d("response", status.toString())
            }

            override fun onFailure(call: Call<CardResponse>, t: Throwable) {
                Log.e("Error",t.message.toString())
            }
        })
    }
}
