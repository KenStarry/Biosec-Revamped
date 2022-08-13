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
import com.example.biosec.interfaces.OnItemClickListener
import com.example.biosec.interfaces.PasswordClickedInterface
import com.example.biosec.utils.PasswordStrengthCalculator
import com.example.biosec.viewmodels.PasswordsViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.add_password_dialog.*
import kotlinx.android.synthetic.main.color_picker_dialog.*
import kotlinx.android.synthetic.main.icon_picker_dialog.*

class AddPasswordDialog : BottomSheetDialogFragment(),
    OnItemClickListener,
    IconClickedInterface,
PasswordClickedInterface{

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

    private val socialIconsArray = intArrayOf(
        R.drawable.airtel_logo, R.drawable.amazon_logo, R.drawable.apple_logo,
        R.drawable.cisco_logo, R.drawable.discord_logo, R.drawable.disney_logo,
        R.drawable.facebook_logo, R.drawable.gmail_logo, R.drawable.google_logo,
        R.drawable.hp_logo, R.drawable.instagram_logo, R.drawable.intel_logo,
        R.drawable.lenovo_logo, R.drawable.linkedin_logo, R.drawable.louis_vuitton_logo,
        R.drawable.mastercard_logo, R.drawable.microsoft_logo, R.drawable.nike_logo,
        R.drawable.oracle_logo, R.drawable.pepsi_logo, R.drawable.safaricom_logo,
        R.drawable.samsung_logo, R.drawable.snapchat_logo, R.drawable.telegram_logo,
        R.drawable.telkom_logo, R.drawable.tiktok_logo, R.drawable.twitter_logo,
        R.drawable.visa_logo, R.drawable.windows_defender_logo
    )

    private val iconsArray = intArrayOf(
        R.drawable.user_ic_wash, R.drawable.user_ic_water, R.drawable.user_ic_webhook,
        R.drawable.ic__lock, R.drawable.ic_dashboard, R.drawable.ic_lock_open,
        R.drawable.ic_archive, R.drawable.ic_delete_forever, R.drawable.ic_grain
    )

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
        adapter = PasswordsAdapter(requireContext(), this)
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
        pickedIconHolder.setOnClickListener {
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
        val socialIconsRecyclerView = dialog.socialIconsRecyclerView

        val adapter = IconPickerAdapter(requireActivity(), iconsArray, this)
        val socialAdapter = IconPickerAdapter(requireActivity(), socialIconsArray, this)

        recyclerView.adapter = adapter
        recyclerView.layoutManager =
            GridLayoutManager(requireActivity(), 4, GridLayoutManager.VERTICAL, false)

        socialIconsRecyclerView.adapter = socialAdapter
        socialIconsRecyclerView.layoutManager =
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

    override fun onPasswordClicked(password: Passwords) {
        toast("${password.website} clicked")
    }
}












