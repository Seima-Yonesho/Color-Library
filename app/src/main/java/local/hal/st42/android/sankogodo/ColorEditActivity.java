package local.hal.st42.android.sankogodo;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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
        setContentView(R.layout.activity_report_edit);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        ViewModelProvider provider = new ViewModelProvider(ReportEditActivity.this, factory);
        _reportEditViewModel = provider.get(ReportEditViewModel.class);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        //スピナー
        Spinner spinner = findViewById(R.id.spWorkKinds);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Consts.CATEGORY);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Spinner spinner = (Spinner)parent;
                spinner.setTag(position);
            }
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //バックボタン表示
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        Intent intent = getIntent();
        _mode  = intent.getIntExtra("mode", Consts.MODE_INSERT);

        if(_mode == Consts.MODE_INSERT) {
            TextView tvTitle = findViewById(R.id.tvTitle);
            tvTitle.setText(R.string.tv_title_insert);
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            TextView etInputDate = findViewById(R.id.etDate);
            etInputDate.setText(sdf.format(calendar.getTime()));
            TextView etStartTime = findViewById(R.id.etStartTime);
            etStartTime.setText(defaultInputTime);
            TextView etEndTime = findViewById(R.id.etEndTime);
            etEndTime.setText(defaultInputTime);
        }
        else {
            _idNo = intent.getIntExtra("idNo", 0);
            Report report = _reportEditViewModel.getReport(_idNo);
            EditText etInputNote = findViewById(R.id.etInputNote);
            etInputNote.setText(report.workin);
            TextView etDate = findViewById(R.id.etDate);
            etDate.setText(report.workdate);
            TextView etStartTime = findViewById(R.id.etStartTime);
            etStartTime.setText(report.starttime);
            TextView etEndTime = findViewById(R.id.etEndTime);
            etEndTime.setText(report.endtime);
            Spinner spinner1 = findViewById(R.id.spWorkKinds);
            spinner1.setSelection(report.workkind);
            _insertDate = report.insertAt;
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }
}
