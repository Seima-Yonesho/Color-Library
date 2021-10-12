package local.hal.st42.android.sankogodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.appbar.CollapsingToolbarLayout;

import java.util.ArrayList;
import java.util.List;

import local.hal.st42.android.sankogodo.dataaccess.Colors;
import local.hal.st42.android.sankogodo.viewmodel.ColorListViewModel;

public class MainActivity extends AppCompatActivity {
    /**
     * リサイクラービューを表すフィールド。
     */
    private RecyclerView _rvColor;
    /**
     * メニューリストの種類を表すフィールド。
     */
    private int _menuCategory;
    /**
     * リサイクラービューで利用するアダプタオブジェクト。
     */
    private ColorListAdapter _adapter;
    /**
     * リストビューモデルオブジェクト。
     */
    private ColorListViewModel _colorListViewModel;
    /**
     * リストLiveDataオブジェクト。
     */
    private LiveData<List<Colors>> _colorListLiveData;
    /**
     * リストLiveData変更時に対応するオブザーバーオブジェクト。
     */
    private ColorListObserver _colorListObserver;
    /**
     * プレファレンスファイル名を表す定数フィールド。
     */
    private static final String PREFS_NAME = "ColorFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*
         * ツールバー操作
         */
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        CollapsingToolbarLayout toolbarLayout = findViewById(R.id.toolbarLayout);
        toolbarLayout.setTitle(getString(R.string.app_name));
        toolbarLayout.setExpandedTitleColor(Color.WHITE);
        toolbarLayout.setCollapsedTitleTextColor(Color.LTGRAY);

        _rvColor = findViewById(R.id.rvColor);
        LinearLayoutManager layout = new LinearLayoutManager(MainActivity.this);
        _rvColor.setLayoutManager(layout);
        DividerItemDecoration decoration = new DividerItemDecoration(MainActivity.this, layout.getOrientation());
        _rvColor.addItemDecoration(decoration);
        List<Colors> reportList = new ArrayList<>();
        _adapter = new ColorListAdapter(reportList);
        _rvColor.setAdapter(_adapter);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider provider = new ViewModelProvider(MainActivity.this, factory);
        _colorListViewModel = provider.get(ColorListViewModel.class);
        _colorListObserver = new ColorListObserver();
        _colorListLiveData = new MutableLiveData<>();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        _menuCategory = settings.getInt("WorkCategory", Consts.ALL);
        createRecyclerView();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_options_activity_main, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuListOptionTitle = menu.findItem(R.id.menuListOptionTitle);
        switch (_menuCategory) {
            case Consts.ALL:
                menuListOptionTitle.setTitle(R.string.menu_list_all);
                break;
        }
        return super.onPrepareOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        SharedPreferences.Editor editor   = settings.edit();
        boolean returnVal = true;
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuListOptionAll:
                _menuCategory = Consts.ALL;
                editor.putInt("WorkCategory", _menuCategory);
                invalidateOptionsMenu();
                break;
            default:
                returnVal = super.onOptionsItemSelected(item);
        }
        if(returnVal) {
            createRecyclerView();
        }
        editor.commit();
        return returnVal;
    }

    /**
     * 新規ボタンが押されたときのイベント処理用メソッド。
     *
     * @param view 画面部品。
     */
    public void onFabAddClicked(View view) {
        Intent intent = new Intent(getApplicationContext(), ColorEditActivity.class);
        intent.putExtra("mode", Consts.MODE_INSERT);
        startActivity(intent);
    }

    /**
     * リスト画面表示用のデータを生成するメソッド。
     * フィールド_onlyImportantの値に合わせて生成するデータを切り替える。
     */
    private void createRecyclerView() {
        _colorListLiveData.removeObserver(_colorListObserver);
        _colorListLiveData = _colorListViewModel.getColorList(_menuCategory);
        _colorListLiveData.observe(MainActivity.this, _colorListObserver);
    }

    /**
     * ビューモデル中のメモ情報リストに変更があった際に、画面の更新を行う処理が記述されたクラス。
     */
    private class ColorListObserver implements Observer<List<Colors>> {
        @Override
        public void onChanged(List<Colors> colorList) {
            _adapter.changeColorList(colorList);
        }
    }

    /**
     * リサイクラービューで利用するビューホルダクラス。
     */
    private static class ColorViewHolder extends RecyclerView.ViewHolder {
        /**
         * ID
         */
        public Integer id;
        /**
         * タイトル表示用TextViewフィールド。
         */
        public TextView _tvRowTitle;
        /**
         * カラー表示用TextViewフィールド
         */
        public TextView _tvRowDate;
        /**
         * 編集ボタン
         */
        public Button _btRowEdit;

        /**
         * コンストラクタ。
         *
         * @param itemView リスト1行分の画面部品。
         */
        public ColorViewHolder(View itemView) {
            super(itemView);
            _tvRowTitle = itemView.findViewById(R.id.tvRowTitle);
            _tvRowDate = itemView.findViewById(R.id.tvRowDate);
            _btRowEdit = itemView.findViewById(R.id.btRowEdit);
        }
    }

    /**
     * リサイクラービューで利用するアダプタクラス。
     */
    private class ColorListAdapter extends RecyclerView.Adapter<ColorViewHolder> {
        /**
         * リストデータを表すフィールド。
         */
        private List<Colors> _listData;

        /**
         * コンストラクタ。
         *
         * @param listData
         */
        public ColorListAdapter(List<Colors> listData) {
            _listData = listData;
        }

        @Override
        public ColorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View row = inflater.inflate(R.layout.row_activity_main, parent, false);
            row.setOnClickListener(new ListItemClickListener());
            ColorViewHolder holder = new ColorViewHolder(row);
            return new ColorViewHolder(row);
        }

        @Override
        public void onBindViewHolder(ColorViewHolder holder, int position) {
            Colors item = _listData.get(position);
            holder.id = item.id;
            holder._tvRowTitle.setText(item.colorcode);
            holder._btRowEdit.setTag(item.id);
            holder._btRowEdit.setOnClickListener(new OnEditButtonClickListener());
            holder._tvRowDate.setBackgroundColor(Color.parseColor(String.valueOf(item.colorcode)));
            LinearLayout row = (LinearLayout) holder._tvRowTitle.getParent().getParent();
            row.setTag(item.id);
        }

        @Override
        public int getItemCount() {
            return _listData.size();
        }

        /**
         * 内部で保持しているリストデータを丸ごと入れ替えるメソッド。
         *
         * @param listData 新しいリストデータ。
         */
        public void changeColorList(List<Colors> listData) {
            _listData = listData;
            notifyDataSetChanged();
        }
    }

    /**
     * リストをタップした時の処理が記述されたメンバクラス。
     */
    private class ListItemClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
//            int idNo = (int) view.getTag();
//            Intent intent = new Intent(getApplicationContext(), ReportDetailActivity.class);
//            intent.putExtra("idNo", idNo);
//            startActivity(intent);
        }
    }

    /*
     * 編集ボタンをタップした時の処理が記述されたメンバクラス。
     */
    private class OnEditButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view){
            Button btRowEdit = (Button) view;
            int idNo = (Integer) btRowEdit.getTag();
            Intent intent = new Intent(getApplicationContext(), ColorEditActivity.class);
            intent.putExtra("mode", Consts.MODE_EDIT);
            intent.putExtra("idNo", idNo);
            startActivity(intent);
        }
    }
}