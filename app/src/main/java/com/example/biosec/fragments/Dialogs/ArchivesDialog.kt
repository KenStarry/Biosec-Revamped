package com.example.biosec.fragments.Dialogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.activities.MainActivity
import com.example.biosec.adapters.ArchivesAdapter
import com.example.biosec.entities.Archives
import com.example.biosec.viewmodels.ArchivesViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArchivesDialog(
    private val adapter: ArchivesAdapter

) : BottomSheetDialogFragment() {

    private lateinit var viewModel: ArchivesViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view: View = inflater.inflate(R.layout.archives_dialog, container, false)

        val archivesRecycler: RecyclerView = view.findViewById(R.id.archivesRecyclerView)

        archivesRecycler.adapter = adapter
        archivesRecycler.layoutManager = LinearLayoutManager(requireActivity())

        return view
    }
}













