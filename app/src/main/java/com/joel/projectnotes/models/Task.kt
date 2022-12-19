package com.joel.projectnotes.models

data class Task(
    val id: Long? = null,
    val title: String,
    val description: String,
    val color: String
)