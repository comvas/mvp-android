package com.example.comvasmvp

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


class BoardFragment : Fragment() {

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
        val adapter = CustomPagerAdapter(childFragmentManager)
        adapter.addFragment(ColorFragment.createInstance("hoge", Color.RED, projectId), "HI-FIVES")
        adapter.addFragment(ColorFragment.createInstance("hoge", Color.BLUE, projectId), "HI-FIVES")
        adapter.addFragment(ColorFragment.createInstance("hoge", Color.RED, projectId), "HI-FIVES")
        viewPager.adapter = adapter

    }
}
