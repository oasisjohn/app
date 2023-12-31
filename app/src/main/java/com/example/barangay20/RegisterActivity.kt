package com.example.barangay20

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import android.net.Uri
import android.widget.ImageView

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        val buttonhere = findViewById<TextView>(R.id.clickhere)
        val back = findViewById<ImageView>(R.id.imageView2)
        buttonhere.setOnClickListener {
           val openURL = Intent(android.content.Intent.ACTION_VIEW)
           openURL.data = Uri.parse("https://barangaymngmt.bsisakalam.com/?fbclid=IwAR087L9n06n8XhuLhFGCI9zL2dAcGgWuiChm2iJxElOxo8XdgklJN9eoK9M")
           startActivity(openURL)
        }
        back.setOnClickListener{
            val registerback =  Intent(this, LoginActivity::class.java)
            startActivity(registerback)
            finish();
        }
    }
}