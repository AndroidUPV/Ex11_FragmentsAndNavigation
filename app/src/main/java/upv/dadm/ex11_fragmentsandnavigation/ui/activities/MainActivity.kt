/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex11_fragmentsandnavigation.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import upv.dadm.ex11_fragmentsandnavigation.databinding.ActivityMainBinding

/**
 * Let users customize their Froyo (size, toppings, and sauce) and place their order.
 */
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        val binding = ActivityMainBinding.inflate(layoutInflater)
        // Set the activity content to the root element of the generated view
        setContentView(binding.root)
    }

}