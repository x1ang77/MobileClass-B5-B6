package com.joel.projectnotes.ui

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.NavHost
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.joel.projectnotes.MyApplication
import com.joel.projectnotes.R
import com.joel.projectnotes.databinding.FragmentEditItemBinding
import com.joel.projectnotes.models.Task
import com.joel.projectnotes.viewModels.EditTaskViewModel

class EditItemFragment : Fragment() {
    private lateinit var binding: FragmentEditItemBinding
    private val viewModel: EditTaskViewModel by viewModels {
        EditTaskViewModel.Provider(
            (requireContext().applicationContext as MyApplication).taskRepo
        )
    }
    private lateinit var color: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: EditItemFragmentArgs by navArgs()

        viewModel.getTaskById(navArgs.id)

        viewModel.task.observe(viewLifecycleOwner) {
            binding.run {
                color = it.color
                etEditTitle.setText(it.title)
                etEditDescription.setText(it.description)
                rlEditItem.setBackgroundColor(Color.parseColor(it.color))
                viewEditGreen.setOnClickListener {
                    color = "#96DC02"
                    rlEditItem.setBackgroundColor(Color.parseColor(color))
                }
                viewEditBlue.setOnClickListener {
                    color = "#00D4F1"
                    rlEditItem.setBackgroundColor(Color.parseColor(color))
                }
                viewEditRed.setOnClickListener {
                    color = "#FF4C4C"
                    rlEditItem.setBackgroundColor(Color.parseColor(color))
                }
                viewEditPurple.setOnClickListener {
                    color = "#AB00C7"
                    rlEditItem.setBackgroundColor(Color.parseColor(color))
                }
                viewEditYellow.setOnClickListener {
                    color = "#FFD232"
                    rlEditItem.setBackgroundColor(Color.parseColor(color))
                }
            }
        }

        binding.btnEditEdit.setOnClickListener {
            val id = navArgs.id
            val title = binding.etEditTitle.text.toString()
            val description = binding.etEditDescription.text.toString()

            if (validate(title, description)) {
                val task = Task(id, title, description, color)
                viewModel.updateTask(navArgs.id, task)
                NavHostFragment.findNavController(this).popBackStack()
            } else {
                Snackbar.make(
                    binding.root,
                    "Note title and description cannot be empty",
                    Snackbar.LENGTH_LONG
                ).show()
            }
        }

        binding.btnEditDelete.setOnClickListener {
            viewModel.deleteTask(navArgs.id)
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("from_edit", bundle)
            val action = EditItemFragmentDirections.toHomeFragment()
            NavHostFragment.findNavController(this).navigate(action)
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