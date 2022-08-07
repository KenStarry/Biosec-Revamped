package com.example.biosec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.fragments.AddPasswordDialog
import com.example.biosec.utils.SwipeGesture
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.main_toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.sortMenu -> {
                toast("Sort option selected")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initializeVariables() {

        bottomNavBar = findViewById(R.id.bottomNavBar)
        coordinatorLayout = findViewById(R.id.mainCoordinatorLayout)
        recyclerView = findViewById(R.id.allPasswordsRecyclerView)

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

        //  Item Touch helper
        val swipeGesture = object : SwipeGesture(this) {

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                when (direction) {
                    ItemTouchHelper.LEFT -> {

                        //  Delete the item
                        val password = adapter.getPasswordAt(viewHolder.bindingAdapterPosition)
                        viewModel.deletePass(password)
                        toast("${password.userName.toString()} deleted")
                    }

                    ItemTouchHelper.RIGHT -> {
                        //  Do something
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupRecyclerView() {
        adapter = PasswordsAdapter()

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