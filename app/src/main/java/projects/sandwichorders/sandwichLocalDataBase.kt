package projects.sandwichorders

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import java.util.*


class sandwichLocalDataBase internal constructor(context: Context) {
    // local phone DB:
    private var sp: SharedPreferences = context.getSharedPreferences("sandwiches_DB", Context.MODE_PRIVATE)
    var gson = Gson()
    // class DB:
    var orderedSandwiches: MutableList<sandwich> = initialize()

    private companion object {
        var instance: sandwichLocalDataBase? = null
    }

    private fun initialize(): MutableList<sandwich> {
        val keys: Set<String> = sp.all.keys
        val orderedSandwiches = mutableListOf<sandwich>()
        for (key in keys) {
            val sandwichAsString = sp.getString(key, null)
            if (sandwichAsString != null) {
                orderedSandwiches.add(string2sandwich(sandwichAsString))
            }
        }
        return orderedSandwiches
    }

    private fun string2sandwich(str: String): sandwich {
        return gson.fromJson(str, sandwich::class.java)
    }

    private fun sandwich2string(sand: sandwich?): String {
        return gson.toJson(sand)
    }

    fun addNewSandwich(new: sandwich) {
        // local
        orderedSandwiches.add(new)

        // update DB
        val editor = sp.edit()
        editor.putString(new.id, sandwich2string(new))
        editor.apply()
    }

    fun deleteSandwich(sandwich2delete: sandwich) {
        // update local list
        orderedSandwiches.remove(sandwich2delete)

        // update SP
        val editor = sp.edit()
        editor.remove(sandwich2string(sandwich2delete)).apply()
    }


    fun clear() {
        // clear local list
        orderedSandwiches.clear()

        // clear sp
        val editor = sp.edit()
        editor.clear().apply()
    }

    fun getSandwich(id: String?):sandwich? {
        orderedSandwiches.forEach{
            if (it.id == id){
                return it
            }
        }
        return null
    }

}
