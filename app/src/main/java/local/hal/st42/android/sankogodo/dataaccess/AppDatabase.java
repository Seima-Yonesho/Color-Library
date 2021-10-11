package local.hal.st42.android.sankogodo.dataaccess;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

/**
 * ST42 Android
 * データベースオブジェクトクラス
 *
 * @author Seima Yonesho
 */
@Database(entities = {Colors.class}, version = 1, exportSchema = false)
@TypeConverters(TimestampConverter.class)
public abstract class AppDatabase extends RoomDatabase {
    /**
     * データベースインスタンス。
     */
    private static AppDatabase _instance;

    /**
     * データベースインスタンスを得るメソッド。
     * シングルトンパターンに従ってインスタンスを生成する。
     *
     * @param context コンテキスト。
     * @return データベースインスタンス。
     */
    public static AppDatabase getDatabase(Context context) {
        if (_instance == null) {
            _instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "color_db").build();
        }
        return _instance;
    }

    /**
     * オブジェクトを生成するメソッド。
     *
     * @return TaskDAOオブジェクト。
     */
    public abstract ColorDAO colorDAO();

}