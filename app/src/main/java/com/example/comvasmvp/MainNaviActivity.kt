package com.example.comvasmvp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainNaviActivity : AppCompatActivity() {

    //private val projectId: Long = intent.getLongExtra("project_id", -1L)
    //private val projectId: Long = 0
    //private val projectId: Long = intent.getLongExtra("project_id", 0)
    private var projectId: Long = 0

    private lateinit var textMessage: TextView
    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, HomeFragment())
                    .commit()
//                textMessage.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_board -> {
                val transaction = supportFragmentManager.beginTransaction()
                transaction.replace(R.id.frameLayout, BoardFragment.createInstance(projectId))
                transaction.commit()
//                supportFragmentManager.beginTransaction()
//                    .replace(R.id.frameLayout, BoardFragment)
//                    .commit()
//                textMessage.setText(R.string.title_board)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_backlog -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, BacklogFragment())
                    .commit()
//                textMessage.setText(R.string.title_backlog)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_roadmap -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.frameLayout, RoadmapFragment())
                    .commit()
//                textMessage.setText(R.string.title_roadmap)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_navi)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        //textMessage = findViewById(R.id.message)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, HomeFragment())
            .commit()

        //projectId = intent?.getLongExtra("project_id", -1L)
        projectId = intent.getLongExtra("project_id", -1L)
    }
}
