package com.kdb.grocer.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.core.view.MenuProvider
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputLayout
import com.kdb.grocer.R
import com.kdb.grocer.data.GroceryRoomDatabase
import com.kdb.grocer.databinding.FragmentAddItemBinding

class AddItemFragment : Fragment(), MenuProvider {

    private lateinit var binding: FragmentAddItemBinding

    private val viewModel: AddItemViewModel by viewModels {
        AddItemViewModelFactory(
            GroceryRoomDatabase.getDatabase(requireContext().applicationContext).groceryDao()
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddItemBinding.inflate(inflater, container, false)

        // Set up the options menu
        requireActivity().addMenuProvider(this, viewLifecycleOwner)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Focus the first input field
        binding.textInputName.requestFocus()
        val imm = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.showSoftInput(binding.editTextName, InputMethodManager.SHOW_IMPLICIT)

        // Observe the input errors
        observeErrors()

        // Remove errors when user starts typing
        removeErrorsWhenTyping()
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.menu_add_item, menu)
    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.action_save -> {
                onSaveItem()
                return true
            }
            else -> return false
        }
    }

    private fun observeErrors() = with(binding) {
        viewModel.nameEmpty.observe(viewLifecycleOwner) {
            textInputName.error = it.errorStringOrNull()
        }

        viewModel.quantityEmpty.observe(viewLifecycleOwner) {
            textInputQuantity.error = it.errorStringOrNull()
        }

        viewModel.priceEmpty.observe(viewLifecycleOwner) {
            textInputPrice.error = it.errorStringOrNull()
        }
    }

    private fun removeErrorsWhenTyping() = with(binding) {
        textInputName.hideErrorOnTyping()
        textInputQuantity.hideErrorOnTyping()
        textInputPrice.hideErrorOnTyping()
    }

    private fun onSaveItem() {
        with(binding) {
            val name = editTextName.text.toString()
            val quantity = editTextQuantity.text.toString()
            val price = editTextPrice.text.toString()

            // Validate the input data
            if (viewModel.validate(name, quantity, price)) {
                viewModel.saveItem(name, quantity, price)
                findNavController().navigateUp()
            }
        }
    }

    private fun TextInputLayout.hideErrorOnTyping() {
        editText?.addTextChangedListener { error = null }
    }

    private fun Boolean.errorStringOrNull(): String? {
        return if (this) getString(R.string.error_required) else null
    }
}