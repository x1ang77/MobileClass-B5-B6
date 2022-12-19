package com.joel.projectnotes

import android.app.Application
import com.joel.projectnotes.repository.TaskRepository

class MyApplication : Application() {
    val taskRepo = TaskRepository.getInstance()
}