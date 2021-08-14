package projects.sandwichorders
import java.io.Serializable

//class Sandwich(val id:String = "", val name:String = "",
//               var tahini:Boolean?= false, var cream_cheese:Boolean?= false,
//               var tapenade:Boolean?= false, var pesto:Boolean?= false,
//               var hummus:Boolean?= false, var guacamole:Boolean?= false,
//               var avocado:Boolean?= false, var tomatoes:Boolean?= false,
//               var Cucumbers:Boolean?= false, var lettuce:Boolean?= false,
//               var pickles:Boolean?= false, var onion:Boolean?= false,
//               var comments:String?="", var status:String=""): Serializable
class Sandwich(val id:String = "", val name:String = "", var spreads:List<Boolean> = emptyList(),
               var vegetables :List<Boolean> = emptyList() , var comments:String?="",
               var status:String=""): Serializable
{
}


