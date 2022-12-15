package com.chiaching.projecttodolist.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import cn.pedant.SweetAlert.SweetAlertDialog
import com.chiaching.projecttodolist.MyApplication
import com.chiaching.projecttodolist.databinding.FragmentAddItemBinding
import com.chiaching.projecttodolist.model.Task
import com.chiaching.projecttodolist.viewModels.AddTaskViewModel

class AddItemFragment : Fragment() {

    private lateinit var binding : FragmentAddItemBinding
    private val viewModel: AddTaskViewModel by viewModels {
        AddTaskViewModel.Provider(
            (requireContext().applicationContext as MyApplication).taskRepo
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddItemBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnAdd.setOnClickListener {
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

            if(title.trim() == ""){
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Oops...").setContentText("Title is missing").show()
            }else if(details.trim() == ""){
                SweetAlertDialog(requireContext(), SweetAlertDialog.WARNING_TYPE).setTitleText("Oops...").setContentText("Details is missing").show()
            }else{
                Log.d("TestDebug", color)
                viewModel.addTask(Task(null,title,details,color))
                val bundle = Bundle()
                bundle.putBoolean("refresh",true)
                setFragmentResult("from_add_item",bundle)
                NavHostFragment.findNavController(this).popBackStack()
            }
        }
    }
}