package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class EditActivity : AppCompatActivity() {
//    private val collection = "sandwiches"
    lateinit var sandwichLocalDB:SandwichLocalDataBase
    lateinit var sandwichOrder:Sandwich

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        sandwichLocalDB = SandwichApp.sandwichLocalDB

        val intent = getIntent()
        val orderID = intent.getStringExtra("id")
        val recivedSand = sandwichLocalDB.getSandwich(orderID)
        if (recivedSand != null) {
            sandwichOrder = recivedSand
        }
        // find all views:
        // general
        val nameEdit = this.findViewById<EditText>(R.id.nameEdit)
        val commentsEdit = this.findViewById<EditText>(R.id.commentsEdit)
        val deleteButton = this.findViewById<Button>(R.id.deleteButton)
        val saveButton = this.findViewById<Button>(R.id.saveButton)
        // spreads:
        val tahiniCheckBox = this.findViewById<CheckBox>(R.id.tahini)
        val tapenadeCheckBox = this.findViewById<CheckBox>(R.id.tapenade)
        val hummusCheckBox = this.findViewById<CheckBox>(R.id.hummus)
        val cheeseCheckBox = this.findViewById<CheckBox>(R.id.cream_cheese)
        val pestoCheckBox = this.findViewById<CheckBox>(R.id.pesto)
        val guacamoleCheckBox = this.findViewById<CheckBox>(R.id.guacamole)

        // set initial UI:
        nameEdit.setText(sandwichOrder.name)
        hummusCheckBox!!.isChecked = sandwichOrder.hummus!!
        tahiniCheckBox!!.isChecked = sandwichOrder.tahini!!
        commentsEdit.setText(sandwichOrder.comments.toString())

        // set listener
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(SandwichApp.COLLECTION).document(sandwichOrder.id).addSnapshotListener {
                documentSnapshot, firebaseFirestoreException ->
            val sandwichInFireStore = documentSnapshot?.toObject(Sandwich::class.java)
            if (sandwichInFireStore!= null && sandwichInFireStore.status == SandwichApp.PROGRESS_STATUS) {
                 // todo - change status
            }
            if (sandwichInFireStore!= null && sandwichInFireStore.status == SandwichApp.READY_STATUS) {
                // todo - change status
            }
        }


        // saveButton listener
        saveButton.setOnClickListener(View.OnClickListener {
            // create new sandwich
            val tahini:Boolean = tahiniCheckBox.isChecked
            val hummus:Boolean = hummusCheckBox.isChecked
            val comments:String = commentsEdit.text.toString()
            val name:String = nameEdit.text.toString()
            val newSandwich = Sandwich(sandwichOrder.id, name, hummus, tahini, comments, SandwichApp.WAITING_STATUS)
            // save to local DB
            sandwichLocalDB.addNewSandwich(newSandwich)
            // update to firebase
            updateFireStore(sandwichOrder, newSandwich)
            sandwichOrder = newSandwich
        })

        // deleteButton listener
        deleteButton.setOnClickListener {
            deleteFromFireStore(sandwichOrder)
            sandwichLocalDB.deleteSandwich(sandwichOrder)

            // start orders screen
            val nextIntent = Intent(this, OrdersStatusActivity::class.java)
            startActivity(nextIntent) }
    }



    private fun deleteFromFireStore(sandwichToDelete: Sandwich) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(SandwichApp.COLLECTION).document(sandwichToDelete.id).delete()
            .addOnSuccessListener {
                Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
            }
    }

    private fun updateFireStore(oldSandwich: Sandwich, newSandwich: Sandwich) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(SandwichApp.COLLECTION).document(oldSandwich.id).delete()
            .addOnSuccessListener {
                fireStore.collection(SandwichApp.COLLECTION).add(newSandwich).addOnSuccessListener {
                    Toast.makeText(this, "Item updated", Toast.LENGTH_SHORT).show()
                }
            }
    }

    override fun onBackPressed() {
        // do nothing
    }


}