package com.example.comvasmvp

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import java.util.*

open class Comment : RealmObject() {
    @PrimaryKey
    var commentId: Long = 0
    var date: Date = Date()
    var title: String = ""
    var detail: String = ""
}