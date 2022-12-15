package com.chiaching.projecttodolist.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import cn.pedant.SweetAlert.SweetAlertDialog
import com.chiaching.projecttodolist.MyApplication
import com.chiaching.projecttodolist.databinding.FragmentEditDetailsBinding
import com.chiaching.projecttodolist.model.Task
import com.chiaching.projecttodolist.viewModels.DetailsViewModel
import com.chiaching.projecttodolist.viewModels.EditDetailsViewModel

class EditDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEditDetailsBinding

    val viewModel: EditDetailsViewModel by viewModels{
        EditDetailsViewModel.Provider(
            (requireContext().applicationContext as MyApplication).taskRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEditDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navArgs: EditDetailsFragmentArgs by navArgs()

        viewModel.getTaskById(navArgs.id)

        viewModel.task.observe(viewLifecycleOwner){
            binding.run {
                etTitle.setText(it.title)
                etDetails.setText(it.details)
                if(it.color.toString() == "#96DC02"){
                    green.isChecked = true
                }else if(it.color.toString() == "#04FFF0"){
                    blue.isChecked = true
                }else if(it.color.toString() == "#E32525"){
                    red.isChecked = true
                }else if(it.color.toString() == "#AB00BA"){
                    purple.isChecked = true
                }else if(it.color.toString() == "#FFC90A") {
                    yellow.isChecked = true
                }
            }
        }

        binding.btnEdit.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val details = binding.etDetails.text.toString()
            val selectedId: Int = binding.radioGroup.checkedRadioButtonId
            val radioName = resources.getResourceEntryName(selectedId)
            val color = when (radioName.toString()){
                "red" -> "#E32525"
                "yellow" -> "#FFC90A"
                "blue" -> "#04FFF0"
                "green" -> "#96DC02"
                "purple" -> "#AB00BA"
                else -> "#FFFFFF"
            }
            val task = Task(navArgs.id,title,details,color)
            viewModel.updateTask(navArgs.id, task)
            val bundle = Bundle()
            bundle.putBoolean("refresh",true)
            setFragmentResult("from_details",bundle)
            NavHostFragment.findNavController(this).popBackStack()
        }


        binding.btnDelete.setOnClickListener{
            SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("Are you sure?")
                .setContentText("Won't be able to recover this file!")
                .setConfirmText("Yes,delete it!")
                .setConfirmClickListener { sDialog ->
                    sDialog
                        .setTitleText("Deleted!")
                        .setContentText("Note has been deleted!")
                        .setConfirmText("OK")
                        .setConfirmClickListener(null)
                        .changeAlertType(SweetAlertDialog.SUCCESS_TYPE)
                    viewModel.deleteTask(navArgs.id)
                    val bundle = Bundle()
                    bundle.putBoolean("refresh",true)
                    setFragmentResult("from_details",bundle)
                    NavHostFragment.findNavController(this).popBackStack()
                }
                .show()
        }

    }

}