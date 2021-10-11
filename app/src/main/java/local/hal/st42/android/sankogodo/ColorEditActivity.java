package local.hal.st42.android.sankogodo;


import androidx.appcompat.app.AppCompatActivity;

/**
 * ST42 Android
 *
 * 第2画面・第4画面表示用アクティビティクラス
 * 新規登録
 * 編集
 * 管理データ項目のうちシステム管理項目以外を入力できるようにする
 *
 * @author Seima Yonesho
 */
public class ColorEditActivity extends AppCompatActivity {
    /**
     * 新規登録モードか更新モードかを表すフィールド。
     */
    private int _mode = Consts.MODE_INSERT;
    /**
     * 更新モードの際、現在表示しているリスト情報のデータベース上の主キー値。
     */
    private int _idNo = 0;
}
