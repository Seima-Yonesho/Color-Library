package local.hal.st42.android.sankogodo.dataaccess;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.sql.Timestamp;

/**
 *
 * ST42 Android
 *
 * 色情報を格納するエンティティクラス。
 *
 * @author Seima Yonesho
 */
@Entity
public class Colors {
    /**
     * 主キーのid
     */
    @PrimaryKey(autoGenerate = true)
    public int id;
    /**
     * カラーコード
     */
    @NonNull
    public int colorcode;
    /**
     * カラー名
     */
    public String colorname;
    /*
    * Red
     */
    public int redincolor;
    /*
    * Green
     */
    public int greenincolor;
    /*
    * Blue
     */
    public int blueincolor;
    /*
    * メモ
     */
    public String memo;
    /**
     * 登録日時。
     */
    @NonNull
    public Timestamp insertAt;
    /**
     * 更新日時。
     */
    @NonNull
    public Timestamp updatedAt;

}
