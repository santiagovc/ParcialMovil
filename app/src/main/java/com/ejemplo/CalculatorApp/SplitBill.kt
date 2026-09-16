package com.ejemplo.CalculatorApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ejemplo.CalculatorApp.databinding.FragmentSplitBillBinding
import java.text.NumberFormat
import java.util.Locale

class SplitBill : Fragment() {

    private var _binding: FragmentSplitBillBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplitBillBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalculateSplit.setOnClickListener {
            calculateSplit()
        }
    }

    private fun calculateSplit() {
        val billStr = binding.etBillTotal.text.toString().trim()
        val peopleStr = binding.etPeopleCount.text.toString().trim()
        val tipStr = binding.etTipPercent.text.toString().trim()

        var isValid = true

        val billTotal = billStr.toDoubleOrNull()
        if (billTotal == null || billTotal <= 0) {
            binding.tilBillTotal.error = "Ingresa un valor mayor a 0"
            isValid = false
        } else {
            binding.tilBillTotal.error = null
        }

        val peopleCount = peopleStr.toIntOrNull()
        if (peopleCount == null || peopleCount <= 0) {
            binding.tilPeopleCount.error = "El número de personas debe ser mayor que 0"
            isValid = false
        } else {
            binding.tilPeopleCount.error = null
        }

        val tipPercent = tipStr.toDoubleOrNull() ?: 0.0
        if (tipPercent < 0) {
            binding.tilTipPercent.error = "La propina no puede ser negativa"
            isValid = false
        } else {
            binding.tilTipPercent.error = null
        }

        if (isValid && billTotal != null && peopleCount != null) {
            val tipAmount = billTotal * (tipPercent / 100.0)
            val totalWithTip = billTotal + tipAmount
            val perPerson = totalWithTip / peopleCount

            val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply {
                maximumFractionDigits = 0
            }

            binding.tvTipAmount.text = "Valor de propina: ${currencyFormatter.format(tipAmount)}"
            binding.tvTotalWithTip.text = "Total con propina: ${currencyFormatter.format(totalWithTip)}"
            binding.tvPerPersonAmount.text = "Cada persona paga: ${currencyFormatter.format(perPerson)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}