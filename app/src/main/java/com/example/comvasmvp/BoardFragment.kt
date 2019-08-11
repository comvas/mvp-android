package com.example.comvasmvp

import android.graphics.Color
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup




class BoardFragment : Fragment() {

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
        //adapter.addFragment(Tab1Fragment(), "PHOTOS")
        //adapter.addFragment(Tab2Fragment(), "HI-FIVES")
        adapter.addFragment(ColorFragment(), "HI-FIVES", "hoge", Color.RED)
        adapter.addFragment(ColorFragment(), "HI-FIVES", "hoge", Color.BLUE)
        adapter.addFragment(ColorFragment(), "HI-FIVES", "hoge", Color.RED)
        viewPager.adapter = adapter

    }
}