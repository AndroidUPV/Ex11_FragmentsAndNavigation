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
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import upv.dadm.ex11_fragmentsandnavigation.R
import upv.dadm.ex11_fragmentsandnavigation.databinding.FragmentSauceBinding
import upv.dadm.ex11_fragmentsandnavigation.ui.viewmodels.FroyoViewModel

/**
 * Displays a screen that lets the user select the sauce for the Froyo.
 * The user can proceed to the checkout or cancel the order.
 */
class SauceFragment : Fragment(R.layout.fragment_sauce) {

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentSauceBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentSauceBinding.bind(view)
        // Set the sauce of the custom Froyo to chocolate
        binding.rbChocolate.setOnClickListener {
            setSauce(binding.rbChocolate.text.toString())
        }
        // Set the sauce of the custom Froyo to forest fruits
        binding.rbFruit.setOnClickListener {
            setSauce(binding.rbFruit.text.toString())
        }
        // Set the sauce of the custom Froyo to honey
        binding.rbHoney.setOnClickListener {
            setSauce(binding.rbHoney.text.toString())
        }
        // Set the sauce of the custom Froyo to mango
        binding.rbMango.setOnClickListener {
            setSauce(binding.rbMango.text.toString())
        }

        // Cancel the order and navigate to the Welcome screen
        binding.bSauceCancel.setOnClickListener { cancel() }
        // Navigate to CheckoutFragment for the user to submit the order
        binding.bSauceNext.setOnClickListener { proceedToCheckout() }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Set the selected sauce according to the state in the ViewModel
                viewModel.froyoUiState.collect { froyo ->
                    when (froyo.sauce) {
                        getString(R.string.chocolate) -> binding.rbChocolate.isChecked = true
                        getString(R.string.fruits) -> binding.rbFruit.isChecked = true
                        getString(R.string.honey) -> binding.rbHoney.isChecked = true
                        getString(R.string.mango) -> binding.rbMango.isChecked = true
                    }
                    // Enable the Button to proceed to the next screen when a sauce has been selected
                    binding.bSauceNext.isEnabled = froyo.sauce.isNotEmpty()
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
     * Updates the sauce of the selected Froyo in the ViewModel.
     */
    private fun setSauce(sauce: String) = viewModel.setSauce(sauce)

    /**
     * Navigates to the screen for checkout.
     */
    private fun proceedToCheckout() = findNavController().navigate(R.id.actionProceedToCheckout)

    /**
     * Clears the state in the ViewModel and navigates to the welcome screen.
     */
    private fun cancel() {
        viewModel.resetOrder()
        findNavController().navigate(R.id.actionBackToWelcome)
    }
}