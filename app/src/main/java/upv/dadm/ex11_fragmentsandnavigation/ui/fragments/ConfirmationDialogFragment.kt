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
import androidx.navigation.fragment.findNavController
import upv.dadm.ex11_fragmentsandnavigation.R

/**
 * Displays a dialog to ask the user for confirmation before cancelling the current order.
 */
class ConfirmationDialogFragment : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        // Create the desired dialog
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.dialog_title)
            .setMessage(R.string.dialog_message)
            .setPositiveButton(R.string.dialog_yes) { _, _ ->
                // Yes, the user wants to cancel the order
                findNavController().previousBackStackEntry?.savedStateHandle?.set(
                    CANCEL_ORDER_KEY,
                    true
                )
                dismiss()
            }
            .setNegativeButton(R.string.dialog_no) { _, _ ->
                dismiss()
            }
            .create()
    }

}