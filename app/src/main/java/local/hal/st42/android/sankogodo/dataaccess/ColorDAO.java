package local.hal.st42.android.sankogodo.dataaccess;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.List;

/**
 * ST42 Android
 *
 * 色情報を操作するDAOインターフェース。
 *
 * @author Seima Yonesho
 */

@Dao
public interface ColorDAO {
    /**
     * 全データ検索メソッド。
     *
     * @return 検索結果のListオブジェクトに関連するLiveDataオブジェクト。
     */
    @Query("SELECT * FROM colors ORDER BY updatedAt DESC")
    LiveData<List<Colors>> findAll();

    /**
     * 主キーによる検索メソッド。
     *
     * @param id 主キー値。
     * @return 主キーに対応するデータを格納したTaskオブジェクトに関連するListenableFutureオブジェクト。
     */
    @Query("SELECT * FROM colors WHERE id = :id")
    ListenableFuture<Colors> findByPK(int id);

    /**
     * メニューカテゴリによる検索メソッド。
     *
     * @param workkind カテゴリ値。
     * @return 検索結果のListオブジェクトに関連するLiveDataオブジェクト。
     */

    /**
     * 情報を新規登録するメソッド。
     *
     * @param colors 登録情報が格納されたReportオブジェクト。
     * @return 新規登録された主キー値に関連するListenableFutureオブジェクト。
     */
    @Insert
    ListenableFuture<Long> insert(Colors colors);

    /**
     * 情報を更新するメソッド。
     *
     * @param colors 更新情報が格納されたReportオブジェクト。
     * @return 更新件数を表す値に関連するListenableFutureオブジェクト。
     */
    @Update
    ListenableFuture<Integer> update(Colors colors);

    /**
     * 情報を削除するメソッド。
     *
     * @param colors 削除レポート情報が格納されたReportオブジェクト。
     * @return 削除件数を表す値に関連するListenableFutureオブジェクト。
     */
    @Delete
    ListenableFuture<Integer> delete(Colors colors);
}
