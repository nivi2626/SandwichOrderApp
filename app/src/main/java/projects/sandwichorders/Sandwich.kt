package projects.sandwichorders
import java.io.Serializable

class Sandwich(val id:String = "", val name:String = "",
               var hummus:Boolean?= false, var tahini:Boolean?= false,
               var comments:String?="", var status:String=""): Serializable
{

}


