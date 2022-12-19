package com.joel.projectnotes.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.joel.projectnotes.MyApplication
import com.joel.projectnotes.R
import com.joel.projectnotes.databinding.FragmentAddItemBinding
import com.joel.projectnotes.models.Task
import com.joel.projectnotes.viewModels.AddTaskViewModel

class AddItemFragment : Fragment() {
    private lateinit var binding: FragmentAddItemBinding
    private val viewModel: AddTaskViewModel by viewModels {
        AddTaskViewModel.Provider((requireContext().applicationContext as MyApplication).taskRepo)
    }
    private lateinit var color: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAddItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.run {
            color = "#96DC02"
            rlAddItem.setBackgroundColor(Color.parseColor(color))
            viewAddGreen.requestFocus()
            viewAddGreen.setOnClickListener {
                color = "#96DC02"
                rlAddItem.setBackgroundColor(Color.parseColor(color))
                it.requestFocus()
            }
            viewAddBlue.setOnClickListener {
                color = "#00D4F1"
                rlAddItem.setBackgroundColor(Color.parseColor(color))
                it.requestFocus()
            }
            viewAddRed.setOnClickListener {
                color = "#FF4C4C"
                rlAddItem.setBackgroundColor(Color.parseColor(color))
                it.requestFocus()
            }
            viewAddPurple.setOnClickListener {
                color = "#AB00C7"
                rlAddItem.setBackgroundColor(Color.parseColor(color))
                it.requestFocus()
            }
            viewAddYellow.setOnClickListener {
                color = "#FFD232"
                rlAddItem.setBackgroundColor(Color.parseColor(color))
                it.requestFocus()
            }
        }

        binding.btnAddItem.setOnClickListener {
            val title = binding.etAddTitle.text.toString()
            val description = binding.etAddDescription.text.toString()

            if (validate(title, description)) {
                viewModel.addTask(Task(null, title, description, color))
                val bundle = Bundle()
                bundle.putBoolean("refresh", true)
                setFragmentResult("from_add_item", bundle)
                NavHostFragment.findNavController(this).popBackStack()
            } else {
                Snackbar.make(
                    binding.root,
                    "Note title and description cannot be empty",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.btnAddBack.setOnClickListener {
            NavHostFragment.findNavController(this).popBackStack()
        }
    }

    private fun validate(vararg list: String): Boolean {
        for (field in list) {
            if (field.isEmpty()) {
                return false
            }
        }
        return true
    }
}