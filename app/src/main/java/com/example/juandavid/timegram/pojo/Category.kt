package com.example.juandavid.timegram.pojo

enum class Category (val id:Int, val text:String){
    PERSONAL(0, "PERSONAL"),
    FAMILY(1, "FAMILY"),
    WORK(2, "WORK"),
    WORK2(3, "WORK2"),
    FRIENDS(4, "FRIENDS"),
    MEDICAL(5, "MEDICAL"),
    DENTIST(6, "DENTIST");

    companion object {
        fun fromId(findValue: Int): Category = Category.values().first { it.id == findValue }
        fun fromString(findValue: String): Category = Category.values().first { it.text == findValue }

    }
}