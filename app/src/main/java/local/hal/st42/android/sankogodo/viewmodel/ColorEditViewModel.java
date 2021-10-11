package local.hal.st42.android.sankogodo.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.ExecutionException;

import local.hal.st42.android.sankogodo.dataaccess.AppDatabase;
import local.hal.st42.android.sankogodo.dataaccess.ColorDAO;
import local.hal.st42.android.sankogodo.dataaccess.Colors;

public class ColorEditViewModel extends AndroidViewModel {
    /**
     * データベースオブジェクト。
     */
    private AppDatabase _db;

    /**
     * コンストラクタ。
     *
     * @param application アプリケーションオブジェクト。
     */
    public ColorEditViewModel(Application application) {
        super(application);
        _db = AppDatabase.getDatabase(application);
    }

    /**
     * 引数の主キーに該当する情報を取得するメソッド。
     *
     * @param id 主キー値。
     * @return 引数に該当するカラーオブジェクト。該当データが存在しない場合は、空のカラーオブジェクト。
     */
    public Colors getColor(int id) {
        ColorDAO colorDAO = _db.colorDAO();
        ListenableFuture<Colors> future = colorDAO.findByPK(id);
        Colors color = new Colors();
        try {
            color = future.get();
        }
        catch(ExecutionException ex) {
            Log.e("ColorEditViewModel", "データ取得処理失敗", ex);
        }
        catch(InterruptedException ex) {
            Log.e("ColorEditViewModel", "データ削除処理失敗", ex);
        }
        return color;
    }

    /**
     * 登録メソッド。
     *
     * @param color 登録するカラー情報。
     * @return 登録されたカラー情報の主キー値。登録に失敗した場合は0。
     */
    public long insert(Colors color) {
        ColorDAO colorDAO = _db.colorDAO();
        ListenableFuture<Long> future = colorDAO.insert(color);
        long result = 0;
        try {
            result = future.get();
        }
        catch(ExecutionException ex) {
            Log.e("ColorEditViewModel", "データ登録処理失敗", ex);
        }
        catch(InterruptedException ex) {
            Log.e("ColorEditViewModel", "データ登録処理失敗", ex);
        }
        return result;
    }

    /**
     * カラー情報更新メソッド。
     *
     * @param color 更新するカラー情報。
     * @return 更新件数。更新に失敗した場合は0。
     */
    public int update(Colors color) {
        ColorDAO colorDAO = _db.colorDAO();
        ListenableFuture<Integer> future = colorDAO.update(color);
        int result = 0;
        try {
            result = future.get();
        }
        catch(ExecutionException ex) {
            Log.e("ColorEditViewModel", "データ更新処理失敗", ex);
        }
        catch(InterruptedException ex) {
            Log.e("ColorEditViewModel", "データ更新処理失敗", ex);
        }
        return result;
    }

    /**
     * レポート情報削除メソッド。
     *
     * @param id 削除対象レポートの主キー値。
     * @return 削除件数。削除に失敗した場合は0。
     */
    public int delete(int id) {
        Colors colors = new Colors();
        colors.id = id;
        ColorDAO colorDAO = _db.colorDAO();
        ListenableFuture<Integer> future = colorDAO.delete(colors);
        int result = 0;
        try {
            result = future.get();
        }
        catch(ExecutionException ex) {
            Log.e("ColorEditViewModel", "データ削除処理失敗", ex);
        }
        catch(InterruptedException ex) {
            Log.e("ColorEditViewModel", "データ削除処理失敗", ex);
        }
        return result;
    }
}
