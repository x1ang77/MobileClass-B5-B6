package com.chiaching.projecttodolist

import android.app.Application
import com.chiaching.projecttodolist.repository.TaskRepository

class MyApplication: Application() {
    val taskRepo = TaskRepository.getInstance()
}