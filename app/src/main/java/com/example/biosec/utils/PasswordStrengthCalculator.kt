package com.example.biosec.utils

import android.text.Editable
import android.text.TextWatcher
import androidx.lifecycle.MutableLiveData
import com.example.biosec.R
import java.util.regex.Matcher
import java.util.regex.Pattern

class PasswordStrengthCalculator : TextWatcher {

    var strengthLevel: MutableLiveData<String> = MutableLiveData()
    var strengthColor: MutableLiveData<Int> = MutableLiveData()
    var strengthIcon: MutableLiveData<Int> = MutableLiveData()

    var lowercase: MutableLiveData<Int> = MutableLiveData(0)
    var uppercase: MutableLiveData<Int> = MutableLiveData(0)
    var digit: MutableLiveData<Int> = MutableLiveData(0)
    var specialChar: MutableLiveData<Int> = MutableLiveData(0)

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
    }

    override fun onTextChanged(char: CharSequence?, p1: Int, p2: Int, p3: Int) {
        if (char != null) {

            lowercase.value = if (char.hasLowerCase()) 1 else 0
            uppercase.value = if (char.hasUpperCase()) 1 else 0
            digit.value = if (char.hasDigit()) 1 else 0
            specialChar.value = if (char.hasSpecialChar()) 1 else 0

            calculateStrength(char)
        }
    }

    private fun calculateStrength(password: CharSequence?) {

        //  Weak password if length is 0 - 7
        if (password?.length in 0..7) {
            strengthColor.value = R.color.weak_pass
            strengthLevel.value = "Weak"
            strengthIcon.value = R.drawable.ic_weak_pass

        } else if (password?.length in 8..10) {

            //  it has to at least contain some special characters
            if (lowercase.value == 1 || uppercase.value == 1 || digit.value == 1 || specialChar.value == 1) {
                strengthColor.value = R.color.medium_pass
                strengthLevel.value = "Medium"
                strengthIcon.value = R.drawable.ic_medium_pass
            }
        } else if (password?.length in 11..16) {

            //  Must contain upper and lowercase values
            if (lowercase.value == 1 || uppercase.value == 1 || digit.value == 1 || specialChar.value == 1) {
                if (lowercase.value == 1 && uppercase.value == 1 && specialChar.value == 1) {
                    strengthColor.value = R.color.strong_pass
                    strengthLevel.value = "Strong"
                    strengthIcon.value = R.drawable.ic_strong_pass
                }
            }
        } else if (password?.length!! > 16) {

            //  Must contain everything for it to be certified
            if (lowercase.value == 1 && uppercase.value == 1 && digit.value == 1 && specialChar.value == 1) {
                strengthColor.value = R.color.certified_pass
                strengthLevel.value = "Certified"
                strengthIcon.value = R.drawable.ic_verified
            }
        }
    }

    override fun afterTextChanged(p0: Editable?) {
    }

    //  EXTENSION FUNCTIONS

    private fun CharSequence.hasLowerCase(): Boolean {

        val pattern: Pattern = Pattern.compile("[a-z]")
        val hasLowerCase: Matcher = pattern.matcher(this)

        return hasLowerCase.find()
    }

    private fun CharSequence.hasUpperCase(): Boolean {

        val pattern: Pattern = Pattern.compile("[A-Z]")
        val hasUpperCase: Matcher = pattern.matcher(this)

        return hasUpperCase.find()
    }

    private fun CharSequence.hasDigit(): Boolean {

        val pattern: Pattern = Pattern.compile("[0-9]")
        val hasDigit: Matcher = pattern.matcher(this)

        return hasDigit.find()
    }

    private fun CharSequence.hasSpecialChar(): Boolean {

        val pattern: Pattern = Pattern.compile("[!@#$%^&*()<>?{}\\[\\]~`.,';+-=/]")
        val hasSpecialChar: Matcher = pattern.matcher(this)

        return hasSpecialChar.find()
    }
}






