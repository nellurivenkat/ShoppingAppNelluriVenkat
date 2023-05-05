package com.ecomerce.shoponline.app.activities


import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import androidx.core.widget.NestedScrollView
import com.ecomerce.shoponline.app.R
import com.ecomerce.shoponline.app.models.SignUpModel
import com.ecomerce.shoponline.app.webapi.WebApiClient
import com.ecomerce.shoponline.app.webapi_objects.address
import com.ecomerce.shoponline.app.webapi_objects.geolocation
import com.ecomerce.shoponline.app.webapi_objects.name
import com.ecomerce.shoponline.app.webapiresponse.UserSignUpResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    private  lateinit var usernameEditTextView:EditText
    private  lateinit var usernamePasswordEditTextView:EditText
    private  lateinit var firstnameEditTextView:EditText
    private  lateinit var lastnamePasswordEditTextView:EditText
    private  lateinit var addressNumberEditTextView:EditText
    private  lateinit var addressStreetEditTextView:EditText
    private  lateinit var cityEditTextView:EditText
    private  lateinit var zipCodePasswordEditTextView:EditText
    private  lateinit var phoneNumberEditTextView:EditText
    private  lateinit var userEmailEditTextView:EditText
    private  lateinit var progressBar: ProgressBar
    private  lateinit var  signupButton: Button
    private  lateinit var  nestedScrollView: NestedScrollView
    private  lateinit var  sharedPreferences: SharedPreferences
    private  lateinit var  signinButton:TextView
    private  var  windowsHeight:Int=0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        if(!(getSharedPreferences(packageName, Context.MODE_PRIVATE).getString("userToken","").isNullOrEmpty())){
            val intent= Intent(this@SignupActivity,HomeActivity::class.java)
            startActivity(intent)
            finish()
        }
        initView()
    }
    // a function to initialize views
    private fun initView(){
        usernameEditTextView=findViewById(R.id.signup_username_edit_view)
        usernamePasswordEditTextView=findViewById(R.id.signup_password_edit_view)
        firstnameEditTextView=findViewById(R.id.signup_firstname_edit_view)
        lastnamePasswordEditTextView=findViewById(R.id.signup_lastname_edit_view)
       addressNumberEditTextView=findViewById(R.id.signup_addressnumber_edit_view)
        addressStreetEditTextView=findViewById(R.id.signup_addressstreet_edit_view)
       cityEditTextView=findViewById(R.id.signup_city_edit_view)
        zipCodePasswordEditTextView=findViewById(R.id.signup_zipcode_edit_view)
        phoneNumberEditTextView=findViewById(R.id.signup_phonenumber_edit_view)
        userEmailEditTextView=findViewById(R.id.signup_user_email_edit_view)
        progressBar=findViewById(R.id.signup_progress_bar)
        signupButton=findViewById(R.id.signup_button)
        signinButton=findViewById(R.id.signup_username_edit_sign_in)
        nestedScrollView=findViewById(R.id.nestedScroll)
        performLogic()
    }
    // function to perform logic
    private fun performLogic(){

        sharedPreferences=getSharedPreferences(packageName, Context.MODE_PRIVATE)
       signupButton.setOnClickListener {
           val username=usernameEditTextView.text
           val userPassword=usernamePasswordEditTextView.text
           val userFirstName=firstnameEditTextView.text
           val userLastName=lastnamePasswordEditTextView.text
           val userAddressNumber=addressNumberEditTextView.text
           val userAddressStreet=addressStreetEditTextView.text
           val userCity=cityEditTextView.text
           val userZipCode=zipCodePasswordEditTextView.text
           val userPhoneNumber=phoneNumberEditTextView.text
           val userEmail=userEmailEditTextView.text
           windowsHeight=resources.displayMetrics.heightPixels
           signinButton.setOnClickListener {
               startActivity(Intent(this@SignupActivity,SignInActivity::class.java))
               finish()
           }
           if(TextUtils.isEmpty(username.toString().trim())){
               usernameEditTextView.error = "enter name"
               usernameEditTextView.requestFocus()
               return@setOnClickListener
           }
           if(TextUtils.isEmpty(userPassword.toString().trim())){
               usernamePasswordEditTextView.error = "enter password"
               usernamePasswordEditTextView.requestFocus()
               return@setOnClickListener
           }
           if( TextUtils.isEmpty(userEmail.toString().trim())){
               userEmailEditTextView.error = "enter email"
               userEmailEditTextView.requestFocus()
               return@setOnClickListener
           }
           if( TextUtils.isEmpty(userFirstName.toString().trim())){
               firstnameEditTextView.error = "first name"
               firstnameEditTextView.requestFocus()
               return@setOnClickListener
           }
           if(TextUtils.isEmpty(userLastName.toString().trim())){
               lastnamePasswordEditTextView.error = "last name"
               lastnamePasswordEditTextView.requestFocus()
               return@setOnClickListener
           }
           if(TextUtils.isEmpty(userAddressNumber.toString().trim())){
               addressNumberEditTextView.error = "enter address"
               addressNumberEditTextView.requestFocus()
               return@setOnClickListener
           }
           if(TextUtils.isEmpty(userAddressStreet.toString().trim())){
               addressStreetEditTextView.error = "enter street"
               addressStreetEditTextView.requestFocus()
               return@setOnClickListener
           }
           if(TextUtils.isEmpty(userCity.toString().trim())){
               cityEditTextView.error = "enter city"
               cityEditTextView.requestFocus()
               return@setOnClickListener
           }
           if(TextUtils.isEmpty(userZipCode.toString().trim())){
               zipCodePasswordEditTextView.error = "enter zipCode"
               zipCodePasswordEditTextView.requestFocus()
               return@setOnClickListener
           }
           if(TextUtils.isEmpty(userPhoneNumber.toString().trim())){
               phoneNumberEditTextView.error = "enter phone"
               phoneNumberEditTextView.requestFocus()
               return@setOnClickListener
           }
           nestedScrollView.post {
                  nestedScrollView.fling(windowsHeight)
                   nestedScrollView.fullScroll(View.FOCUS_DOWN)

           }
           progressBar.visibility= View.VISIBLE
          val signUp= SignUpModel(userEmail.toString(),username.toString(),userPassword.toString(), name(userFirstName.toString(),userLastName.toString()),
               address(userCity.toString(),userAddressStreet.toString(),userAddressNumber.toString(),userZipCode.toString(),geolocation("379292","00488488"))
               ,userPhoneNumber.toString())
           signUpUser(signUp)

           val gson=Gson()
          Log.d("Json", gson.toJson(signUp))
       }
    }
    //function to sign up user connecting to api using retrofit
    private fun   signUpUser( signUpModel: SignUpModel){

        WebApiClient.getWebApiInterface().signUpUser(signUpModel).enqueue(object:Callback<UserSignUpResponse>{
            override fun onResponse(
                call: Call<UserSignUpResponse>,
                response: Response<UserSignUpResponse>
            ) {
                if (response.code() == 200) {
                    sharedPreferences.edit {
                        putString("userId", response.body()?.id)
                        putString("username", response.body()?.username)
                        putString("userpassword", response.body()?.password)
                        apply()
                    }
                    Log.d("SignUp",sharedPreferences.getString("userId","")!!)
                    val intent=Intent(this@SignupActivity,SignInActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
            override fun onFailure(call: Call<UserSignUpResponse>, t: Throwable) {
                Log.d("SignUp",t.message!!)
                progressBar.visibility= View.GONE
                Toast.makeText(this@SignupActivity,"failed try again",Toast.LENGTH_LONG).show()
            }

        })
    }

}