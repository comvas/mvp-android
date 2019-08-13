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
import java.util.*


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

        // ヘッダに目標と期日表示
        val projectTitleTextView: TextView = view.findViewById(R.id.projectTitleText)
        val projectDateTextView: TextView = view.findViewById(R.id.projectDateText)
        projectTitleTextView.setText(project?.title)
        projectDateTextView.setText(DateFormat.format("yyyy/MM/dd", project?.date).toString())

        // 残り日数を計算
        val remainingDate = dateDiff(Date(), project!!.date)
        val projectRemainingDateTextView: TextView = view.findViewById(R.id.projectRemainingDateText)
        projectRemainingDateTextView.setText("残り日数： " + remainingDate.toString() + "日")

        // 残り時間を計算
        val projectRemainingTimeTextView: TextView = view.findViewById(R.id.projectRemainingTimeText)
        projectRemainingTimeTextView.setText("残り時間(推定)： " + (remainingDate * 3).toString() + "時間 (1日当たり3.0時間計算)")


    }

    // 日にちの差分を計算
    fun dateDiff(dateFrom: Date, dateTo: Date): Int {
//        val sdf = SimpleDateFormat("yyyy/MM/dd")
//        var dateTo: Date? = null
//        var dateFrom: Date? = null
//
//        // Date型に変換
//        try {
//            dateFrom = sdf.parse(dateFromStrig)
//            dateTo = sdf.parse(dateToString)
//        } catch (e: java.text.ParseException) {
//            e.printStackTrace()
//        }

        // 差分の日数を計算する
        val dateTimeTo = dateTo!!.getTime()
        val dateTimeFrom = dateFrom!!.getTime()
        val dayDiff = (dateTimeTo - dateTimeFrom) / (1000 * 60 * 60 * 24)

        //println("差分日数 : $dayDiff")
        return dayDiff.toInt()
    }
}