package com.example.biosec.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.biosec.R
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.entities.Passwords
import com.example.biosec.utils.PasswordStrengthCalculator
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.snackbar.Snackbar

class AddPasswordDialog : BottomSheetDialogFragment() {

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var adapter: PasswordsAdapter

    private lateinit var websiteInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var savePassBtn: TextView
    private lateinit var passCheckerText: TextView
    private lateinit var passCheckerIcon: ImageView
    private lateinit var lockBtn: ImageButton

    private var passColor: Int = R.color.weak_pass
    private var passIcon: Int = R.drawable.ic_weak_pass
    private val passwordStrengthCalculator = PasswordStrengthCalculator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.add_password_dialog, container, false)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)

        viewModel = ViewModelProvider(requireActivity()).get(PasswordsViewModel::class.java)
        adapter = PasswordsAdapter()

        websiteInput = view.findViewById(R.id.websiteInput)
        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        savePassBtn = view.findViewById(R.id.savePassBtn)
        passCheckerText = view.findViewById(R.id.passCheckerText)
        passCheckerIcon = view.findViewById(R.id.passCheckerIcon)
        lockBtn = view.findViewById(R.id.lockBtn)

        passwordInput.addTextChangedListener(passwordStrengthCalculator)

        //  Observers for password calculator
        passwordStrengthCalculator.strengthLevel.observe(this, Observer {strengthLevel ->
            displayStrengthLevel(strengthLevel)
        })

        passwordStrengthCalculator.strengthColor.observe(this, Observer {strengthColor ->
            passColor = strengthColor
        })

        passwordStrengthCalculator.strengthIcon.observe(this, Observer {strengthIcon ->
            passIcon = strengthIcon
        })


        var lockedState = false
        lockBtn.setOnClickListener {

            if (!lockedState) {
                lockBtn.setImageResource(R.drawable.ic__lock)
                lockedState = true
                toast("Password locked")

            } else {
                lockBtn.setImageResource(R.drawable.ic_lock_open)
                lockedState = false
                toast("Password unlocked")
            }
        }

        savePassBtn.setOnClickListener {

            //  Check if all the edittexts are filled out
            if (!isEditTextEmpty()) {

                //  Add insert items to database
                val website = websiteInput.text.toString()
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()

                viewModel.insertPass(Passwords(
                    userName = "Someone",
                    emailAddress = email,
                    password = password,
                    isLocked = lockedState,
                    isCertified = false,
                    website = website
                ))

                viewModel.getAllPasswords().observe(requireActivity()) {
                    adapter.submitList(it)
                }


            } else {
                toast("Fields cannot be empty")
            }
        }

        return view
    }

    private fun displayStrengthLevel(strengthLevel: String) {

        passCheckerText.text = strengthLevel
        passCheckerText.setTextColor(ContextCompat.getColor(requireActivity(), passColor))
        passCheckerIcon.setImageResource(passIcon)
    }

    private fun isEditTextEmpty(): Boolean {
        return websiteInput.text.toString().trim() == "" &&
                emailInput.text.toString().trim() == "" &&
                passwordInput.text.toString().trim() == ""
    }

    private fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}












