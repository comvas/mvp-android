package com.example.comvasmvp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.ListView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_comment_edit.*
import kotlinx.android.synthetic.main.activity_project_edit.*
import kotlinx.android.synthetic.main.activity_ticket_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class CommentEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comment_edit)

        realm = Realm.getDefaultInstance()

        val projectId = intent?.getLongExtra("project_id", -1L)
        val boardId = intent?.getLongExtra("board_id", -1L)
        val ticketId = intent?.getLongExtra("ticket_id", -1L)
        val commentId = intent?.getLongExtra("comment_id", -1L)
        if (commentId != -1L) {
            val comment = realm.where<Comment>().equalTo("commentId", commentId).findFirst()
            commentTitleEdit.setText(comment?.title)
            commentDateEdit.setText(DateFormat.format("yyyy/MM/dd", comment?.date))
            commentProgressEdit.setText(comment?.progress?.toString())
            commentTimeEdit.setText(comment?.time?.toString())
            commentTagEdit.setText(comment?.tag)
            commentDetailEdit.setText(comment?.detail)

            deleteComment.visibility = View.VISIBLE
        } else {
            deleteComment.visibility = View.INVISIBLE
        }

        saveComment.setOnClickListener {
            when (commentId) {
                -1L -> {
                    realm.executeTransaction {
                        // Create new data for comment
                        val commentMaxId = realm.where<Comment>().max("commentId")
                        val commentNextId = (commentMaxId?.toLong() ?: 0L) + 1
                        val comment = realm.createObject<Comment>(commentNextId)

                        comment.title = commentTitleEdit.text.toString()
                        commentDateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            comment.date = it
                        }
                        comment.progress = commentProgressEdit.text.toString().toLong()
                        comment.time = commentTimeEdit.text.toString().toFloat()
                        comment.tag = commentTagEdit.text.toString()
                        comment.detail = commentDetailEdit.text.toString()

                        // Set relationship between board and comment
                        val ticket = realm.where<Ticket>().equalTo("ticketId", ticketId).findFirst()
                        ticket?.commentList?.add(comment)
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val comment = realm.where<Comment>().equalTo("commentId", commentId).findFirst()

                        comment?.title = commentTitleEdit.text.toString()
                        commentDateEdit.text.toString().toDate("yyyy.MM.dd")?.let {
                            comment?.date = it
                        }
                        comment?.progress = commentProgressEdit.text.toString().toLong()
                        comment?.time = commentTimeEdit.text.toString().toFloat()
                        comment?.tag = commentTagEdit.text.toString()
                        comment?.detail = commentDetailEdit.text.toString()
                    }
                    alert("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        deleteComment.setOnClickListener {
            realm.executeTransaction {
                realm.where<Comment>().equalTo("commentId", commentId)?.findFirst()?.deleteFromRealm()
            }
            alert("削除しました") {
                yesButton { finish() }
            }.show()
        }
    }

    fun String.toDate(pattern: String = "yyyy/MM/dd HH:mm"): Date? {
        val sdFormat = try {
            SimpleDateFormat(pattern)
        } catch (e: IllegalArgumentException) {
            null
        }
        val date = sdFormat?.let {
            try {
                it.parse(this)
            } catch (e: ParseException) {
                null
            }
        }
        return date
    }
}