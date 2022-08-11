package com.example.biosec.fragments.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.biosec.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArchivesDialog : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.archives_dialog, container, false)



        return view
    }
}