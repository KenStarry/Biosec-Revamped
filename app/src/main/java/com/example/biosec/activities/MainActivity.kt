package com.example.biosec.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.adapters.ArchivesAdapter
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.entities.Archives
import com.example.biosec.fragments.Dialogs.AddPasswordDialog
import com.example.biosec.fragments.Dialogs.ArchivesDialog
import com.example.biosec.utils.SwipeGesture
import com.example.biosec.viewmodels.ArchivesViewModel
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    private val alphabets = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
    'W', 'X', 'Y', 'Z')

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var archivesViewModel: ArchivesViewModel
    private lateinit var adapter: PasswordsAdapter
    private lateinit var archivesAdapter: ArchivesAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var toolbar: Toolbar
    private lateinit var totalPasswordsCount: TextView
    private lateinit var mainFab: FloatingActionButton
    private lateinit var coordinatorLayout: CoordinatorLayout
    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var emptyTaskLottie: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeVariables()

        viewModel = ViewModelProvider(this).get(PasswordsViewModel::class.java)
        archivesViewModel = ViewModelProvider(this).get(ArchivesViewModel::class.java)

        viewModel.getAllPasswords().observe(this) {

            adapter.submitList(it)
            totalPasswordsCount.text = it.size.toString()

            //  if the list is empty, show the lottie animation
            if (it.isEmpty()) {
                emptyTaskLottie.visibility = View.VISIBLE
                recyclerView.visibility = View.GONE
            } else {
                emptyTaskLottie.visibility = View.GONE
                recyclerView.visibility = View.VISIBLE
            }
        }

        setupRecyclerView()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.main_toolbar_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.sortMenu -> {
                toast("Sort option selected")
            }

            R.id.archiveMenu -> {

                archivesViewModel.getAllArchives().observe(this) { archives ->
                    archivesAdapter.submitList(archives)
                }

                val archivesDialog = ArchivesDialog(archivesAdapter)
                archivesDialog.show(supportFragmentManager, "ArchivesDialog")
            }

            R.id.hideDetailsMenu -> {
                toast("Hide details option")
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initializeVariables() {

        adapter = PasswordsAdapter(this)
        archivesAdapter = ArchivesAdapter(this)
        bottomNavBar = findViewById(R.id.bottomNavBar)
        totalPasswordsCount = findViewById(R.id.totalPasswordsCount)
        coordinatorLayout = findViewById(R.id.mainCoordinatorLayout)
        recyclerView = findViewById(R.id.allPasswordsRecyclerView)
        emptyTaskLottie = findViewById(R.id.emptyLottieHolder)

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
                        toast("${password.website.toString()} deleted")
                    }

                    ItemTouchHelper.RIGHT -> {

                        //  Get the password that has been swiped
                        val passwordItem = adapter.getPasswordAt(viewHolder.bindingAdapterPosition)

                        //  copy contents of this file and insert to the archive
                        archivesViewModel.insertArchive(Archives(
                            website = passwordItem.website,
                            userName = passwordItem.userName,
                            emailAddress = passwordItem.emailAddress,
                            password = passwordItem.password,
                            passStrengthIcon = passwordItem.passStrengthIcon,
                            isLocked = passwordItem.isLocked,
                            passIcon = passwordItem.passIcon,
                            passColor = passwordItem.passColor
                        ))
                        viewModel.deletePass(passwordItem)
                        toast("${passwordItem.website.toString()} archived successfully")
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(recyclerView)
    }

    private fun setupRecyclerView() {
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