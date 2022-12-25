package space.beka.newcodialgram.models

class User {
     var uid:String =""
     var userName:String = ""

     constructor(uid: String, userName: String) {
          this.uid = uid
          this.userName = userName
     }

     constructor()

     override fun toString(): String {
          return "User(uid='$uid', userName='$userName')"
     }

}