package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


class OpenActivity : AppCompatActivity() {
    lateinit var sandwichLocalDB: SandwichLocalDataBase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.open_activity)
        sandwichLocalDB = SandwichApp.sandwichLocalDB

        // check if there are active orders
        // no order -  go to OrderActivity
        if (sandwichLocalDB.orderedSandwiches.isEmpty()) {
            val intentNext = Intent(this, OrderActivity::class.java)
            startActivity(intentNext)
        }
        // orders -  go to OrdersStatusActivity
        else {
            val intentNext = Intent(this, OrdersStatusActivity::class.java)
            startActivity(intentNext)
        }
    }
}
