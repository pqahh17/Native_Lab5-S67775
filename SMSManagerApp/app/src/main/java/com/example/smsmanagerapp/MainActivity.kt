package com.example.smsmanagerapp

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val REQUEST_SMS_PERMISSION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // SMS sending functionality
        val phoneNumberEditText: EditText = findViewById(R.id.phone_number)
        val messageEditText: EditText = findViewById(R.id.message_text)
        val sendButton: Button = findViewById(R.id.send_button)

        sendButton.setOnClickListener {
            val phoneNumber = phoneNumberEditText.text.toString()
            val message = messageEditText.text.toString()

            if (phoneNumber.isNotEmpty() && message.isNotEmpty()) {
                // Check if we have SEND_SMS permission
                if (ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    // If permission is not granted, request it
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.SEND_SMS),
                        REQUEST_SMS_PERMISSION
                    )
                } else {
                    // If permission is granted, send SMS
                    sendSms(phoneNumber, message)
                }
            } else {
                Toast.makeText(this, "Please enter both phone number and message", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendSms(phoneNumber: String, message: String) {
        try {
            val smsManager: SmsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(phoneNumber, null, message, null, null)
            Toast.makeText(this, "SMS sent successfully", Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to send SMS: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_SMS_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission granted, send the SMS again
                Toast.makeText(this, "Permission granted. Sending SMS.", Toast.LENGTH_SHORT).show()
                val phoneNumber = findViewById<EditText>(R.id.phone_number).text.toString()
                val message = findViewById<EditText>(R.id.message_text).text.toString()
                sendSms(phoneNumber, message)
            } else {
                // Permission denied
                Toast.makeText(this, "Permission denied. Cannot send SMS.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
