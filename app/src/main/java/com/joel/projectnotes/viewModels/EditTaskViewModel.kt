package com.joel.projectnotes.viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.joel.projectnotes.models.Task
import com.joel.projectnotes.repository.TaskRepository

class EditTaskViewModel(private val repo: TaskRepository) : ViewModel() {
    val task: MutableLiveData<Task> = MutableLiveData()

    fun getTaskById(id: Long) {
        val res = repo.getTaskById(id)
        res?.let {
            task.value = it
        }
    }

    fun deleteTask(id: Long) {
        repo.deleteTask(id)
    }

    fun updateTask(id:Long, task: Task) {
        repo.updateTask(id, task)
    }

    class Provider(private val repo: TaskRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EditTaskViewModel(repo) as T
        }
    }
}