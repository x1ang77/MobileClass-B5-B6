package com.joel.projectnotes.repository

import com.joel.projectnotes.models.Task

class TaskRepository {
    private var counter = 0L
    private val tasksMap: MutableMap<Long, Task> = mutableMapOf()

    fun getTasks(): List<Task> {
        return tasksMap.values.toList()
    }

    fun addTask(task: Task): Task? {
        tasksMap[++counter] = task.copy(id = counter)
        return tasksMap[counter]
    }

    fun getTaskById(id: Long): Task? {
        return tasksMap[id]
    }

    fun updateTask(id: Long, task: Task): Task? {
        tasksMap[id] = task
        return tasksMap[id]
    }

    fun deleteTask(id: Long) {
        tasksMap.remove(id)
    }

    companion object {
        private var taskRepository: TaskRepository? = null

        fun getInstance(): TaskRepository {
            if (taskRepository == null) {
                taskRepository = TaskRepository()
            }
            return taskRepository!!
        }
    }
}