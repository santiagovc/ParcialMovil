package com.ejemplo.CalculatorApp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ejemplo.CalculatorApp.databinding.FragmentDiscountBinding
import java.text.NumberFormat
import java.util.Locale

class Discount : Fragment() {

    private var _binding: FragmentDiscountBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDiscountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnCalculate.setOnClickListener {
            calculateDiscount()
        }
    }

    private fun calculateDiscount() {
        val priceStr = binding.etOriginalPrice.text.toString().trim()
        val percentStr = binding.etDiscountPercent.text.toString().trim()

        var isValid = true

        val price = priceStr.toDoubleOrNull()
        if (price == null || price <= 0) {
            binding.tilOriginalPrice.error = "Ingresa un precio mayor a 0"
            isValid = false
        } else {
            binding.tilOriginalPrice.error = null
        }

        val percent = percentStr.toDoubleOrNull()
        if (percent == null || percent < 0 || percent > 100) {
            binding.tilDiscountPercent.error = "Ingresa un porcentaje entre 0 y 100"
            isValid = false
        } else {
            binding.tilDiscountPercent.error = null
        }

        if (isValid && price != null && percent != null) {
            val discountAmount = price * (percent / 100.0)
            val finalPrice = price - discountAmount

            val currencyFormatter = NumberFormat.getCurrencyInstance(Locale("es", "CO")).apply {
                maximumFractionDigits = 0
            }

            binding.tvDiscountAmount.text = "Valor del descuento: ${currencyFormatter.format(discountAmount)}"
            binding.tvFinalPrice.text = "Total a pagar: ${currencyFormatter.format(finalPrice)}"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}