package com.example.biosec.activities

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.biosec.R
import com.example.biosec.entities.Passwords
import com.example.biosec.interfaces.PasswordClickedInterface
import com.example.biosec.viewmodels.PasswordsViewModel
import kotlinx.android.synthetic.main.activity_view_password.*

class ViewPasswordActivity : AppCompatActivity(), PasswordClickedInterface {

    //  Grabbing values from database
    private lateinit var viewModel: PasswordsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_password)

        val passId = intent.getIntExtra("PASS_ID", 0)

        viewModel = ViewModelProvider(this)[PasswordsViewModel::class.java]
        viewModel.getPasswordAt(passId).observe(this) { password ->

            initializeDbValues(password)
        }
    }

    //  set the views values
    private fun initializeDbValues(password: Passwords) {

        viewIcon.setImageResource(password.passIcon!!)
        viewIcon.imageTintList = ContextCompat.getColorStateList(this, password.passColor!!)
        watermarkImage.setImageResource(password.passIcon)

        viewWebsite.text = password.website

        //  Adjust drawable color
        val imageDrawable = openWebsite.background.mutate()

        if (imageDrawable is ShapeDrawable) {
            (imageDrawable as ShapeDrawable).paint.color = ContextCompat.getColor(this, password.passColor)

        } else if (imageDrawable is GradientDrawable) {
            (imageDrawable as GradientDrawable).setColor(ContextCompat.getColor(this, password.passColor))
        }
    }

    fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onPasswordClicked(password: Passwords) {
        toast("${password.id}")
    }
}