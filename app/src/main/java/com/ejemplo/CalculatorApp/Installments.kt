package com.ejemplo.CalculatorApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

class Installments : Fragment() {

    private lateinit var tilPurchaseAmount: TextInputLayout
    private lateinit var tilInterestRate: TextInputLayout
    private lateinit var tilInstallmentsCount: TextInputLayout

    private lateinit var etPurchaseAmount: TextInputEditText
    private lateinit var etInterestRate: TextInputEditText
    private lateinit var etInstallmentsCount: TextInputEditText

    private lateinit var btnCalculate: Button
    private lateinit var tvMonthlyFee: TextView
    private lateinit var tvTotalPaid: TextView
    private lateinit var tvTotalInterest: TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_installments, container, false)

        tilPurchaseAmount = view.findViewById(R.id.tilPurchaseAmount)
        tilInterestRate = view.findViewById(R.id.tilInterestRate)
        tilInstallmentsCount = view.findViewById(R.id.tilInstallmentsCount)

        etPurchaseAmount = view.findViewById(R.id.etPurchaseAmount)
        etInterestRate = view.findViewById(R.id.etInterestRate)
        etInstallmentsCount = view.findViewById(R.id.etInstallmentsCount)

        btnCalculate = view.findViewById(R.id.btnCalculateInstallments)
        tvMonthlyFee = view.findViewById(R.id.tvMonthlyFee)
        tvTotalPaid = view.findViewById(R.id.tvTotalPaid)
        tvTotalInterest = view.findViewById(R.id.tvTotalInterest)

        btnCalculate.setOnClickListener {
            calculateInstallments()
        }

        return view
    }

    private fun calculateInstallments() {
        val amountStr = etPurchaseAmount.text.toString().trim()
        val interestStr = etInterestRate.text.toString().trim()
        val countStr = etInstallmentsCount.text.toString().trim()

        tilPurchaseAmount.error = null
        tilInterestRate.error = null
        tilInstallmentsCount.error = null

        var hasError = false

        if (amountStr.isEmpty() || amountStr.toDoubleOrNull() == null || amountStr.toDouble() <= 0) {
            tilPurchaseAmount.error = getString(R.string.error_invalid_amount)
            hasError = true
        }

        val interestRate = interestStr.toDoubleOrNull() ?: 0.0
        if (interestStr.isNotEmpty() && interestRate < 0) {
            tilInterestRate.error = getString(R.string.error_invalid_interest)
            hasError = true
        }

        val installments = countStr.toIntOrNull() ?: 0
        if (countStr.isEmpty() || installments < 1) {
            tilInstallmentsCount.error = getString(R.string.error_invalid_installments)
            hasError = true
        }

        if (hasError) return

        val amount = amountStr.toDouble()
        val monthlyInterest = interestRate / 100.0

        val monthlyFee: Double
        val totalPaid: Double
        val totalInterest: Double

        if (monthlyInterest == 0.0) {
            // Sin interés
            monthlyFee = amount / installments
            totalPaid = amount
            totalInterest = 0.0
        } else {
            monthlyFee = amount * (monthlyInterest * (1 + monthlyInterest).pow(installments)) /
                    ((1 + monthlyInterest).pow(installments) - 1)
            totalPaid = monthlyFee * installments
            totalInterest = totalPaid - amount
        }

        val formatter = NumberFormat.getCurrencyInstance(Locale.forLanguageTag("es-CO")).apply {
            maximumFractionDigits = 0
        }

        tvMonthlyFee.text = getString(R.string.result_monthly_fee, formatter.format(monthlyFee))
        tvTotalPaid.text = getString(R.string.result_total_paid, formatter.format(totalPaid))
        tvTotalInterest.text = getString(R.string.result_total_interest, formatter.format(totalInterest))
    }
}