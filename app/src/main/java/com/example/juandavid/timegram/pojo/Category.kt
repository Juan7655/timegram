package com.example.juandavid.timegram.pojo

enum class Category (val id:Int, val text:String){
    PERSONAL(0, "Personal"),
    FAMILY(1, "Family"),
    WORK(2, "Work"),
    WORK2(3, "Work2"),
    FRIENDS(4, "Friends"),
    HEALTH(5, "Health"),
    DENTIST(6, "Dentist");

    companion object {
        fun fromId(findValue: Int): Category = Category.values().first { it.id == findValue }
        fun fromString(findValue: String): Category = Category.values().first { it.text == findValue }

    }
}