package projects.sandwichorders

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class SandwichesAdapter(): RecyclerView.Adapter<SandwichHolder>() {
    private lateinit var _sandwichesList: MutableList<Sandwich>
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SandwichHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_sandwich, parent, false)
        return SandwichHolder(view)
    }

    override fun onBindViewHolder(holder: SandwichHolder, position: Int) {
        val sandwich = _sandwichesList[position]
        holder.name.text = sandwich.name
        val spreadsText = "all spreads"
        holder.spreads.text = spreadsText
        val veggiesText = "all vegetables"
        holder.vegetables.text = veggiesText

        holder.editButton.setOnClickListener{
            val intentForEdit = Intent(context, EditActivity::class.java)
            intentForEdit.putExtra("id", sandwich.id)
            context.startActivity(intentForEdit)
        }
    }

    override fun getItemCount(): Int {
        return _sandwichesList.size
    }

    fun setItems(orderedSandwiches: MutableList<Sandwich> ){
        _sandwichesList = orderedSandwiches
        notifyDataSetChanged()
    }

}