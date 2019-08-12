package com.example.comvasmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.graphics.Color
import android.widget.TextView
import android.support.annotation.ColorInt
import android.widget.Button
import android.widget.ListView
import io.realm.Realm
import io.realm.kotlin.where

class BoardContentsFragment  : Fragment() {

    private lateinit var realm: Realm

    // このクラス内でだけ参照する値のため、BundleのKEYの値をprivateにする
    private val KEY_NAME = "key_name"
    private val KEY_BACKGROUND = "key_background_color"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_board_contents, container, false)
    }

//    // このメソッドからFragmentを作成することを強制する
//    @CheckResult
//    fun createInstance(name: String, @ColorInt color: Int): ColorFragment {
//        // Fragmentを作成して返すメソッド
//        // createInstanceメソッドを使用することで、そのクラスを作成する際にどのような値が必要になるか制約を設けることができる
//        val fragment = ColorFragment()
//        // Fragmentに渡す値はBundleという型でやり取りする
//        val args = Bundle()
//        // Key/Pairの形で値をセットする
//        args.putString(KEY_NAME, name)
//        args.putInt(KEY_BACKGROUND, color)
//        // Fragmentに値をセットする
//        fragment.setArguments(args)
//        return fragment
//    }

    companion object {
        private val KEY_NAME = "key_name"
        private val KEY_BACKGROUND = "key_background_color"
        private val KEY_PROJECTID = "project_id"
        private val KEY_BOARDID = "board_id"

        fun createInstance(name: String, @ColorInt color: Int, projectId: Long, boardId: Long): BoardContentsFragment {
            var boardContentsFragment = BoardContentsFragment()
            val args = Bundle()
            // Key/Pairの形で値をセットする
            args.putString(KEY_NAME, name)
            args.putInt(KEY_BACKGROUND, color)
            args.putLong(KEY_PROJECTID, projectId)
            args.putLong(KEY_BOARDID, boardId)
            boardContentsFragment.arguments = args

            return boardContentsFragment
        }
    }


    // 値をonCreateで受け取るため、新規で変数を作成する
    // 値がセットされなかった時のために初期値をセットする
    private var mName: String? = ""
    @ColorInt
    private var mBackgroundColor = Color.TRANSPARENT
    private var projectId: Long = 0
    private var boardId: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Bundleの値を受け取る際はonCreateメソッド内で行う
        val args = arguments
        // Bundleがセットされていなかった時はNullなのでNullチェックをする
        if (args != null) {
            // String型でNameの値を受け取る
            mName = args.getString(KEY_NAME)
            // int型で背景色を受け取る
            mBackgroundColor = args.getInt(KEY_BACKGROUND, Color.TRANSPARENT)
            projectId = args.getLong(KEY_PROJECTID)
            boardId = args.getLong(KEY_BOARDID)
        }
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // TextViewをひも付けます
        var mTextView: TextView = view.findViewById(R.id.boardStatusView)
//        // Buttonのクリックした時の処理を書きます
//        view.findViewById(R.id.button)
//            .setOnClickListener(View.OnClickListener { mTextView.setText(mTextView.getText() + "!") })

        // 背景色をセットする
        view.setBackgroundColor(mBackgroundColor)

        // Ticketのリスト表示
        realm = Realm.getDefaultInstance()
        val project = realm.where<Project>().equalTo("projectId", projectId).findFirst()
        val board = project?.boardList?.where()?.equalTo("boardId", boardId)?.findFirst()
        //val boards = project?.boardList?.where()?.findAll()
        //val board = boards?.get(boardId.toInt())

        // ボードのタイトルをセットする
        mTextView.setText(board?.title)

        if (board?.ticketList?.isEmpty() == true){
        } else {
            val tickets = board?.ticketList?.where()?.findAll()
            //listView.adapter = TicketAdapter(tickets)
            var ticketListView: ListView = view.findViewById(R.id.ticketListView)
            ticketListView.adapter = TicketAdapter(tickets)
        }

        // Buttonクリック時の処理
        var createTicketButton: Button = view.findViewById(R.id.createTicketButton)
        createTicketButton.setOnClickListener {
            val intent = Intent(activity, TicketEditActivity::class.java)
            intent.putExtra("project_id", projectId)
            intent.putExtra("board_id", boardId)
            startActivity(intent)
            //activity?.startActivityFromFragment(this, intent, -1)

            // refresh
            val tickets = board?.ticketList?.where()?.findAll()
            var ticketListView: ListView = view.findViewById(R.id.ticketListView)
            ticketListView.adapter = TicketAdapter(tickets)
        }

        // リスト内のチケットクリック時の処理
        var ticketListView: ListView = view.findViewById(R.id.ticketListView)
        ticketListView.setOnItemClickListener { parent, view, position, id ->
            val ticket = parent.getItemAtPosition(position) as Ticket
            val intent = Intent(activity, TicketEditActivity::class.java)
            intent.putExtra("project_id", projectId)
            intent.putExtra("board_id", boardId)
            intent.putExtra("ticket_id", ticket.ticketId)
            startActivity(intent)

//            // refresh
//            val tickets = board?.ticketList?.where()?.findAll()
//            var ticketListView: ListView = view.findViewById(R.id.ticketListView)
//            ticketListView.adapter = TicketAdapter(tickets)
        }

    }
}