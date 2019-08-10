package com.example.comvasmvp

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.format.DateFormat
import android.view.View
import io.realm.Realm
import io.realm.kotlin.createObject
import io.realm.kotlin.where
import kotlinx.android.synthetic.main.activity_project_edit.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.yesButton
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class ProjectEditActivity : AppCompatActivity() {
    private lateinit var realm: Realm


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project_edit)

        realm = Realm.getDefaultInstance()

        val projectId = intent?.getLongExtra("project_id", -1L)
        if (projectId != -1L) {
            val project = realm.where<Project>().equalTo("projectId", projectId).findFirst()
            dateEdit.setText(DateFormat.format("yyyy/MM/dd", project?.date))
            titleEdit.setText(project?.title)
            detailEdit.setText(project?.detail)

            deleteProject.visibility = View.VISIBLE
        } else {
            deleteProject.visibility = View.INVISIBLE
        }

        saveProject.setOnClickListener {
            when (projectId) {
                -1L -> {
                    realm.executeTransaction {
                        val maxId = realm.where<Project>().max("projectId")
                        val nextId = (maxId?.toLong() ?: 0L) + 1
                        val project = realm.createObject<Project>(nextId)
                        dateEdit.text.toString().toDate("yyyy/MM/dd")?.let {
                            project.date = it
                        }
                        project.title = titleEdit.text.toString()
                        project.detail = detailEdit.text.toString()
                    }
                    alert("追加しました") {
                        yesButton { finish() }
                    }.show()
                }
                else -> {
                    realm.executeTransaction {
                        val project = realm.where<Project>().equalTo("projectId", projectId).findFirst()
                        dateEdit.text.toString().toDate("yyyy.MM.dd")?.let {
                            project?.date = it
                        }
                        project?.title = titleEdit.text.toString()
                        project?.detail = detailEdit.text.toString()
                    }
                    alert("修正しました") {
                        yesButton { finish() }
                    }.show()
                }
            }
        }

        deleteProject.setOnClickListener {
            realm.executeTransaction {
                realm.where<Project>().equalTo("projectId", projectId)?.findFirst()?.deleteFromRealm()
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
