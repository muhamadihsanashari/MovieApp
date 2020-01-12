package com.ashart.mov.sign.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.ashart.mov.R
import com.ashart.mov.sign.signin.User
import com.ashart.mov.utils.Preferences
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {

    lateinit var sUsername:String
    lateinit var sPassword:String
    lateinit var sNama:String
    lateinit var sEmail:String

    private lateinit var mFirebaseDatabase: DatabaseReference
    private lateinit var mFirebaseInstance: FirebaseDatabase
    private lateinit var mDatabase: DatabaseReference

    private lateinit var preference: Preferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        mFirebaseInstance = FirebaseDatabase.getInstance()
        mDatabase = FirebaseDatabase.getInstance().getReference()
        mFirebaseDatabase = mFirebaseInstance.getReference("User")

        preference = Preferences(this)

        btn_home.setOnClickListener {
            sUsername = et_username.text.toString()
            sPassword = et_password.text.toString()
            sNama = et_name.text.toString()
            sEmail = et_email.text.toString()

            if (sUsername.equals("")){
                et_username.error = "Silahkan masukkan Username Anda"
                et_username.requestFocus()
            }else if (sPassword.equals("")){
                et_password.error = "Silahkan masukkan Password Anda"
                et_password.requestFocus()
            }else if (sNama.equals("")) {
                et_name.error = "Silahkan masukkan Nama Anda"
                et_name.requestFocus()
            }else if (sEmail.equals("")){
                et_email.error = "Silahkan masukkan Email Anda"
                et_email.requestFocus()
            }else{
                saveUser(sUsername, sPassword, sNama, sEmail)
            }
        }
    }

    private fun saveUser(sUsername: String, sPassword: String, sNama: String, sEmail: String) {
        val user = User()
        user.email = sEmail
        user.username = sUsername
        user.password = sPassword
        user.nama = sNama

        if (sUsername != null) {
            checkingUsername(sUsername, user)
        }
    }

    private fun checkingUsername(iUsername: String, data: User) {
        mFirebaseDatabase.child(iUsername).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(dataSnapshot : DataSnapshot) {
                val user = dataSnapshot.getValue(User::class.java)
                if (user == null) {
                    mFirebaseDatabase.child(iUsername).setValue(data)

                    preference.setValues("nama", data.nama.toString())
                    preference.setValues("user", data.username.toString())
                    preference.setValues("url", "")
                    preference.setValues("email", data.email.toString())
                    preference.setValues("status", "1")

                    val intent = Intent(this@SignUpActivity,
                        SignUpPhotoScreenActivity::class.java).putExtra("nama", data.nama);
                    startActivity(intent)
                }else{
                    Toast.makeText(this@SignUpActivity, "User sudah digunakan!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@SignUpActivity, ""+error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
