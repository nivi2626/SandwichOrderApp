package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class OrdersStatusActivity : AppCompatActivity() {
    lateinit var orderedSandwiches: List<Sandwich>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_status_activity)
        orderedSandwiches = SandwichApp.sandwichLocalDB.orderedSandwiches

        // find views
        val newOrderButton = this.findViewById<Button>(R.id.order)

        // recycleView
        val adapter = SandwichesAdapter()
        adapter.setItems(orderedSandwiches)
        val sandwichRecycleView = findViewById<RecyclerView>(R.id.sandwich_recycler)
        sandwichRecycleView.adapter = adapter
        sandwichRecycleView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)

        // newOrderButton listener
        newOrderButton.setOnClickListener(View.OnClickListener {
            // start edit screen
            val intentForOrder = Intent(this, OrderActivity::class.java)
            startActivity(intentForOrder);
        })
    }
}

//val orderID = sandwichLocalDB.orderedSandwich!!.id
//val fireStore = FirebaseFirestore.getInstance()
//fireStore.collection(sandwichApp.collection).document(orderID).get()
//.addOnSuccessListener {
//    val downloadSandwich = it.toObject(sandwich::class.java)
//    if (downloadSandwich != null) {
//        if (downloadSandwich.status == "waiting") {
//            intentNext = Intent(this, EditActivity::class.java)
//        }
//        if (downloadSandwich.status == "in-progress") {
//            intentNext = Intent(this, EditActivity::class.java)
//        }
//
//        intentNext.putExtra("id", downloadSandwich.id)
//        startActivity(intentNext)
//    }
//}