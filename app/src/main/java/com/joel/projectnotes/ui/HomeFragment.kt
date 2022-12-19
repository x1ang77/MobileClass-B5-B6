package com.joel.projectnotes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.joel.projectnotes.MyApplication
import com.joel.projectnotes.R
import com.joel.projectnotes.adapters.TaskAdapter
import com.joel.projectnotes.databinding.FragmentHomeBinding
import com.joel.projectnotes.viewModels.HomeViewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeViewModel by viewModels() {
        HomeViewModel.Provider((requireContext().applicationContext as MyApplication).taskRepo)
    }
    private lateinit var adapter: TaskAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: DetailsFragmentArgs by navArgs()

        setupAdapter()

        viewModel.tasks.observe(viewLifecycleOwner) {
            adapter.setTasks(it)
        }

        binding.fabAddNewItem.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeToAddItem()
            NavHostFragment.findNavController(this).navigate(action)
        }

        setFragmentResultListener("from_details") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.getTasks()
            }
        }

        setFragmentResultListener("from_add_item") { _, result ->
            val refresh = result.getBoolean("refresh")
            if (refresh) {
                viewModel.getTasks()
            }
        }
    }

    fun setupAdapter() {
        val layoutManager = GridLayoutManager(requireContext(), 2)
        adapter = TaskAdapter(emptyList()) {
            val action = HomeFragmentDirections.actionHomeToDetails(it.id!!)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.rvMyNotes.adapter = adapter
        binding.rvMyNotes.layoutManager = layoutManager
    }
}