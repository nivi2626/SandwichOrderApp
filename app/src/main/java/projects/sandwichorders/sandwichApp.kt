package projects.sandwichorders
import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp


class sandwichApp:Application(){
    companion object {
        lateinit var sandwichLocalDB: sandwichLocalDataBase
        const val collection = "sandwiches"
        const val READY_STATUS = "ready"
        const val PROGRESS_STATUS = "in-progress"
        const val WAITING_STATUS = "waiting"
        const val COLLECTION = "sandwiches"
    }
    val Context.myApp: sandwichApp get() = applicationContext as sandwichApp

    @Override
    override fun onCreate() {
        super.onCreate()
        sandwichLocalDB = sandwichLocalDataBase(this)
        FirebaseApp.initializeApp(this)
    }



}