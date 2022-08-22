package com.example.biosec.activities

import android.content.Intent
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
import com.example.biosec.entities.Passwords
import com.example.biosec.fragments.Dialogs.AddPasswordDialog
import com.example.biosec.fragments.Dialogs.ArchivesDialog
import com.example.biosec.fragments.classes.GenerateFragment
import com.example.biosec.fragments.classes.GroupsFragment
import com.example.biosec.fragments.classes.HomeFragment
import com.example.biosec.interfaces.PasswordClickedInterface
import com.example.biosec.utils.SwipeGesture
import com.example.biosec.viewmodels.ArchivesViewModel
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationBarItemView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {

    private val alphabets = arrayOf('A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
    'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
    'W', 'X', 'Y', 'Z')

    private lateinit var archivesViewModel: ArchivesViewModel
    private lateinit var archivesAdapter: ArchivesAdapter
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeVariables()

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment())
            .addToBackStack(null)
            .commit()
        bottomNavBar.selectedItemId = R.id.bottomHome
    }

    private fun initializeVariables() {

        toolbar = findViewById(R.id.mainToolbar)
        setSupportActionBar(toolbar)
        supportActionBar!!.title = ""

        mainFab.setOnClickListener {

            val dialog = AddPasswordDialog()
            dialog.show(supportFragmentManager, "AddPasswordDialog")
        }

        mainFab.setOnLongClickListener {

            snackbar()
            return@setOnLongClickListener true
        }

        //  Bottom Navigation View
        bottomNavBar.setOnItemSelectedListener(this)
    }

    private fun snackbar() {

        Snackbar.make(mainCoordinatorLayout, "Hello This is me!", Snackbar.LENGTH_SHORT)
            .setAnchorView(mainFab)
            .setAction("Okay", View.OnClickListener {
                toast("Perfect")
            })
            .show()
    }

    private fun toast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
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

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.bottomHome -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, HomeFragment())
                    .addToBackStack(null)
                    .commit()
            }

            R.id.bottomGroups -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, GroupsFragment())
                    .addToBackStack(null)
                    .commit()
            }

            R.id.bottomGenerate -> {

                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, GenerateFragment())
                    .addToBackStack(null)
                    .commit()
            }

            R.id.bottomSettings -> {
                val intent = Intent(this, SettingsActivity::class.java)
                startActivity(intent)
            }
        }

        return true
    }


}