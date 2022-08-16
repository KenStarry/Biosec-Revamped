package com.example.biosec.activities

import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.ShapeDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Update
import com.example.biosec.R
import com.example.biosec.adapters.ViewPasswordAdapter
import com.example.biosec.entities.Passwords
import com.example.biosec.fragments.Dialogs.UpdatePasswordDialog
import com.example.biosec.interfaces.PasswordClickedInterface
import com.example.biosec.viewmodels.PasswordsViewModel
import kotlinx.android.synthetic.main.activity_view_password.*
import kotlinx.android.synthetic.main.view_toolbar.*

class ViewPasswordActivity : AppCompatActivity(), PasswordClickedInterface {

    //  Grabbing values from database
    private lateinit var viewModel: PasswordsViewModel
    private lateinit var adapter: ViewPasswordAdapter

    private lateinit var pass: Passwords

    val passwordColumns = arrayOf(
        "User Name",
        "Email Address",
        "Password",
        "Phone Number",
        "Security Question",
        "Security Answer",
        "Description"
    )

    val passwordIcons = intArrayOf(
        R.drawable.ic_view_user_name,
        R.drawable.ic_view_email,
        R.drawable.ic_view_password,
        R.drawable.ic_view_phone,
        R.drawable.ic_view_question_mark,
        R.drawable.ic_view_question_answer,
        R.drawable.ic_view_description
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_password)

        setupToolbar()
        setupListeners()

        val passId = intent.getIntExtra("PASS_ID", 0)

        viewModel = ViewModelProvider(this)[PasswordsViewModel::class.java]
        viewModel.getPasswordAt(passId).observe(this) { password ->

            val passwordMap = mutableMapOf(
                0 to password.userName,
                1 to password.emailAddress,
                2 to password.password,
                3 to password.phoneNumber,
                4 to password.secQuestion,
                5 to password.secAnswer,
                6 to password.description
            )

            setPass(password)

            initializeDbValues(password)
            buildRecyclerView(passwordMap, password.passColor!!)
        }
    }

    private fun setupListeners() {

        viewCopyPass.setOnClickListener {
            toast("Password copied")
        }

        openWebsiteBtn.setOnClickListener {
            toast("Website opened")
        }
    }

    //  set the views values
    private fun initializeDbValues(password: Passwords) {

        viewIcon.setImageResource(password.passIcon!!)
        watermarkImage.setImageResource(password.passIcon)

        viewIcon.imageTintList = ContextCompat.getColorStateList(this, password.passColor!!)
        viewCopyPass.imageTintList = ContextCompat.getColorStateList(this, password.passColor)
        viewPassBarChart.imageTintList = ContextCompat.getColorStateList(this, password.passColor)
        viewPassQuestionMark.imageTintList = ContextCompat.getColorStateList(this, password.passColor)

        viewWebsite.text = password.website
        viewPassCharacterCount.text = password.password!!.toString().length.toString()

        //  Adjust drawable color
        val imageDrawable = openWebsiteBtn.background.mutate()

        if (imageDrawable is ShapeDrawable) {
            (imageDrawable as ShapeDrawable).paint.color =
                ContextCompat.getColor(this, password.passColor)

        } else if (imageDrawable is GradientDrawable) {
            (imageDrawable as GradientDrawable).setColor(
                ContextCompat.getColor(
                    this,
                    password.passColor
                )
            )
        }

        //  Checking the password strength
        when (password.passStrengthIcon) {

            R.drawable.ic_weak_pass -> {
                viewPassLottie.setAnimation(R.raw.alert_lottie)
                viewPassLottie.playAnimation()
                viewPassPasswordStatusText.text = getString(R.string.weak_pass_text)
                viewPassPasswordStatusText.setTextColor(ContextCompat.getColor(this, R.color.weak_pass))
            }

            R.drawable.ic_medium_pass -> {
                viewPassLottie.setAnimation(R.raw.medium_pass)
                viewPassLottie.playAnimation()
                viewPassPasswordStatusText.text = getString(R.string.medium_pass_text)
                viewPassPasswordStatusText.setTextColor(ContextCompat.getColor(this, R.color.medium_pass))
            }

            R.drawable.ic_strong_pass -> {
                viewPassLottie.setAnimation(R.raw.strong_pass)
                viewPassLottie.playAnimation()
                viewPassPasswordStatusText.text = getString(R.string.strong_pass_text)
                viewPassPasswordStatusText.setTextColor(ContextCompat.getColor(this, R.color.strong_pass))
            }

            R.drawable.ic_verified -> {
                viewPassLottie.setAnimation(R.raw.certified_pass_blue)
                viewPassLottie.playAnimation()
                viewPassPasswordStatusText.text = getString(R.string.certified_pass_text)
                viewPassPasswordStatusText.setTextColor(ContextCompat.getColor(this, R.color.certified_pass))
            }
        }
    }

    private fun buildRecyclerView(passwordMap: MutableMap<Int, String?>, passColor: Int) {

        adapter = ViewPasswordAdapter(this, passwordColumns, passwordIcons, passwordMap, passColor)

        viewPassRecyclerView.adapter = adapter
        viewPassRecyclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun setupToolbar() {
        val toolbar = viewToolbar as Toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    //  Setters and getters for the id
    private fun setPass(password: Passwords) {
        pass = password
    }

    private fun getPass(): Passwords {
        return pass
    }

    override fun onPasswordClicked(password: Passwords) {
        toast("${password.id}")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.view_pass_toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.updatePassMenu -> {

                //  Open Update Password Dialog
                val updateDialog = UpdatePasswordDialog(getPass())
                updateDialog.show(supportFragmentManager, "UpdateDialog")
            }
        }
        return super.onOptionsItemSelected(item)
    }
}