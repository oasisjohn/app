package com.example.barangay20

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.privacysandbox.tools.core.model.Method
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.barangay20.databinding.ActivityLoginBinding
import com.example.barangay20.databinding.ActivityRegisterBinding
import com.google.android.material.textfield.TextInputEditText
import org.json.JSONObject

class LoginActivity : AppCompatActivity() {
    lateinit var username: EditText
    lateinit var password: EditText
    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        username = findViewById(R.id.text_input_email)
         password = findViewById(R.id.text_input_password)
        val signupBtn =findViewById<TextView>(R.id.tv_click)
        val loginBtn = findViewById<Button>(R.id.login)
        signupBtn.setOnClickListener {
          val registerIntent =  Intent(this, RegisterActivity::class.java)
            startActivity(registerIntent)
        }
        loginBtn.setOnClickListener{

            val post_user = username.text.toString()
            val post_password = password.text.toString()
            val apiUrl = "https://barangaymngmt.bsisakalam.com/login.php"
            val requestQueue = Volley.newRequestQueue(this)

            val params = mapOf(
                "username" to post_user,
                "password" to post_password

            )

            val request = object : StringRequest(
                Request.Method.POST, apiUrl,
                { response ->
                    val jsonResponse = JSONObject(response)

                    // Extract data from the JSON response
                    val status = jsonResponse.optBoolean("status")
                    val message = jsonResponse.optString("message")
                    Toast.makeText(this, "${message}", Toast.LENGTH_LONG).show()
                    if (status) {
                        val homeActivityIntent =
                            Intent(this, HomeActivity::class.java)
                        startActivity(homeActivityIntent)
                        finish()
                        // Login successful, handle further data extraction if needed
                    } else {
                        Toast.makeText(this, "${message}", Toast.LENGTH_LONG).show()
                        // Login failed, display the error message

                    }

                },
                { error ->
                    Toast.makeText(this, "Invalid", Toast.LENGTH_LONG).show()
                }
            ) {
                override fun getParams(): Map<String, String> {
                    return params
                }
            }
            requestQueue.add(request)
        }

    }
}




