package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class ordersStatusActivity : AppCompatActivity() {
    lateinit var sandwichLocalDB: sandwichLocalDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.orders_status_activity)
        sandwichLocalDB = sandwichApp.sandwichLocalDB
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