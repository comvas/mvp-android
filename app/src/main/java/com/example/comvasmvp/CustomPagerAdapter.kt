package com.example.comvasmvp

import android.os.Bundle
import android.support.annotation.ColorInt
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import java.nio.file.Files.size
import android.support.v4.app.FragmentPagerAdapter



//import java.util.List

//import android.content.Context
//import android.support.v4.view.PagerAdapter
//import android.view.View
//import android.view.ViewGroup
//
//class CustomPagerAdapter(context: Context, colorList: List<Int>) : PagerAdapter() {
//
//    private var mContext: Context = context
//    private var mColorList: List<Int> = colorList
//
////    fun CustomPagerAdapter(context: Context, colorList: List<Int>): ??? {
////        mContext = context
////        mColorList = colorList
////    }
//
//    override fun instantiateItem(container: ViewGroup, position: Int): Any {
//        // リストからページの背景色を取得します。
//        val color = mColorList.get(position)
//
//        // Viewを生成します。
//        val view = View(mContext)
//        view.setBackgroundColor(color)
//
//        // 生成したViewをコンテナに追加します。
//        container.addView(view)
//        return view
//    }
//
//    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
//        container.removeView(`object` as View)
//    }
//
//    override fun getCount(): Int {
//        return mColorList.size
//    }
//
//    override fun isViewFromObject(view: View, `object`: Any): Boolean {
//        return view === `object`
//    }
//}

class CustomPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    private var mFragments: ArrayList<Fragment> = ArrayList()
    private val mFragmentTitles: ArrayList<String> = ArrayList()

    private val KEY_NAME = "key_name"
    private val KEY_BACKGROUND = "key_background_color"

    fun addFragment(fragment: Fragment, title: String, name: String, @ColorInt color: Int) {

        // Fragmentに渡す値はBundleという型でやり取りする
        val args = Bundle()
        // Key/Pairの形で値をセットする
        args.putString(KEY_NAME, name)
        args.putInt(KEY_BACKGROUND, color)
        // Fragmentに値をセットする
        fragment.setArguments(args)

        mFragments.add(fragment)
        mFragmentTitles.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return mFragments.get(position)
    }

    override fun getCount(): Int {
        return mFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragmentTitles.get(position)
    }
}