package com.example.comvasmvp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*
import io.realm.RealmList



open class Project : RealmObject() {
    @PrimaryKey
    var projectId: Long = 0
    var date: Date = Date()
    var title: String = ""
    var detail: String = ""

    var list: RealmList<Board>? = null
}