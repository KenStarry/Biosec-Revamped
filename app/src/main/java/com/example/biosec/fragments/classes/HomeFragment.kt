package com.example.biosec.fragments.classes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.biosec.R
import com.example.biosec.adapters.ArchivesAdapter
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.entities.Archives
import com.example.biosec.entities.Passwords
import com.example.biosec.interfaces.PasswordClickedInterface
import com.example.biosec.utils.SwipeGesture
import com.example.biosec.viewmodels.ArchivesViewModel
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), PasswordClickedInterface {

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var archivesViewModel: ArchivesViewModel
    private lateinit var adapter: PasswordsAdapter
    private lateinit var archivesAdapter: ArchivesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initializeVariables()
        initializeViewModel()
        setupRecyclerView()
    }

    private fun initializeViewModel() {
        viewModel = ViewModelProvider(this).get(PasswordsViewModel::class.java)
        archivesViewModel = ViewModelProvider(this).get(ArchivesViewModel::class.java)

        viewModel.getAllPasswords().observe(viewLifecycleOwner) {

            adapter.submitList(it)
            totalPasswordsCount.text = it.size.toString()

            //  if the list is empty, show the lottie animation
            if (it.isEmpty()) {
                emptyLottieHolder.visibility = View.VISIBLE
                allPasswordsRecyclerView.visibility = View.GONE
            } else {
                emptyLottieHolder.visibility = View.GONE
                allPasswordsRecyclerView.visibility = View.VISIBLE
            }
        }
    }

    private fun initializeVariables() {

        adapter = PasswordsAdapter(requireContext(), this)
        archivesAdapter = ArchivesAdapter(requireContext())

        //  Item Touch helper
        val swipeGesture = object : SwipeGesture(requireContext()) {

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
                        archivesViewModel.insertArchive(
                            Archives(
                            website = passwordItem.website,
                            userName = passwordItem.userName,
                            emailAddress = passwordItem.emailAddress,
                            password = passwordItem.password,
                            passStrengthIcon = passwordItem.passStrengthIcon,
                            isLocked = passwordItem.isLocked,
                            passIcon = passwordItem.passIcon,
                            passColor = passwordItem.passColor,
                            url = "https://linkedin.com",
                            phoneNumber = "0717446607",
                            secQuestion = "Which School Did you go to?",
                            secAnswer = "Starehe",
                            description = "Where all coding begins"
                        )
                        )
                        viewModel.deletePass(passwordItem)
                        toast("${passwordItem.website.toString()} archived successfully")
                    }
                }
            }
        }

        val touchHelper = ItemTouchHelper(swipeGesture)
        touchHelper.attachToRecyclerView(allPasswordsRecyclerView)

    }

    private fun setupRecyclerView() {
        allPasswordsRecyclerView.layoutManager = LinearLayoutManager(requireActivity())
        allPasswordsRecyclerView.adapter = adapter
    }

    private fun toast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onPasswordClicked(password: Passwords) {
        toast("${password.website} clicked")
    }
}