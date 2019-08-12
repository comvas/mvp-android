package com.example.comvasmvp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import io.realm.RealmList

open class Ticket : RealmObject() {
    @PrimaryKey
    var ticketId: Long = 0
    var date: Date = Date()
    var title: String = ""
    var detail: String = ""
    var progress: Long = 0
    var priority: Long = 0
    var deadline: Date = Date()
    var point: Long = 0
    var tag: String = ""

    var commetList: RealmList<Comment>? = null
}