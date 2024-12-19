/*
 * Copyright (c) 2022-2024 Universitat Politècnica de València
 * Authors: David de Andrés and Juan Carlos Ruiz
 *          Fault-Tolerant Systems
 *          Instituto ITACA
 *          Universitat Politècnica de València
 *
 * Distributed under MIT license
 * (See accompanying file LICENSE.txt)
 */

package upv.dadm.ex11_fragmentsandnavigation.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import upv.dadm.ex11_fragmentsandnavigation.R
import upv.dadm.ex11_fragmentsandnavigation.databinding.FragmentWelcomeBinding

/**
 * Displays a welcome screen that gives access to customize an order.
 */
class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    // Instance of Fragment's arguments
    private val args: WelcomeFragmentArgs by navArgs()

    // Backing property to resource binding
    private var _binding: FragmentWelcomeBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentWelcomeBinding.bind(view)
        // Navigate to SizeFragment for the user to select the size of the Froyo
        binding.bWelcomeNext.setOnClickListener {
            selectSize()
        }
        // Customize the welcome message using the received argument
        binding.tvWelcome.text = getString(R.string.welcome, args.userName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }

    /**
     * Navigates to the screen for size selection.
     */
    private fun selectSize() = findNavController().navigate(R.id.actionSelectSize)
}