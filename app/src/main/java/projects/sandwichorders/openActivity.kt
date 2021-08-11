package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class openActivity : AppCompatActivity() {
    lateinit var sandwichLocalDB: sandwichLocalDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.open_activity)
        sandwichLocalDB = sandwichApp.sandwichLocalDB
//        sandwichLocalDB.clear() // debugging

        // check if there are active orders
        // no order -  go to OrderActivity
        if (sandwichLocalDB.orderedSandwiches.isEmpty()) {
            val intentNext = Intent(this, OrderActivity::class.java)
            startActivity(intentNext)
        }
        // no order -  go to OrderActivity
        else {
            val intentNext = Intent(this, ordersStatusActivity::class.java)
            startActivity(intentNext)
        }
    }
}
