package com.example.quizonline

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.quizonline.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaberMais.setOnClickListener {
            val intent = Intent(this, OdsInfoActivity::class.java)
            startActivity(intent)
        }

        binding.btnJogar.setOnClickListener {
            startActivity(Intent(this, CategoriasActivity::class.java))
        }
    }
}
