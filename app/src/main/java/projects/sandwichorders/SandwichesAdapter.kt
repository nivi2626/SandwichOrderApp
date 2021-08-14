package projects.sandwichorders

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class SandwichesAdapter: RecyclerView.Adapter<SandwichHolder>() {
    private lateinit var _sandwichesList: List<Sandwich>
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SandwichHolder {
        context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.item_sandwich, parent, false)
        return SandwichHolder(view)
    }

    override fun onBindViewHolder(holder: SandwichHolder, position: Int) {
        // set UI
        val sandwich = _sandwichesList[position]
        holder.name.text = sandwich.name
        holder.ingredients.text = parseIngredients(sandwich)
        holder.status.text = sandwich.status
        holder.editButton.isEnabled = (sandwich.status == SandwichApp.WAITING_STATUS)

        holder.editButton.alpha = if (sandwich.status == SandwichApp.WAITING_STATUS) { 1f } else { .4f }

        // edit listener
        holder.editButton.setOnClickListener{
            val intentForEdit = Intent(context, EditActivity::class.java)
            intentForEdit.putExtra("id", sandwich.id)
            context.startActivity(intentForEdit)
        }

        // fireStore listener
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(SandwichApp.COLLECTION).document(sandwich.id).addSnapshotListener {
            documentSnapshot, e ->
            if (documentSnapshot != null) {
                val sandwichInFireStore = documentSnapshot.toObject(Sandwich::class.java)
                if (sandwichInFireStore != null && sandwichInFireStore.status == SandwichApp.PROGRESS_STATUS) {
                    sandwich.status = SandwichApp.PROGRESS_STATUS
                    holder.editButton.isEnabled = false
                    holder.editButton.alpha = .5f

                }
                else if (sandwichInFireStore != null && sandwichInFireStore.status == SandwichApp.READY_STATUS) {
                    sandwich.status = SandwichApp.READY_STATUS
                }
                holder.status.text = sandwich.status
            }
        }

    }

    override fun getItemCount(): Int {
        return _sandwichesList.size
    }

    fun setItems(orderedSandwiches: List<Sandwich> ){
        _sandwichesList = orderedSandwiches
        notifyDataSetChanged()
    }

    private fun parseIngredients(sandwich: Sandwich): String{
        val spreadsNames = listOf("tahini", "cream cheese", "tapenade", "pesto", "hummus", "guacamole")
        val vegetablesNames = listOf("avocado", "tomatoes", "cucumbers", "lettuce", "pickles", "onion")
        var spreads = ""
        var veggis = ""
        for (i in 0..5){
            if (sandwich.spreads[i]) {
                spreads +=
                        if (spreads == ""){ spreadsNames[i] }
                        else { ", " + spreadsNames[i] }
            }
            if (sandwich.vegetables[i]) {
                veggis +=
                        if (veggis == "") { vegetablesNames[i] }
                        else { ", " + vegetablesNames[i] }
            }
        }
        return spreads + "\n" + veggis
    }

}