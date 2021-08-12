package projects.sandwichorders

import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SandwichHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val editButton: Button = itemView.findViewById(R.id.edit)
    val name: TextView = itemView.findViewById(R.id.name)
    val spreads: TextView= itemView.findViewById(R.id.spreads)
    val vegetables: TextView = itemView.findViewById(R.id.vegetables)
}