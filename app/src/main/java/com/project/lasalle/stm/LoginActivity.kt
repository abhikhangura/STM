package com.project.lasalle.stm

import api.RetrofitClient.service
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.View
import android.view.WindowManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_login.*
import model.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onResume() {
        super.onResume()
        edEmail.isErrorEnabled = false
        edPassword.isErrorEnabled = false
        edEmail.requestFocus()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
            WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
        setContentView(R.layout.activity_login)
        init()
    }

    private fun init(){
        btnLogin.setOnClickListener(){

            val username = edEmail.editText?.text.toString().trim()
            val password = edPassword.editText?.text.toString().trim()
            validateUser(username, password, it)
        }

        txtRegister.setOnClickListener(){
            val intent = Intent(this@LoginActivity, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    // validating user
    private fun validateUser(username: String, password: String, it: View) {
        if (username.isEmpty()){
            edEmail.error = "Please enter your email.."
            edEmail.requestFocus()
        }
        else if (password.isEmpty()) {
            edPassword.error = "Please enter your password.."
            edPassword.requestFocus()
        }
        else{
           val valid = service.verifyUser(username,password)

            valid.enqueue(object: Callback<LoginResponse>{
                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>){
                    val user = response.body()?.success

                    if (user == true){
                        val sharedUsername = getSharedPreferences("email", MODE_PRIVATE)
                        val editor = sharedUsername.edit()
                        editor.putString("email",username)
                        editor.apply()
                        val mainIntent = Intent(this@LoginActivity,
                            MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    }
                    else{
                        Snackbar.make(it,R.string.invalidIdAndPassword, Snackbar.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    e("Error in getting data",t.message.toString())
                }

            })
        }
    }
}
