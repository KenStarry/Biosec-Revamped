package com.example.biosec.fragments.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.adapters.GroupsAdapter
import com.example.biosec.entities.Groups
import com.example.biosec.viewmodels.GroupsViewModel
import kotlinx.android.synthetic.main.fragment_groups.view.*
import java.security.acl.Group

class GroupsFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: GroupsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_groups, container, false)

        //  Getting the viewmodel
        initializeVariables(view)
        setupViewModel()

        //  setup recycler view
        setupRecyclerView()

        return view
    }

    private fun initializeVariables(view: View) {

        adapter = GroupsAdapter()
        recyclerView = view.groupCardsRecyclerView
    }

    private fun setupViewModel() {

        val viewModel = ViewModelProvider(this)[GroupsViewModel::class.java]

        viewModel.getAllGroups().observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupRecyclerView() {

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
    }


}











