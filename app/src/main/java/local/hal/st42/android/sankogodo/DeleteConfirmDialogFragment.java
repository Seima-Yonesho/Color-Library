//package local.hal.st42.android.sankogodo;
//
//import android.app.Activity;
//import android.app.Dialog;
//import android.content.DialogInterface;
//import android.os.Bundle;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AlertDialog;
//import androidx.fragment.app.DialogFragment;
//
//import local.hal.st42.android.taskreports80483.viewmodel.ReportEditViewModel;
//
///**
//  * ST42 Android
//  *
//  * 削除確認ダイアログクラス。
//  *
//  */
//public class DeleteConfirmDialogFragment extends DialogFragment {
//    /**
//     * レポート情報リストビューモデルオブジェクト。
//     */
//    private ReportEditViewModel _reportEditViewModel;
//    /**
//     * コンストラクタ。
//     *
//     * @param reportEditViewModel メモ情報リストビューモデルオブジェクト。
//     */
//    public DeleteConfirmDialogFragment(ReportEditViewModel reportEditViewModel) {
//        _reportEditViewModel = reportEditViewModel;
//    }
//
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle(R.string.del_dialog_title);
//        builder.setMessage(R.string.del_dialog_message);
//        builder.setPositiveButton(R.string.del_dialog_positive, new DeleteConfirmDialogClickListener());
//        builder.setNegativeButton(R.string.del_dialog_negative, new DeleteConfirmDialogClickListener());
//        AlertDialog dialog = builder.create();
//        return dialog;
//    }
//
//    /**
//     * 削除確認ダイアログのボタンが押されたときの処理が記述されたメンバクラス。
//     */
//    private class DeleteConfirmDialogClickListener implements DialogInterface.OnClickListener {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//            if(which ==  DialogInterface.BUTTON_POSITIVE) {
//                Activity parent = getActivity();
//                Bundle extras = getArguments();
//                int idNo = extras.getInt("id", 0);
//                int result = _reportEditViewModel.delete(idNo);
//                if(result <= 0) {
//                    Toast.makeText(parent, R.string.msg_save_err, Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    parent.finish();
//                }
//            }
//        }
//    }
//}