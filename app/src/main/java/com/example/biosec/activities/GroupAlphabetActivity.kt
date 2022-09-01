package com.example.biosec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.entities.Passwords
import com.example.biosec.interfaces.PasswordClickedInterface
import com.example.biosec.viewmodels.PasswordsViewModel
import kotlinx.android.synthetic.main.activity_group_alphabet.*

class GroupAlphabetActivity : AppCompatActivity(), PasswordClickedInterface {

    private lateinit var viewModel: PasswordsViewModel

    private lateinit var alphabet: String
    private lateinit var adapter: PasswordsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_group_alphabet)

        val toolbar: Toolbar = findViewById(R.id.viewAlphabetToolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        initializeVariables()

        //  Viewmodel
        viewModel = ViewModelProvider(this)[PasswordsViewModel::class.java]
        viewModel.getAlphabeticalPasswords("${alphabet}%").observe(this) {

            adapter.submitList(it)
        }

        setupRecyclerView()
    }

    private fun initializeVariables() {

        adapter = PasswordsAdapter(this, this)
        alphabet = intent.getStringExtra("ALPHABET")!!

        viewAlphabetTitle.text = alphabet
    }

    private fun setupRecyclerView() {

        viewAlphabetRecyclerView.adapter = adapter
        viewAlphabetRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    override fun onPasswordClicked(password: Passwords) {
        Toast.makeText(this, "", Toast.LENGTH_SHORT).show()
    }

}