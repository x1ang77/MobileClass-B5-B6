package com.chiaching.projecttodolist.ui

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import com.chiaching.projecttodolist.MyApplication
import com.chiaching.projecttodolist.adapters.TaskAdapter
import com.chiaching.projecttodolist.databinding.FragmentDetailsBinding
import com.chiaching.projecttodolist.model.Task
import com.chiaching.projecttodolist.viewModels.DetailsViewModel


class DetailsFragment : Fragment() {
    private lateinit var binding:FragmentDetailsBinding

    val viewModel: DetailsViewModel by viewModels{
        DetailsViewModel.Provider(
            (requireContext().applicationContext as MyApplication).taskRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navArgs: DetailsFragmentArgs by navArgs()

        viewModel.getTaskById(navArgs.id)
        viewModel.task.observe(viewLifecycleOwner){
            binding.run{
                Log.d("findId2", it.id.toString())
                tvTitle.text = it.title
                tvDetails.text = it.details
                viewBG.setBackgroundColor(Color.parseColor(it.color.toString()))
            }
            binding.btnEdit.setOnClickListener {
                val action = DetailsFragmentDirections.actionDetailsFragmentToEditDetailsFragment(navArgs.id)
                NavHostFragment.findNavController(this).navigate(action)
            }

        }


    }
}