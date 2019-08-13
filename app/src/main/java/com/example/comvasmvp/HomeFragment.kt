package com.example.comvasmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.format.DateFormat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.realm.Realm
import io.realm.kotlin.where


class HomeFragment : Fragment() {

    private lateinit var realm: Realm
    private var projectId: Long = 0

    companion object {
        private const val KEY_PROJECTID = "project_id"

        fun createInstance(projectId: Long): HomeFragment {
            val homeFragment = HomeFragment()
            val args = Bundle()
            args.putLong(KEY_PROJECTID, projectId)
            homeFragment.arguments = args
            return homeFragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val args = arguments
        if (args != null) {
            projectId = args.getLong(KEY_PROJECTID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_home, container, false)
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        realm = Realm.getDefaultInstance()
        val project = realm.where<Project>().equalTo("projectId", projectId).findFirst()

        var projectTitleTextView: TextView = view.findViewById(R.id.projectTitleText)
        var projectDateTextView: TextView = view.findViewById(R.id.projectDateText)
        projectTitleTextView.setText(project?.title)
        projectDateTextView.setText(DateFormat.format("yyyy/MM/dd", project?.date).toString())
    }
}