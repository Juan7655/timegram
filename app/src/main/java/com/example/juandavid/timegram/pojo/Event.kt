package com.example.juandavid.timegram.pojo


import android.arch.persistence.room.Entity
import android.arch.persistence.room.Ignore
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "EVENTS")
data class Event constructor(var date:String,
                             var objective: String,
                             var description:String,
                             var category:String,
                             var realtime:String?){
    @PrimaryKey(autoGenerate = true)
    var id:Int=0

    @Ignore
    var cat:Category=Category.fromString(category)
}