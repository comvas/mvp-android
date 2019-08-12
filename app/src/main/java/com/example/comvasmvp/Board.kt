package com.example.comvasmvp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import io.realm.RealmList

open class Board : RealmObject() {
    @PrimaryKey
    var boardId: Long = 0
    var date: Date = Date()
    var title: String = ""
    var priority: Long = 0

    var ticketList: RealmList<Ticket>? = null
}