package com.chiaching.projecttodolist.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.chiaching.projecttodolist.MyApplication
import com.chiaching.projecttodolist.adapters.TaskAdapter
import com.chiaching.projecttodolist.databinding.FragmentAddItemBinding
import com.chiaching.projecttodolist.databinding.FragmentNotesBinding
import com.chiaching.projecttodolist.model.Task
import com.chiaching.projecttodolist.viewModels.HomeViewModel


class NotesFragment : Fragment() {

    private lateinit var adapter: TaskAdapter
    private lateinit var binding: FragmentNotesBinding
    private val viewModel: HomeViewModel by viewModels {
        HomeViewModel.Provider((requireContext().applicationContext as MyApplication).taskRepo)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotesBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()


        viewModel.tasks.observe(viewLifecycleOwner){
            adapter.setTasks(it)
            if(adapter.itemCount == 0){
                binding.tvTips.text = "Nothing added yet"
            }
        }
        binding.eFabAddNewItem.setOnClickListener {
            val action = NotesFragmentDirections.actionHomeFragmentToAddItemFragment()
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
        val layoutManager = GridLayoutManager(requireContext(),2)
        adapter = TaskAdapter(emptyList()) {
            val action = NotesFragmentDirections.actionHomeFragmentToDetailsFragment(it.id!!)
            NavHostFragment.findNavController(this).navigate(action)
        }
        binding.rvTask.adapter = adapter
        binding.rvTask.layoutManager = layoutManager
    }
}