package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import projects.sandwichorders.SandwichApp.Companion.sandwichLocalDB
import java.util.*

class OrderActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_activity)

        // general views:
        val backButton = this.findViewById<Button>(R.id.backButton)
        val saveButton = this.findViewById<Button>(R.id.saveButton)

        //sandwich views:
        val nameEdit = this.findViewById<EditText>(R.id.nameEdit)
        val commentsEdit = this.findViewById<EditText>(R.id.commentsEdit)
        // spreads views:
        val tahiniCheckBox = this.findViewById<CheckBox>(R.id.tahini)
        val tapenadeCheckBox = this.findViewById<CheckBox>(R.id.tapenade)
        val hummusCheckBox = this.findViewById<CheckBox>(R.id.hummus)
        val cheeseCheckBox = this.findViewById<CheckBox>(R.id.cream_cheese)
        val pestoCheckBox = this.findViewById<CheckBox>(R.id.pesto)
        val guacamoleCheckBox = this.findViewById<CheckBox>(R.id.guacamole)
        // vegetables views:
        val avocadoCheckBox = this.findViewById<CheckBox>(R.id.avocado)
        val cucumbersCheckBox = this.findViewById<CheckBox>(R.id.cucumbers)
        val picklesCheckBox = this.findViewById<CheckBox>(R.id.pickles)
        val tomatoesCheckBox = this.findViewById<CheckBox>(R.id.tomatoes)
        val lettuceCheckBox = this.findViewById<CheckBox>(R.id.lettuce)
        val onionCheckBox = this.findViewById<CheckBox>(R.id.onion)

        // save listener
        saveButton.setOnClickListener(View.OnClickListener {
            // create new sandwich
            val newSandwich = Sandwich(
                    id=UUID.randomUUID().toString(),
                    name = nameEdit.text.toString(),
                    spreads = listOf(tahiniCheckBox.isChecked, cheeseCheckBox.isChecked,
                            tapenadeCheckBox.isChecked, pestoCheckBox.isChecked,
                            hummusCheckBox.isChecked, guacamoleCheckBox.isChecked),
                    vegetables = listOf(avocadoCheckBox.isChecked, tomatoesCheckBox.isChecked,
                            cucumbersCheckBox.isChecked, lettuceCheckBox.isChecked,
                            picklesCheckBox.isChecked, onionCheckBox.isChecked),
                    comments= commentsEdit.text.toString(),
                    status = "waiting")

            // save to local DB
            sandwichLocalDB.addNewSandwich(newSandwich)
            // upload to firebase
            uploadToFireStore(newSandwich)

            // start orders status screen
            val nextIntent = Intent(this, OrdersStatusActivity::class.java)
            startActivity(nextIntent);
        })

        // back listener
        backButton.setOnClickListener(){
            onBackPressed()
        }
    }


    private fun uploadToFireStore(sandwichToUpload: Sandwich) {

        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(SandwichApp.COLLECTION).document(sandwichToUpload.id).set(sandwichToUpload)
            .addOnSuccessListener {
                Toast.makeText(this, "item saved", Toast.LENGTH_SHORT).show()
            }
            .addOnFailureListener {
                Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
            }

    }

    override fun onBackPressed() {
        // start orders status screen
        val nextIntent = Intent(this, OrdersStatusActivity::class.java)
        startActivity(nextIntent);
    }

}

