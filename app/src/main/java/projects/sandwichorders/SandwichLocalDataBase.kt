package projects.sandwichorders

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson


class SandwichLocalDataBase internal constructor(context: Context) {
    // local phone DB:
    private var sp: SharedPreferences = context.getSharedPreferences("sandwiches_DB", Context.MODE_PRIVATE)
    var gson = Gson()
    // class DB:
    var orderedSandwiches: MutableList<Sandwich> = initialize()

    private companion object {
        var instance: SandwichLocalDataBase? = null
    }

    private fun initialize(): MutableList<Sandwich> {
        val keys: Set<String> = sp.all.keys
        val orderedSandwiches = mutableListOf<Sandwich>()
        for (key in keys) {
            val sandwichAsString = sp.getString(key, null)
            if (sandwichAsString != null) {
                orderedSandwiches.add(string2sandwich(sandwichAsString))
            }
        }
        return orderedSandwiches
    }

    private fun string2sandwich(str: String): Sandwich {
        return gson.fromJson(str, Sandwich::class.java)
    }

    private fun sandwich2string(sand: Sandwich?): String {
        return gson.toJson(sand)
    }

    fun addNewSandwich(new: Sandwich) {
        // local
        orderedSandwiches.add(new)

        // update DB
        val editor = sp.edit()
        editor.putString(new.id, sandwich2string(new))
        editor.apply()
    }

    fun deleteSandwich(sandwich2delete: Sandwich) {
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

    fun getSandwich(id: String?):Sandwich? {
        orderedSandwiches.forEach{
            if (it.id == id){
                return it
            }
        }
        return null
    }

}
