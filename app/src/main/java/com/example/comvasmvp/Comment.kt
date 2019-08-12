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
    var progress: Long = 0
    var time: Float = 0.0f
    var tag = String()
}