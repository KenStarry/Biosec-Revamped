package com.example.biosec.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.biosec.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddPasswordDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.add_password_dialog, container, false)


        return view
    }
}












