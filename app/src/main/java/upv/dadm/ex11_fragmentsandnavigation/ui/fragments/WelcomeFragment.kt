/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex11_fragmentsandnavigation.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import upv.dadm.ex11_fragmentsandnavigation.R
import upv.dadm.ex11_fragmentsandnavigation.databinding.FragmentWelcomeBinding

/**
 * Displays a welcome screen that gives access to customize an order.
 */
class WelcomeFragment : Fragment() {

    // Reference to resource binding
    private var binding: FragmentWelcomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Get the automatically generated view binding for the layout resource
        val fragmentBinding = FragmentWelcomeBinding.inflate(layoutInflater)
        // Navigate to SizeFragment for the user to select the size of the Froyo
        fragmentBinding.bWelcomeNext.setOnClickListener {
            navigateToSizeSelection()
        }

        // Hold a reference to resource binding for later use
        binding = fragmentBinding
        // Return the root element of the generated view
        return fragmentBinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        binding = null
    }

    /**
     * Notifies the activity it must navigate to the screen for size selection.
     */
    private fun navigateToSizeSelection() {
        findNavController().navigate(R.id.action_welcomeFragment_to_sizeFragment)
    }
}