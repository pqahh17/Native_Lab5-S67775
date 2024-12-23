package com.example.smsapp

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Set padding for edge-to-edge experience
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // SMS sending functionality
        val phoneNumberEditText: EditText = findViewById(R.id.phone_number)
        val messageEditText: EditText = findViewById(R.id.message_text)
        val sendButton: Button = findViewById(R.id.send_button)

        sendButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val message = messageEditText.text.toString()

            if (phoneNumber.isNotEmpty() && message.isNotEmpty()) {
                sendSMS(phoneNumber, message)
            } else {
                Toast.makeText(this, "Please enter a phone number and message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendSMS(phoneNumber: String, message: String) {
        val smsIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("smsto:$phoneNumber") // Set the SMS URI
            putExtra("sms_body", message)         // Set the SMS message body
        }
        startActivity(smsIntent)
    }
}