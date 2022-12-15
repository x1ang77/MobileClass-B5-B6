package com.chiaching.projecttodolist.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.chiaching.projecttodolist.model.Task
import com.chiaching.projecttodolist.repository.TaskRepository

class HomeViewModel(val repo: TaskRepository): ViewModel() {
    val tasks: MutableLiveData<List<Task>> = MutableLiveData()

    init {
        getTasks()
    }

    fun getTasks(){
        val res = repo.getTasks()
        tasks.value = res
    }

    class Provider(val repo: TaskRepository) : ViewModelProvider.Factory{
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(repo) as T
        }
    }
}