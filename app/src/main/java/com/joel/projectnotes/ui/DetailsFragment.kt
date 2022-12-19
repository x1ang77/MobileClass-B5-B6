package com.joel.projectnotes.ui

import android.graphics.Color
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
import com.joel.projectnotes.MyApplication
import com.joel.projectnotes.R
import com.joel.projectnotes.databinding.FragmentDetailsBinding
import com.joel.projectnotes.viewModels.DetailsViewModel

class DetailsFragment : Fragment() {
    private lateinit var binding: FragmentDetailsBinding
    private val viewModel: DetailsViewModel by viewModels {
        DetailsViewModel.Provider(
            (requireContext().applicationContext as MyApplication).taskRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navArgs: DetailsFragmentArgs by navArgs()

        viewModel.getTaskById(navArgs.id)

        viewModel.task.observe(viewLifecycleOwner) {
            binding.run {
                tvDetailsTitle.text = it.title
                tvDetailsDescription.text = it.description
                root.setBackgroundColor(Color.parseColor(it.color))
            }
        }

        binding.btnDetailsEdit.setOnClickListener {
            val action = DetailsFragmentDirections.actionDetailsToEditItem(navArgs.id)
            NavHostFragment.findNavController(this).navigate(action)
        }

        binding.btnDetailsBack.setOnClickListener {
            val bundle = Bundle()
            bundle.putBoolean("refresh", true)
            setFragmentResult("from_details", bundle)
            val action = DetailsFragmentDirections.actionDetailsToHome()
            NavHostFragment.findNavController(this).navigate(action)
        }
    }
}