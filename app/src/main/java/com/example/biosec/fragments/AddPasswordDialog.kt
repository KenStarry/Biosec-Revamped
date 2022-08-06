package com.example.biosec.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.biosec.R
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.entities.Passwords
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPasswordDialog : BottomSheetDialogFragment() {

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var adapter: PasswordsAdapter

    private lateinit var userNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var savePassBtn: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.add_password_dialog, container, false)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)

        viewModel = ViewModelProvider(requireActivity()).get(PasswordsViewModel::class.java)
        adapter = PasswordsAdapter()

        userNameInput = view.findViewById(R.id.userNameInput)
        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        savePassBtn = view.findViewById(R.id.savePassBtn)

        savePassBtn.setOnClickListener {

            //  Check if all the edittexts are filled out
            if (!isEditTextEmpty()) {

                //  Add insert items to database
                val userName = userNameInput.text.toString()
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()

                viewModel.insertPass(Passwords(
                    userName = userName,
                    emailAddress = email,
                    password = password
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

    private fun isEditTextEmpty(): Boolean {
        return userNameInput.text.toString().trim() == "" &&
                emailInput.text.toString().trim() == "" &&
                passwordInput.text.toString().trim() == ""
    }

    private fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }
}












