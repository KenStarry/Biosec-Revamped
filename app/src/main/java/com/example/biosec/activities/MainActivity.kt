package com.example.biosec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.fragments.AddPasswordDialog
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var adapter: PasswordsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var mainFab: FloatingActionButton
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var bottomNavBar: BottomNavigationView

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

        bottomNavBar = findViewById(R.id.bottomNavBar)
        coordinatorLayout = findViewById(R.id.mainCoordinatorLayout)

        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        mainFab = findViewById(R.id.mainFab)

        mainFab.setOnClickListener {

            val dialog = AddPasswordDialog()
            dialog.show(supportFragmentManager, "AddPasswordDialog")
        }

        mainFab.setOnLongClickListener {

            snackbar()
            return@setOnLongClickListener true
        }
    }

    private fun setupRecyclerView() {
        adapter = PasswordsAdapter()
        recyclerView = findViewById(R.id.allPasswordsRecyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    private fun snackbar() {

        Snackbar.make(coordinatorLayout, "Hello This is me!", Snackbar.LENGTH_SHORT)
            .setAnchorView(mainFab)
            .setAction("Okay", View.OnClickListener {
                toast("Perfect")
            })
            .show()
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}