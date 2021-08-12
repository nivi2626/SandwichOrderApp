package projects.sandwichorders
import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp


class SandwichApp:Application(){
    companion object {
        lateinit var sandwichLocalDB: SandwichLocalDataBase
        const val collection = "sandwiches"
        const val READY_STATUS = "ready"
        const val PROGRESS_STATUS = "in-progress"
        const val WAITING_STATUS = "waiting"
        const val COLLECTION = "sandwiches"
    }
    val Context.myApp: SandwichApp get() = applicationContext as SandwichApp

    @Override
    override fun onCreate() {
        super.onCreate()
        sandwichLocalDB = SandwichLocalDataBase(this)
        FirebaseApp.initializeApp(this)
    }



}