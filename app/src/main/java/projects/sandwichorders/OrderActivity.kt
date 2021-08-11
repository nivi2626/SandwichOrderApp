package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import projects.sandwichorders.sandwichApp.Companion.sandwichLocalDB
import java.util.*
import kotlin.collections.ArrayList

class OrderActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.order_activity)

        // general views:
        val nameEdit = this.findViewById<EditText>(R.id.nameEdit)
        val commentsEdit = this.findViewById<EditText>(R.id.commentsEdit)
        val deleteButton = this.findViewById<Button>(R.id.deleteButton)
        val saveButton = this.findViewById<Button>(R.id.saveButton)
        // spreads views:
        val tahiniCheckBox = this.findViewById<CheckBox>(R.id.tahini)
        val tapenadeCheckBox = this.findViewById<CheckBox>(R.id.tapenade)
        val hummusCheckBox = this.findViewById<CheckBox>(R.id.hummus)
        val cheeseCheckBox = this.findViewById<CheckBox>(R.id.cream_cheese)
        val pestoCheckBox = this.findViewById<CheckBox>(R.id.pesto)
        val guacamoleCheckBox = this.findViewById<CheckBox>(R.id.guacamole)
        // vegetables views:
        ///
        //
        //

        // save listener
        saveButton.setOnClickListener(View.OnClickListener {
            // create new sandwich
            val id :String = UUID.randomUUID().toString()
            val name :String = nameEdit.text.toString()
            val tahini:Boolean = tahiniCheckBox.isChecked
            val hummus:Boolean = hummusCheckBox.isChecked
            val comments:String = commentsEdit.text.toString()
            val newSandwich = sandwich(id, name, hummus, tahini, comments, "waiting")
            // save to local DB
            sandwichLocalDB.addNewSandwich(newSandwich)
            // upload to firebase
            uploadToFireStore(newSandwich)

            // start edit screen
            val intentForEdit = Intent(this, EditActivity::class.java)
            intentForEdit.putExtra("id", id)
            startActivity(intentForEdit);
        })

    }

    private fun uploadToFireStore(sandwichToUpload: sandwich) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(sandwichApp.collection).document(sandwichToUpload.id).set(sandwichToUpload)
            .addOnSuccessListener {
                Toast.makeText(this, "item saved", Toast.LENGTH_SHORT).show()
            }
    }

    private fun downloadFromFireStore(id: String) {
        val newSandwich = null
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(sandwichApp.collection).document(id).get()
            .addOnSuccessListener {
                val newSandwich = it.toObject(sandwich::class.java)
                return@addOnSuccessListener
            }.addOnFailureListener {
                return@addOnFailureListener
            }
    }

    override fun onBackPressed() {
        // do nothing
    }
}

