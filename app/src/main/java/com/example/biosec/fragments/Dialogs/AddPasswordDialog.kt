package com.example.biosec.fragments.Dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.biosec.R
import com.example.biosec.adapters.ColorPickerAdapter
import com.example.biosec.adapters.IconPickerAdapter
import com.example.biosec.adapters.PasswordsAdapter
import com.example.biosec.entities.Passwords
import com.example.biosec.interfaces.IconClickedInterface
import com.example.biosec.utils.PasswordStrengthCalculator
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_password_dialog.*
import kotlinx.android.synthetic.main.color_picker_dialog.*
import kotlinx.android.synthetic.main.icon_picker_dialog.*

class AddPasswordDialog : BottomSheetDialogFragment(),
    ColorPickerAdapter.OnItemClickListener,
    IconClickedInterface {

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var adapter: PasswordsAdapter

    @get:JvmName("MyDialog")
    private lateinit var dialog: Dialog

    private lateinit var websiteInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private lateinit var savePassBtn: TextView
    private lateinit var passCheckerText: TextView
    private lateinit var passCheckerIcon: ImageView
    private lateinit var lockBtn: ImageButton

    private var userColor = R.color.medium_pass
    private var userIc = R.drawable.ic_dashboard

    private var passColor: Int = R.color.weak_pass
    private var passIcon: Int = R.drawable.ic_weak_pass
    private val passwordStrengthCalculator = PasswordStrengthCalculator()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.add_password_dialog, container, false)
        setStyle(DialogFragment.STYLE_NORMAL, R.style.MyBottomSheetDialogTheme)


        return view
    }

    override fun onStart() {
        super.onStart()

        val bottomSheetBehaviour = BottomSheetBehavior.from(requireView().parent as View)
        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED

        initializeVariables(requireView().parent as View)
        setupListeners()

    }

    private fun initializeVariables(view: View) {

        viewModel = ViewModelProvider(requireActivity()).get(PasswordsViewModel::class.java)
        adapter = PasswordsAdapter(requireContext())
        dialog = Dialog(requireActivity())

        websiteInput = view.findViewById(R.id.websiteInput)
        emailInput = view.findViewById(R.id.emailInput)
        passwordInput = view.findViewById(R.id.passwordInput)
        savePassBtn = view.findViewById(R.id.savePassBtn)
        passCheckerText = view.findViewById(R.id.passCheckerText)
        passCheckerIcon = view.findViewById(R.id.passCheckerIcon)
        lockBtn = view.findViewById(R.id.lockBtn)


    }

    private fun setupListeners() {

        passwordInput.addTextChangedListener(passwordStrengthCalculator)

        //  Observers for password calculator
        passwordStrengthCalculator.strengthLevel.observe(this, Observer { strengthLevel ->
            displayStrengthLevel(strengthLevel)
        })

        passwordStrengthCalculator.strengthColor.observe(this, Observer { strengthColor ->
            passColor = strengthColor
        })

        passwordStrengthCalculator.strengthIcon.observe(this, Observer { strengthIcon ->
            passIcon = strengthIcon
        })


        var lockedState = false
        lockBtn.setOnClickListener {

            if (!lockedState) {
                lockBtn.setImageResource(R.drawable.ic__lock)
                lockedState = true
                toast("Password locked")

            } else {
                lockBtn.setImageResource(R.drawable.ic_lock_open)
                lockedState = false
                toast("Password unlocked")
            }
        }

        //  Display color picker
        pickedColor.setOnClickListener {
            displayColorDialog()
        }

        //  Display Icon Picker
        pickedIcon.setOnClickListener {
            displayIconDialog()
        }

        savePassBtn.setOnClickListener {

            //  Check if all the edittexts are filled out
            if (!isEditTextEmpty()) {

                //  Add insert items to database
                val website = websiteInput.text.toString()
                val email = emailInput.text.toString()
                val password = passwordInput.text.toString()

                viewModel.insertPass(
                    Passwords(
                        userName = "Someone",
                        emailAddress = email,
                        password = password,
                        isLocked = lockedState,
                        passStrengthIcon = passIcon,
                        website = website,
                        passIcon = userIc,
                        passColor = userColor
                    )
                )

                viewModel.getAllPasswords().observe(requireActivity()) {
                    adapter.submitList(it)
                }

                val bottomSheetBehaviour = BottomSheetBehavior.from(requireView().parent as View)
                bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN

                toast("Entry Added")

            } else {
                toast("Fields cannot be empty")
            }
        }
    }

    private fun displayIconDialog() {

        dialog.setContentView(R.layout.icon_picker_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val recyclerView = dialog.pickIconRecyclerView
        val adapter = IconPickerAdapter(requireActivity(), this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            GridLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false)

        dialog.show()
    }

    //  Color Picker Dialog
    private fun displayColorDialog() {

        dialog.setContentView(R.layout.color_picker_dialog)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        val colRecyclerView = dialog.pickColorRecyclerView
        val adapter = ColorPickerAdapter(requireActivity(), this)

        colRecyclerView.adapter = adapter
        colRecyclerView.layoutManager =
            GridLayoutManager(
                requireActivity(),
                4,
                GridLayoutManager.VERTICAL,
                false
            )

        dialog.show()

    }

    private fun displayStrengthLevel(strengthLevel: String) {

        passCheckerText.text = strengthLevel
        passCheckerText.setTextColor(ContextCompat.getColor(requireActivity(), passColor))
        passCheckerIcon.setImageResource(passIcon)
    }

    private fun isEditTextEmpty(): Boolean {
        return websiteInput.text.toString().trim() == "" &&
                emailInput.text.toString().trim() == "" &&
                passwordInput.text.toString().trim() == ""
    }

    private fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(userCol: Int) {

        userColor = userCol
        pickedColor.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), userCol))
        dialog.dismiss()
    }

    override fun onIconClicked(userIcon: Int) {

        userIc = userIcon
        pickedIcon.setImageResource(userIcon)
        dialog.dismiss()
    }
}












