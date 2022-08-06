package com.example.biosec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.viewmodels.PasswordsViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var adapter: PasswordsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeVariables()

        setupRecyclerView()

        viewModel = ViewModelProvider(this).get(PasswordsViewModel::class.java)
        viewModel.getAllPasswords().observe(this) {
            adapter.submitList(it)
        }

    }

    private fun initializeVariables() {

        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
    }

    private fun setupRecyclerView() {
        adapter = PasswordsAdapter()
        recyclerView = findViewById(R.id.allPasswordsRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

}