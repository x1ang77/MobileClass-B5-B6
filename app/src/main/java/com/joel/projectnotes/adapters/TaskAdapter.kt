package com.joel.projectnotes.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.joel.projectnotes.databinding.ItemTaskLayoutBinding
import com.joel.projectnotes.models.Task

class TaskAdapter(private var items: List<Task>, val onClick: (item: Task) -> Unit) :
    RecyclerView.Adapter<TaskAdapter.ItemTaskHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemTaskHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTaskLayoutBinding.inflate(layoutInflater, parent, false)
        return ItemTaskHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemTaskHolder, position: Int) {
        val item = items[position]
        holder.binding.run {
            tvNoteTitle.text = item.title
            tvNoteDescription.text = item.description
            cvMyNotes.setCardBackgroundColor(Color.parseColor(item.color))
            cvMyNotes.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun getItemCount() = items.size

    fun setTasks(items: List<Task>) {
        this.items = items
        notifyDataSetChanged()
    }

    class ItemTaskHolder(val binding: ItemTaskLayoutBinding) : RecyclerView.ViewHolder(binding.root)
}