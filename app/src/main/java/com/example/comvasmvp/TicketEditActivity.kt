package com.example.comvasmvp

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import android.widget.ListView
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_project_edit.*
import kotlinx.android.synthetic.main.activity_ticket_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class TicketEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ticket_edit)

        realm = Realm.getDefaultInstance()

        val projectId = intent?.getLongExtra("project_id", -1L)
        val boardId = intent?.getLongExtra("board_id", -1L)
        val ticketId = intent?.getLongExtra("ticket_id", -1L)
        if (ticketId != -1L) {
            val ticket = realm.where<Ticket>().equalTo("ticketId", ticketId).findFirst()
            ticketTitleEdit.setText(ticket?.title)
            ticketDeadlineEdit.setText(DateFormat.format("yyyy/MM/dd", ticket?.date))
            ticketPriorityEdit.setText(ticket?.priority?.toString())
            ticketDetailEdit.setText(ticket?.detail)
            ticketPointEdit.setText(ticket?.point?.toString())
            ticketProgressEdit.setText(ticket?.progress?.toString())
            ticketTagEdit.setText(ticket?.tag)

            deleteTicket.visibility = View.VISIBLE
            ticketPriorityEdit.visibility = View.VISIBLE
            textView6.visibility = View.VISIBLE
            textView11.visibility = View.VISIBLE
            commentListView.visibility = View.VISIBLE
            postComment.visibility = View.VISIBLE
        } else {
            deleteTicket.visibility = View.INVISIBLE
            ticketPriorityEdit.visibility = View.INVISIBLE
            textView6.visibility = View.INVISIBLE
            textView11.visibility = View.INVISIBLE
            commentListView.visibility = View.INVISIBLE
            postComment.visibility = View.INVISIBLE
        }

        saveTicket.setOnClickListener {
            when (ticketId) {
                -1L -> {
                    realm.executeTransaction {
                        // Create new data for ticket
                        val ticketMaxId = realm.where<Ticket>().max("ticketId")
                        val ticketNextId = (ticketMaxId?.toLong() ?: 0L) + 1
                        val ticket = realm.createObject<Ticket>(ticketNextId)

                        ticket.title = ticketTitleEdit.text.toString()
                        ticketDeadlineEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            ticket.deadline = it
                        }
                        val board = realm.where<Board>().equalTo("boardId", boardId).findFirst()
                        val ticketMaxPriority = board?.ticketList?.where()?.max("priority")
                        val ticketNextPriority = (ticketMaxPriority?.toLong() ?: 0L) + 1
                        ticket.priority = ticketNextPriority
                        ticket.detail = ticketDetailEdit.text.toString()
                        if (ticketPointEdit.text.toString() == "") {
                            ticket.point = 0L
                        } else {
                            ticket.point = ticketPointEdit.text.toString().toLong()
                        }
                        ticket.progress = 0
                        ticket.tag = ticketTagEdit.text.toString()

                        // Set relationship between board and ticket
                        board?.ticketList?.add(ticket)
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val ticket = realm.where<Ticket>().equalTo("ticketId", ticketId).findFirst()

                        ticket?.title = ticketTitleEdit.text.toString()
                        ticketDeadlineEdit.text.toString().toDate("yyyy.MM.dd")?.let {
                            ticket?.deadline = it
                        }
                        ticket?.priority = ticketPriorityEdit.text.toString().toLong()
                        ticket?.detail = ticketDetailEdit.text.toString()
                        ticket?.point = ticketPointEdit.text.toString().toLong()
                        ticket?.progress = ticketProgressEdit.text.toString().toLong()
                        ticket?.tag = ticketTagEdit.text.toString()
                    }
                    alert("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        deleteTicket.setOnClickListener {
            realm.executeTransaction {
                realm.where<Ticket>().equalTo("ticketId", ticketId)?.findFirst()?.deleteFromRealm()
            }
            alert("削除しました") {
                yesButton { finish() }
            }.show()
        }


        // コメントのリストの設定
        val ticket = realm.where<Ticket>().equalTo("ticketId", ticketId).findFirst()
        if (ticket?.commentList?.isEmpty() != true){
            val comments = ticket?.commentList?.where()?.findAll()
            val commentListView: ListView = findViewById(R.id.commentListView)
            commentListView.adapter = CommentAdapter(comments)
        }

        // コメント追加ボタンの設定
        postComment.setOnClickListener {
            val intent = Intent(this, CommentEditActivity::class.java)
            intent.putExtra("project_id", projectId)
            intent.putExtra("board_id", boardId)
            intent.putExtra("ticket_id", ticketId)
            startActivity(intent)
            //activity?.startActivityFromFragment(this, intent, -1)

            // refresh
            val comments = ticket?.commentList?.where()?.findAll()
            var commentListView: ListView = findViewById(R.id.commentListView)
            commentListView.adapter = CommentAdapter(comments)
        }

        // リスト内のコメントクリック時の処理
        var commentListView: ListView = findViewById(R.id.commentListView)
        commentListView.setOnItemClickListener { parent, view, position, id ->
            val comment = parent.getItemAtPosition(position) as Comment
            val intent = Intent(this, CommentEditActivity::class.java)
            intent.putExtra("project_id", projectId)
            intent.putExtra("board_id", boardId)
            intent.putExtra("ticket_id", ticketId)
            intent.putExtra("comment_id", comment.commentId)
            startActivity(intent)
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