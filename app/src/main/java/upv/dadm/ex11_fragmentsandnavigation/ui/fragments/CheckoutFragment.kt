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
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import upv.dadm.ex11_fragmentsandnavigation.R
import upv.dadm.ex11_fragmentsandnavigation.databinding.FragmentCheckoutBinding
import upv.dadm.ex11_fragmentsandnavigation.ui.viewmodels.FroyoViewModel

const val CANCEL_ORDER_KEY = "upv.dadm.ex11_fragmentsandnavigation.ui.fragments.CANCEL_ORDER_KEY"

/**
 * Displays a screen that lets the user submit or cancel the order.
 */
class CheckoutFragment : Fragment(R.layout.fragment_checkout) {

    // Reference to a ViewModel shared between Fragments
    private val viewModel: FroyoViewModel by activityViewModels()

    // Backing property to resource binding
    private var _binding: FragmentCheckoutBinding? = null

    // Property valid between onCreateView() and onDestroyView()
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Get the automatically generated view binding for the layout resource
        _binding = FragmentCheckoutBinding.bind(view)
        // Display a dialog to ask the user for confirmation before cancelling the order
        binding.bCancel.setOnClickListener { displayConfirmationDialog() }
        // Submit the order and navigate to the Welcome screen
        binding.bSubmit.setOnClickListener { submitOrder() }

        // Get the current NavBackStackEntry. As a dialog could be shown,
        // use getBackStackEntry() with this Fragment's ID instead of
        // currentBackStackEntry, which will return that of the DialogFragment
        val navBackStackEntry = findNavController().getBackStackEntry(R.id.checkoutFragment)
        // Create the observer
        val observer = LifecycleEventObserver { _, event ->
            // It will only trigger if the Fragment is interactive (no Dialog on top) and
            // contains a given key (result provided by the DialogFragment)
            if (event == Lifecycle.Event.ON_RESUME &&
                navBackStackEntry.savedStateHandle.contains(CANCEL_ORDER_KEY)
            ) {
                // Get the result provided by the DialogFragment
                if (navBackStackEntry.savedStateHandle.get<Boolean>(CANCEL_ORDER_KEY) == true) {
                    // Cancel the order and reset the result provided by the DialogFragment
                    cancelOrder()
                    navBackStackEntry.savedStateHandle[CANCEL_ORDER_KEY] = false
                }
            }
        }
        // Add the observer to the NavBackStackEntry's lifecycle
        navBackStackEntry.lifecycle.addObserver(observer)
        // addObserver() does not automatically remove the observer,
        // so it must be manually done when the view lifecycle is destroyed
        viewLifecycleOwner.lifecycle.addObserver(LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_DESTROY) {
                navBackStackEntry.lifecycle.removeObserver(observer)
            }
        })

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                // Display the selected size, topping, and sauce according to the state in the ViewModel
                viewModel.froyoUiState.collect { froyo ->
                    binding.tvCheckoutSize.text = getString(R.string.checkout_size, froyo.size)
                    binding.tvCheckoutTopping.text =
                        getString(R.string.checkout_toppings, froyo.topping)
                    binding.tvCheckoutSauce.text = getString(R.string.checkout_sauce, froyo.sauce)
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
     * Clears the state in the ViewModel and navigates to the welcome screen.
     * It has the same effect as cancel(), but this is supposed to actually submit the order.
     */
    private fun submitOrder() {
        viewModel.resetOrder()
        findNavController().navigate(R.id.actionBackToWelcome)
    }

    /**
     * Clears the state in the ViewModel and navigates to the welcome screen.
     */
    private fun cancelOrder() {
        viewModel.resetOrder()
        findNavController().navigate(R.id.actionBackToWelcome)
    }

    /**
     * Displays a dialog asking the user for confirmation before cancelling the order.
     */
    private fun displayConfirmationDialog() =
        findNavController().navigate(R.id.actionDisplayConfirmationDialog)

}