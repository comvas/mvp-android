package com.example.comvasmvp

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.realm.Realm
import io.realm.kotlin.where
import io.realm.RealmResults




class BoardFragment : Fragment() {

    private lateinit var realm: Realm
    private var projectId: Long = 0

    companion object {
        private const val KEY_PROJECTID = "project_id"

        fun createInstance(projectId: Long): BoardFragment {
            val boardFragment = BoardFragment()
            val args = Bundle()
            args.putLong(KEY_PROJECTID, projectId)
            boardFragment.arguments = args
            return boardFragment
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
        val view = inflater.inflate(R.layout.fragment_board, container, false)
        setHasOptionsMenu(true)

        val viewPager: ViewPager = view.findViewById(R.id.viewPager)
//        if (viewPager != null) {
//            setupViewPager(viewPager)
//        }
        setupViewPager(viewPager)

        return view
    }

    private fun setupViewPager(viewPager: ViewPager) {
        realm = Realm.getDefaultInstance()
        val project = realm.where<Project>().equalTo("projectId", projectId).findFirst()
        val boards = project?.boardList?.where()?.findAll()

        // 本当は動的に実装すべき
        // MVPでは3つ決めうち、かつ数に変更なしなので以下の実装をしている
        // しかし、実際の環境ではボード数と順番は変化するため、変更が必要
        // １，boardListのインスタンスにおいて、priorityでソート
        // ２，forで回しながら、プライオリティ昇順にfragmentを生成していく
        val adapter = CustomPagerAdapter(childFragmentManager)
        //adapter.addFragment(ColorFragment.createInstance("hoge", Color.RED, projectId, boards?.get(0)!!.boardId), "HI-FIVES")
        adapter.addFragment(ColorFragment.createInstance("hoge", Color.parseColor("#3F7A63"), projectId, boards?.get(0)!!.boardId), "HI-FIVES")
        adapter.addFragment(ColorFragment.createInstance("hoge", Color.parseColor("#F29089"), projectId, boards?.get(1)!!.boardId), "HI-FIVES")
        adapter.addFragment(ColorFragment.createInstance("hoge", Color.parseColor("#3F7A63"), projectId, boards?.get(2)!!.boardId), "HI-FIVES")
        viewPager.adapter = adapter
    }
}
