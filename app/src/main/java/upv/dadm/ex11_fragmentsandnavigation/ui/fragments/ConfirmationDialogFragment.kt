/*
 * Copyright (c) 2022
 * David de Andrés and Juan Carlos Ruiz
 * Development of apps for mobile devices
 * Universitat Politècnica de València
 */

package upv.dadm.ex11_fragmentsandnavigation.ui.fragments

import android.app.Dialog
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import upv.dadm.ex11_fragmentsandnavigation.R
import upv.dadm.ex11_fragmentsandnavigation.ui.viewmodels.FroyoViewModel

/**
 * Displays a dialog to ask the user for confirmation before cancelling the current order.
 */
class ConfirmationDialogFragment : DialogFragment() {

    private val viewModel: FroyoViewModel by activityViewModels()

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create the desired dialog
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_yes) { _, _ ->
                // Yes, the user wants to cancel the order
                viewModel.resetOrder()
                findNavController().navigate(R.id.actionBackToWelcome)
            }
            .setNegativeButton(R.string.dialog_no) { _, _ ->
                // No, the user wants to keep the order
                findNavController().popBackStack()
            }
            .create()
    }

}