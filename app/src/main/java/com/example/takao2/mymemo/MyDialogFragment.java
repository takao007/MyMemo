package com.example.takao2.mymemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import io.realm.Realm;

public class MyDialogFragment extends DialogFragment {
    TextView textView_hidden;
    private Realm mRealm;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {       //戻り値はｄｉａｌｏｇ
        mRealm = Realm.getDefaultInstance();    //Realmのインスタンスを取得する
        //ダイアログを生成
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Bundleオブジェクトから受け取る
        final Long memoId = getArguments().getLong("memoId");
        final boolean hogo = getArguments().getBoolean("hogo");
        //ダイアログの設定（保護がチェックされている場合）
        if (hogo) {
            return builder.setTitle("保護されています")
                    .setMessage("保護されているので削除できません")
                    .setIcon(R.drawable.hogo)
                    .setNegativeButton("確認",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                              //      Toast.makeText(getActivity(), "キャンセルしました", Toast.LENGTH_SHORT).show();
                                }
                            }
                    )

                    .create();
        }
        //ダイアログの設定
        return builder.setTitle("削除ボタン")
                .setMessage("削除しますか？")
                .setIcon(R.drawable.del)
                .setPositiveButton("はい",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {

                                mRealm.executeTransaction(new Realm.Transaction() {
                                    @Override
                                    public void execute(Realm realm) {
                                        Memo memo = realm.where(Memo.class)
                                                .equalTo("id", memoId).findFirst();
                                        memo.deleteFromRealm();
                                    }
                                });

                                Toast.makeText(getActivity(), "削除しました", Toast.LENGTH_SHORT).show();
                            }
                        }
                )
                .setNegativeButton("いいえ",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(getActivity(), "キャンセルしました", Toast.LENGTH_SHORT)
                                        .show();
                            }
                        }
                )
//                .setNeutralButton("キャンセルしました",
//                        new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) { }
//                        }
//                )
                .create();          //ｄｉａｌｏｇオブジェクトを返す（このメソッドの戻り値）
    }
}
