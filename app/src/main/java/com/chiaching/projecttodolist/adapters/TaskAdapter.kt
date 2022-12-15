package com.chiaching.projecttodolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.graphics.toColorInt
import androidx.recyclerview.widget.RecyclerView
import com.chiaching.projecttodolist.databinding.ItemLayoutTaskBinding

import com.chiaching.projecttodolist.model.Task

class TaskAdapter ( var items: List<Task>, val onClick:(item:Task) -> Unit ): RecyclerView.Adapter<TaskAdapter.ItemTaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTaskHolder {
        val binding = ItemLayoutTaskBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ItemTaskHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTaskHolder, position: Int) {
        val item = items[position]
        holder.binding.run{
            tvTitle.text = item.title
            tvDetails.text = item.details
            cvTaskItem.setCardBackgroundColor(item.color.toColorInt())
            cvTaskItem.setOnClickListener {
                onClick(item)
            }
        }

    }

    override fun getItemCount() = items.size

    fun setTasks(tasks: List<Task>){
        this.items = tasks
        notifyDataSetChanged()
    }
    class ItemTaskHolder(val binding: ItemLayoutTaskBinding): RecyclerView.ViewHolder(binding.root)

}