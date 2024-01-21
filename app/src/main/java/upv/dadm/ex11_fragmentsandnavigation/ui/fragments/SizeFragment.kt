/*
 * Copyright (c) 2022-2023 Universitat Politècnica de València
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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import upv.dadm.ex11_fragmentsandnavigation.R
import upv.dadm.ex11_fragmentsandnavigation.databinding.FragmentSizeBinding
import upv.dadm.ex11_fragmentsandnavigation.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the size of the Froyo.
 * The user can proceed to select the desired toppings or cancel the order.
 */
class SizeFragment : Fragment(R.layout.fragment_size) {

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentSizeBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentSizeBinding.bind(view)
        // Set the size of the custom Froyo to small
        binding.rbSmall.setOnClickListener {
            setSize(binding.rbSmall.text.toString())
        }
        // Set the size of the custom Froyo to medium
        binding.rbMedium.setOnClickListener {
            setSize(binding.rbMedium.text.toString())
        }
        // Set the size of the custom Froyo to large
        binding.rbLarge.setOnClickListener {
            setSize(binding.rbLarge.text.toString())
        }
        // Set the size of the custom Froyo to extra large
        binding.rbExtraLarge.setOnClickListener {
            setSize(binding.rbExtraLarge.text.toString())
        }

        // Cancel the order and navigate to the Welcome screen
        binding.bSizeCancel.setOnClickListener { cancel() }
        // Navigate to ToppingsFragment for the user to select the toppings of the Froyo
        binding.bSizeNext.setOnClickListener { selectToppings() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Set the selected size according to the state in the ViewModel
                viewModel.froyoUiState.collect { froyo ->
                    when (froyo.size) {
                        getString(R.string.size_small) -> binding.rbSmall.isChecked = true
                        getString(R.string.size_medium) -> binding.rbMedium.isChecked = true
                        getString(R.string.size_large) -> binding.rbLarge.isChecked = true
                        getString(R.string.size_extra_large) -> binding.rbExtraLarge.isChecked = true
                    }
                    // Enable the Button to proceed to the next screen when a size has been selected
                    binding.bSizeNext.isEnabled = froyo.size.isNotEmpty()
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Clear resources to make them eligible for garbage collection
        _binding = null
    }

    /**
     * Updates the size of the selected Froyo in the ViewModel.
     */
    private fun setSize(size: String) = viewModel.setSize(size)

    /**
     * Navigates to the screen for toppings selection.
     */
    private fun selectToppings() = findNavController().navigate(R.id.actionSelectToppings)

    /**
     * Clears the state in the ViewModel and navigates to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        findNavController().navigate(R.id.actionBackToWelcome)
    }
}