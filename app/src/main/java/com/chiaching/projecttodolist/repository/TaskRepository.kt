package com.chiaching.projecttodolist.repository

import com.chiaching.projecttodolist.model.Task

class TaskRepository {
    var counter = 0L
    val taskMap: MutableMap<Long, Task> = mutableMapOf()

    fun getTasks(): List<Task> {
        return taskMap.values.toList()
    }

    fun addTask(task: Task): Task?{
        taskMap[++counter] = task.copy(id = counter)
        return taskMap[counter]
    }

    fun getTaskById(id: Long) :Task?{
        return taskMap[id]
    }

    fun updateTask(id:Long, task:Task): Task?{
        taskMap[id] = task
        return taskMap[id]
    }

    fun deleteTask(id:Long){
        taskMap.remove(id)
    }

    companion object{
        private var taskRepository: TaskRepository? = null

        fun getInstance(): TaskRepository{
            if(taskRepository == null){
                taskRepository = TaskRepository()
            }
            return taskRepository!!
        }
    }
}