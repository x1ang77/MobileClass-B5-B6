package com.chiaching.projecttodolist.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chiaching.projecttodolist.model.Task
import com.chiaching.projecttodolist.repository.TaskRepository

class AddTaskViewModel(private val repo: TaskRepository): ViewModel() {
    fun addTask(task: Task){
        repo.addTask(task)
    }

    class Provider(val repo: TaskRepository):ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return AddTaskViewModel(repo) as T
        }
    }
}