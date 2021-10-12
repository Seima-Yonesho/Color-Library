package local.hal.st42.android.sankogodo;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import local.hal.st42.android.sankogodo.dataaccess.Colors;
import local.hal.st42.android.sankogodo.viewmodel.ColorEditViewModel;

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
    /**
     * カラー情報編集モデルオブジェクト。
     */
    private ColorEditViewModel _colorEditViewModel;
    /*
     * 更新モードの際、現在表示しているレポートの登録日時。
     */
    private Timestamp _insertDate = new Timestamp(System.currentTimeMillis());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_edit);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider provider = new ViewModelProvider(ColorEditActivity.this, factory);
        _colorEditViewModel = provider.get(ColorEditViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.parseColor("#000000"));
        toolbar.setBackgroundColor(Color.parseColor("#FFFFFF"));

        //バックボタン表示
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        _mode  = intent.getIntExtra("mode", Consts.MODE_INSERT);

        if(_mode == Consts.MODE_INSERT) {
            toolbar.setTitle(R.string.toolbar_insert);
        }
        else {
            _idNo = intent.getIntExtra("idNo", 0);
            Colors colors = _colorEditViewModel.getColor(_idNo);
            EditText etCode = findViewById(R.id.etCode);
            etCode.setText(colors.colorcode);
            EditText etRed = findViewById(R.id.etRed);
            etRed.setText(colors.redincolor);
            EditText etGreen = findViewById(R.id.etGreen);
            etGreen.setText(colors.greenincolor);
            EditText etBlue = findViewById(R.id.etBlue);
            etBlue.setText(colors.blueincolor);
            EditText etName = findViewById(R.id.etName);
            etName.setText(colors.colorname);
            View viewColor = findViewById(R.id.viewColor);
            viewColor.setBackgroundColor(Color.parseColor(String.valueOf(colors.colorcode)));
            _insertDate = colors.insertAt;
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if(_mode == Consts.MODE_INSERT) {
            inflater.inflate(R.menu.menu_options_report_add, menu);
        }
        else {
            inflater.inflate(R.menu.menu_options_report_edit, menu);
        }
        return true;
    }

    /**
     * オプションメニュー
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case R.id.menuEdit: // 登録・編集ボタン
                EditText etCode = findViewById(R.id.etCode);
                String strCode = etCode.getText().toString();
                EditText etRed = findViewById(R.id.etRed);
                String strRed = etRed.getText().toString();
                EditText etGreen = findViewById(R.id.etGreen);
                String strGreen = etGreen.getText().toString();
                EditText etBlue = findViewById(R.id.etBlue);
                String strBlue = etBlue.getText().toString();
                boolean codeIsNumeric =  strCode.matches("[+-]?\\d*(\\.\\d+)?");
                boolean redIsNumeric =  strRed.matches("[+-]?\\d*(\\.\\d+)?");
                boolean greenIsNumeric =  strGreen.matches("[+-]?\\d*(\\.\\d+)?");
                boolean blueIsNumeric =  strBlue.matches("[+-]?\\d*(\\.\\d+)?");
                if(strCode.equals("")) {
                    Toast.makeText(ColorEditActivity.this, R.string.err_insert_code, Toast.LENGTH_SHORT).show();
                }
                else if(!codeIsNumeric || !redIsNumeric || !greenIsNumeric || !blueIsNumeric){
                    Toast.makeText(ColorEditActivity.this, R.string.err_insert_code, Toast.LENGTH_SHORT).show();
                }
                else {
                    EditText etName = findViewById(R.id.etName);
                    String strName = etName.getText().toString();
                    long result = 0;
                    Colors colors = new Colors();
                    colors.colorcode = Integer.parseInt(strCode);
                    colors.colorname = strName;
                    colors.redincolor = Integer.parseInt(strRed);
                    colors.greenincolor = Integer.parseInt(strGreen);
                    colors.blueincolor = Integer.parseInt(strBlue);
                    colors.updatedAt = new Timestamp(System.currentTimeMillis());
                    if(_mode == Consts.MODE_INSERT) {
                        colors.insertAt = new Timestamp(System.currentTimeMillis());
                        result = _colorEditViewModel.insert(colors);
                    }
                    else {
                        colors.insertAt = _insertDate;
                            colors.id = _idNo;
                            result = _colorEditViewModel.update(colors);
                    }
                    if(result <= 0) {
                        Toast.makeText(ColorEditActivity.this, R.string.err_save, Toast.LENGTH_SHORT).show();
                    }
                    else {
//                        finish();
                    }
                }
                return true;
            case android.R.id.home:
                finish();
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
