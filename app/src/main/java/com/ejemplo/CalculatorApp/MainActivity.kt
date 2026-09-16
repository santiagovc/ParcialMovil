package com.ejemplo.CalculatorApp

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ejemplo.CalculatorApp.databinding.ActivityRegisterBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnRegister.setOnClickListener {
            if (validateInputs()) {
                val name = binding.etName.text.toString().trim()
                Toast.makeText(this, "¡Bienvenido, $name!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, HomeActivity::class.java).apply {
                    putExtra("USER_NAME", name)
                }
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateInputs(): Boolean {
        var isValid = true

        val name = binding.etName.text.toString().trim()
        val email = binding.etEmail.text.toString().trim()

        if (name.isEmpty()) {
            binding.tilName.error = "Por favor, ingresa tu nombre"
            isValid = false
        } else {
            binding.tilName.error = null
        }

        if (email.isEmpty()) {
            binding.tilEmail.error = "Por favor, ingresa tu correo"
            isValid = false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.tilEmail.error = "Ingresa un correo electrónico válido"
            isValid = false
        } else {
            binding.tilEmail.error = null
        }

        return isValid
    }
}