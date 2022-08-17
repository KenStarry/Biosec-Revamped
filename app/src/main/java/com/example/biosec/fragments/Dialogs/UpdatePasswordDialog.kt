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
import kotlinx.android.synthetic.main.add_password_dialog.lockBtn
import kotlinx.android.synthetic.main.color_picker_dialog.*
import kotlinx.android.synthetic.main.icon_picker_dialog.*
import kotlinx.android.synthetic.main.update_password_dialog.*

class UpdatePasswordDialog(
    private val pass: Passwords

) : BottomSheetDialogFragment(),
    OnItemClickListener,
    IconClickedInterface,
    PasswordClickedInterface {

    private lateinit var viewModel: PasswordsViewModel
    private lateinit var adapter: PasswordsAdapter

    @get:JvmName("MyDialog")
    private lateinit var dialog: Dialog

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

        val view = inflater.inflate(R.layout.update_password_dialog, container, false)
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

        when (pass.passStrengthIcon) {
            R.drawable.ic_weak_pass -> {
                passColor = R.color.weak_pass
                passIcon = R.drawable.ic_weak_pass
                updatePassCheckerText.text = "Weak"
                updatePassCheckerText.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.weak_pass
                    )
                )
                updatePassCheckerIcon.setImageResource(R.drawable.ic_weak_pass)
            }

            R.drawable.ic_medium_pass -> {
                passColor = R.color.medium_pass
                passIcon = R.drawable.ic_medium_pass
                updatePassCheckerText.text = "Medium"
                updatePassCheckerText.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.medium_pass
                    )
                )
                updatePassCheckerIcon.setImageResource(R.drawable.ic_medium_pass)
            }

            R.drawable.ic_strong_pass -> {
                passColor = R.color.strong_pass
                passIcon = R.drawable.ic_strong_pass
                updatePassCheckerText.text = "Strong"
                updatePassCheckerText.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.strong_pass
                    )
                )
                updatePassCheckerIcon.setImageResource(R.drawable.ic_strong_pass)
            }

            R.drawable.ic_verified -> {
                passColor = R.color.certified_pass
                passIcon = R.drawable.ic_verified
                updatePassCheckerText.text = "Certified"
                updatePassCheckerText.setTextColor(
                    ContextCompat.getColor(
                        requireActivity(),
                        R.color.certified_pass
                    )
                )
                updatePassCheckerIcon.setImageResource(R.drawable.ic_verified)
            }
        }

        updateWebsiteInput.setText(pass.website.toString())
        updateEmailInput.setText(pass.emailAddress.toString())
        updatePasswordInput.setText(pass.password.toString())
        updateUrlInput.setText(pass.url.toString())
        updateUsernameInput.setText(pass.userName.toString())
        updatePhoneInput.setText(pass.phoneNumber.toString())
        updateSecurityQuestionInput.setText(pass.secQuestion.toString())
        updateSecurityAnswerInput.setText(pass.secAnswer.toString())
        updateDescriptionInput.setText(pass.description.toString())
        updatePickedColor.setCardBackgroundColor(
            ContextCompat.getColor(
                requireActivity(),
                pass.passColor!!
            )
        )
        updatePickedIcon.setImageResource(pass.passIcon!!)

        userColor = pass.passColor
        userIc = pass.passIcon
    }

    private fun setupListeners() {

        updatePasswordInput.addTextChangedListener(passwordStrengthCalculator)

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
        updateLockBtn.setOnClickListener {

            if (!lockedState) {
                updateLockBtn.setImageResource(R.drawable.ic__lock)
                lockedState = true
                toast("Password locked")

            } else {
                updateLockBtn.setImageResource(R.drawable.ic_lock_open)
                lockedState = false
                toast("Password unlocked")
            }
        }

        //  Display color picker
        updatePickedColor.setOnClickListener {
            displayColorDialog()
        }

        //  Display Icon Picker
        updatePickedIconHolder.setOnClickListener {
            displayIconDialog()
        }

        //  Save Button
        updateSavePassBtn.setOnClickListener {

            //  Check if all the edittexts are filled out
            if (!isEditTextEmpty()) {

                //  Add insert items to database
                val website = updateWebsiteInput.text.toString().ifBlank { requireActivity().getString(R.string.no_title) }

                val email = updateEmailInput.text.toString()
                val password = updatePasswordInput.text.toString()
                val url =
                    if (updateUrlInput.text.toString().isNotBlank() && !updateUrlInput.text.toString().startsWith("http://") && !updateUrlInput.text.toString()
                            .startsWith("https://") && !updateUrlInput.text.toString().endsWith(".com")
                    ) {
                        "https://${updateUrlInput.text}.com"
                    } else
                        updateUrlInput.text.toString()

                val username = updateUsernameInput.text.toString()
                val phone = updatePhoneInput.text.toString()
                val secQuestion = updateSecurityQuestionInput.text.toString()
                val secAnswer = updateSecurityAnswerInput.text.toString()
                val desc = updateDescriptionInput.text.toString()

                val passwordsModel = Passwords(
                    userName = username,
                    emailAddress = email,
                    password = password,
                    isLocked = lockedState,
                    passStrengthIcon = passIcon,
                    website = website,
                    passIcon = userIc,
                    passColor = userColor,
                    url = url,
                    phoneNumber = phone,
                    secQuestion = secQuestion,
                    secAnswer = secAnswer,
                    description = desc
                )

                passwordsModel.id = pass.id

                viewModel.updatePass(passwordsModel)

                viewModel.getAllPasswords().observe(requireActivity()) {
                    adapter.submitList(it)
                }

                val bottomSheetBehaviour = BottomSheetBehavior.from(requireView().parent as View)
                bottomSheetBehaviour.state = BottomSheetBehavior.STATE_HIDDEN

                toast("Password updated successfully!")

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

        updatePassCheckerText.text = strengthLevel
        updatePassCheckerText.setTextColor(ContextCompat.getColor(requireActivity(), passColor))
        updatePassCheckerIcon.setImageResource(passIcon)
    }

    private fun isEditTextEmpty(): Boolean {
        return updateWebsiteInput.text.toString().trim() == "" &&
                updateEmailInput.text.toString().trim() == "" &&
                updatePasswordInput.text.toString().trim() == ""
    }

    private fun toast(message: String) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    override fun onItemClick(userCol: Int) {

        userColor = userCol
        updatePickedColor.setCardBackgroundColor(ContextCompat.getColor(requireActivity(), userCol))
        dialog.dismiss()
    }

    override fun onIconClicked(userIcon: Int) {

        userIc = userIcon
        updatePickedIcon.setImageResource(userIcon)
        dialog.dismiss()
    }

    override fun onPasswordClicked(password: Passwords) {
        toast("${password.website} clicked")
    }
}












