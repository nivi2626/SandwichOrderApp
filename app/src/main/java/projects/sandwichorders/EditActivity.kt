package projects.sandwichorders

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class EditActivity : AppCompatActivity() {
    lateinit var sandwichLocalDB: SandwichLocalDataBase
    lateinit var sandwichOrder: Sandwich

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_activity)
        sandwichLocalDB = SandwichApp.sandwichLocalDB

        val intent = getIntent()
        val orderID = intent.getStringExtra("id")
        val receivedSandwich = sandwichLocalDB.getSandwich(orderID)
        if (receivedSandwich != null) {
            sandwichOrder = receivedSandwich
        }
        // general views:
        val deleteButton = this.findViewById<Button>(R.id.deleteButton)
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

        // set initial UI:
        nameEdit.setText(sandwichOrder.name)
        commentsEdit.setText(sandwichOrder.comments)
        // set spreads
        tahiniCheckBox!!.isChecked = sandwichOrder.spreads[0]
        cheeseCheckBox!!.isChecked = sandwichOrder.spreads[1]
        tapenadeCheckBox!!.isChecked = sandwichOrder.spreads[2]
        pestoCheckBox!!.isChecked = sandwichOrder.spreads[3]
        hummusCheckBox!!.isChecked = sandwichOrder.spreads[4]
        guacamoleCheckBox!!.isChecked = sandwichOrder.spreads[5]
        // set spreads vegetables
        avocadoCheckBox!!.isChecked = sandwichOrder.vegetables[0]
        tomatoesCheckBox!!.isChecked = sandwichOrder.vegetables[1]
        cucumbersCheckBox!!.isChecked = sandwichOrder.vegetables[2]
        lettuceCheckBox!!.isChecked = sandwichOrder.vegetables[3]
        picklesCheckBox!!.isChecked = sandwichOrder.vegetables[4]
        onionCheckBox!!.isChecked = sandwichOrder.vegetables[5]

        // set fireStore listener
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(SandwichApp.COLLECTION).document(sandwichOrder.id).addSnapshotListener {
            documentSnapshot, firebaseFirestoreException ->
            Log.w("********", firebaseFirestoreException)
            if (documentSnapshot != null) {
                val sandwichInFireStore = documentSnapshot.toObject(Sandwich::class.java)
                if (sandwichInFireStore != null && sandwichInFireStore.status != SandwichApp.WAITING_STATUS) {
                    Toast.makeText(this, "Your sandwich is in process", Toast.LENGTH_SHORT).show()
                    sandwichOrder.status = SandwichApp.PROGRESS_STATUS
                    val intentNext = Intent(this, OrdersStatusActivity::class.java)
                    startActivity(intentNext)
                }
            }
        }

        // saveButton listener
        saveButton.setOnClickListener(View.OnClickListener {
            // create new sandwich
            val newSandwich = Sandwich(
                    id = sandwichOrder.id,
                    name = nameEdit.text.toString(),
                    spreads = listOf(tahiniCheckBox.isChecked, cheeseCheckBox.isChecked,
                            tapenadeCheckBox.isChecked, pestoCheckBox.isChecked,
                            hummusCheckBox.isChecked, guacamoleCheckBox.isChecked),
                    vegetables = listOf(avocadoCheckBox.isChecked, tomatoesCheckBox.isChecked,
                            cucumbersCheckBox.isChecked, lettuceCheckBox.isChecked,
                            picklesCheckBox.isChecked, onionCheckBox.isChecked),
                    comments = commentsEdit.text.toString(),
                    status = "waiting")
            // save to local DB
            sandwichLocalDB.updateSandwich(sandwichOrder, newSandwich)
            // update to fireStore
            updateFireStore(sandwichOrder, newSandwich)
            sandwichOrder = newSandwich
            // start orders screen
            goToOrders()
        })

        // deleteButton listener
        deleteButton.setOnClickListener {
            sandwichLocalDB.deleteSandwich(sandwichOrder)
            deleteFromFireStore(sandwichOrder)
            // start orders screen
            goToOrders()
        }
    }

    private fun deleteFromFireStore(sandwichToDelete: Sandwich) {
        val fireStore = FirebaseFirestore.getInstance()
        fireStore.collection(SandwichApp.COLLECTION).document(sandwichToDelete.id).delete()
                .addOnSuccessListener {
                    Toast.makeText(this, "Item deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(this, "ERROR", Toast.LENGTH_SHORT).show()
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

    fun goToOrders() {
        val nextIntent = Intent(this, OrdersStatusActivity::class.java)
        startActivity(nextIntent)
    }

}