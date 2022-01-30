package by.home.manager.ui.data.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class GenericRecyclerViewAdapter<T : Any>(
    @LayoutRes private val layout: Int,
    private val bindingInterface: BindingInterface<T>
) : RecyclerView.Adapter<GenericRecyclerViewAdapter.TaskViewHolder>() {

    var itemList = listOf<T>()
        set(values) {
            field = values
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TaskViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.bind(itemList[position], bindingInterface)
    }

    override fun getItemCount(): Int = itemList.size

    class TaskViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        fun <T : Any> bind(item: T, bindingInterface: BindingInterface<T>) {
            bindingInterface.bind(item, view)
        }
    }
}

interface BindingInterface<T> {
    fun bind(item: T, view: View)
}
