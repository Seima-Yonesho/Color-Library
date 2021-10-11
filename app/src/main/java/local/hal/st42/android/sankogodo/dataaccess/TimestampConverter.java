package local.hal.st42.android.sankogodo.dataaccess;

import androidx.room.TypeConverter;

import java.sql.Timestamp;

/**
 * ST42 Android
 *
 * 日時に関するデータベース上のデータ型とJavaのデータ型を変換するクラス。
 *
 * @author Seima Yonesho
 */
public class TimestampConverter {
    /**
     * 日時を表すUNIXタイムスタンプのミリセカンド値(long)をJavaのTimestamp型に変換するメソッド。
     *
     * @param value 変換元の日時を表すUNIXタイムスタンプのミリセカンド値。
     * @return 引数をもとに変換されたTimestampオブジェクト。
     */
    @TypeConverter
    public static Timestamp toTimestamp(Long value) {
        Timestamp returnVal = null;
        if(value != null) {
            returnVal = new Timestamp(value);
        }
        return returnVal;
    }

    /**
     *
     日時を表すJavaのTimestampオブジェクトからUNIXタイムスタンプのミリセカンド値(long)に変換するメソッド。
     *
     * @param value 変換元のTimestampオブジェクト。
     * @return 変換されたUNIXタイムスタンプのミリセカンド値。
     */
    @TypeConverter
    public static Long toLong(Timestamp value) {
        Long returnVal = null;
        if(value != null) {
            returnVal = value.getTime();
        }
        return returnVal;
    }
}