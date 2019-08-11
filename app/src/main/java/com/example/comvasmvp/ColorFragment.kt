package com.example.comvasmvp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.R.attr.button
import android.graphics.Color
import android.widget.TextView
import android.support.annotation.ColorInt
import android.support.annotation.CheckResult



class ColorFragment : Fragment() {

    // このクラス内でだけ参照する値のため、BundleのKEYの値をprivateにする
    private val KEY_NAME = "key_name"
    private val KEY_BACKGROUND = "key_background_color"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return  inflater.inflate(R.layout.fragment_color, container, false)
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


    // 値をonCreateで受け取るため、新規で変数を作成する
    // 値がセットされなかった時のために初期値をセットする
    private var mName: String? = ""
    @ColorInt
    private var mBackgroundColor = Color.TRANSPARENT

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
        }
    }

    // Viewが生成し終わった時に呼ばれるメソッド
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        // TextViewをひも付けます
//        var mTextView: TextView = view.findViewById(R.id.textView)
//        // Buttonのクリックした時の処理を書きます
//        view.findViewById(R.id.button)
//            .setOnClickListener(View.OnClickListener { mTextView.setText(mTextView.getText() + "!") })

        // 背景色をセットする
        view.setBackgroundColor(mBackgroundColor)
//        // onCreateで受け取った値をセットする
//        mTextView.setText(mName)
    }
}