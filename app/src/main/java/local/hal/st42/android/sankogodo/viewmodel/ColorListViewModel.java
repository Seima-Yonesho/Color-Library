package local.hal.st42.android.sankogodo.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import local.hal.st42.android.sankogodo.Consts;
import local.hal.st42.android.sankogodo.dataaccess.AppDatabase;
import local.hal.st42.android.sankogodo.dataaccess.Colors;
import local.hal.st42.android.sankogodo.dataaccess.ColorDAO;

public class ColorListViewModel extends AndroidViewModel {
    /**
     * データベースオブジェクト。
     */
    private AppDatabase _db;

    /**
     * コンストラクタ。
     *
     * @param application アプリケーションオブジェクト。
     */
    public ColorListViewModel(Application application) {
        super(application);
        _db = AppDatabase.getDatabase(application);
    }

    /**
     * 情報リストを取得するメソッド。
     *
     * @param menuCategory
     * @return レポート情報リストに関連するLiveDateオブジェクト
     */
    public LiveData<List<Colors>> getColorList(int menuCategory) {
        ColorDAO colorDAO = _db.colorDAO();
        LiveData<List<Colors>> colorList;

        if (menuCategory == Consts.ALL){
            colorList = colorDAO.findAll();
        }
        else {
            colorList = colorDAO.findAll();
        }

        return colorList;
    }
}
