package com.ecomerce.shoponline.app.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.webapi.WebApiClient
import com.ecomerce.shoponline.app.webapiresponse.SignUpResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInActivity : AppCompatActivity() {
    private  lateinit var usernameEditTextView: EditText
    private  lateinit var usernamePasswordEditTextView: EditText
    private  lateinit var  signupButton: Button
    private  lateinit var progressBar: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initView()
    }
    private fun initView() {
        usernameEditTextView = findViewById(R.id.sign_in_username_edit_view)
        usernamePasswordEditTextView = findViewById(R.id.sign_in_password_edit_view)
        signupButton=findViewById(R.id.sign_in_button)
        progressBar=findViewById(R.id.sign_in_progress_bar)
        performLogic()
    }

    private fun performLogic() {
        supportActionBar?.hide()
        signupButton.setOnClickListener {
            val username = usernameEditTextView.text
            val userPassword = usernamePasswordEditTextView.text
            if (username.toString().trim().isEmpty()) {
                usernameEditTextView.error = "enter name"
                usernameEditTextView.requestFocus()
                return@setOnClickListener
            }
            if (userPassword.toString().trim().isEmpty()) {
                usernamePasswordEditTextView.error = "enter password"
                usernamePasswordEditTextView.requestFocus()
                return@setOnClickListener
            }
            progressBar.visibility= View.VISIBLE
            loginUser(username.toString(),userPassword.toString())
        }
    }

    private fun loginUser(username: String, password: String) {
     WebApiClient.getWebApiInterface().loginUser(username,password).enqueue(object:Callback<SignUpResponse>{
         override fun onResponse(call: Call<SignUpResponse>, response: Response<SignUpResponse>) {
             Log.d("SignIn",response.code().toString())
             if(response.code()==200){
                 getSharedPreferences(packageName, Context.MODE_PRIVATE).edit().putString("userToken",response.body()?.token).apply()
                 response.body()?.token?.let { Log.d("SignIn", it) }
                 getSharedPreferences(packageName, Context.MODE_PRIVATE).edit().putString("userAccName",usernameEditTextView.text.toString()).apply()
                 response.body()?.token?.let { Log.d("SignIn", it) }
                 val intent= Intent(this@SignInActivity,HomeActivity::class.java)
                 startActivity(intent)
                 finish()
                 progressBar.visibility= View.GONE
             }

         }

         override fun onFailure(call: Call<SignUpResponse>, t: Throwable) {
             Log.d("SignIn",t.message!!)
             progressBar.visibility= View.GONE
             Toast.makeText(this@SignInActivity,"failed try again",Toast.LENGTH_LONG).show()
         }

     })
    }

}