package space.beka.newcodialgram.models

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

class MyPosts {
    var desc :String=""
    var likes:Int = 0

    var date = ""
    var userName:String =""
    var isImage = true
    var fileLocation:String = ""

    constructor(
        desc: String,
        likes: Int,
        userName: String,
        isImage: Boolean,
        fileLocation: String,
        @SuppressLint("SimpleDateFormat")
        date: String =SimpleDateFormat("dd.MM.yyyy HH:mm").format(Date())
    ) {
        this.desc = desc
        this.likes = likes
        this.date = date
        this.userName = userName
        this.isImage = isImage
        this.fileLocation = fileLocation
    }

    constructor()

    override fun toString(): String {
        return "MyPosts(desc='$desc', likes=$likes, date='$date', userName='$userName', isImage=$isImage, fileLocation='$fileLocation')"
    }

}