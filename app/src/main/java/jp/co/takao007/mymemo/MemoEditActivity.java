package jp.co.takao007.mymemo;

import android.app.DialogFragment;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmResults;

public class MemoEditActivity extends AppCompatActivity {
    private Realm mRealm;
    EditText mDateEdit;
    EditText mTitleEdit;
    EditText mDetailEdit;
    CheckBox mHogoCheck;    //保護
    Button mDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_memo_edit);
        mRealm = Realm.getDefaultInstance();    //Realmのインスタンスを取得する
        mDateEdit = (EditText) findViewById(R.id.dateEdit);
        mTitleEdit = (EditText) findViewById(R.id.titleEdit);
        mDetailEdit = (EditText) findViewById(R.id.detailEdit);
        mHogoCheck = (CheckBox) findViewById(R.id.hogocheck);
        mDelete = (Button) findViewById(R.id.delete);


        //インテントに格納したmemo_idを取得
        long memoId = getIntent().getLongExtra("memo_id", -1);
        //更新の場合（memoId != -1）
        if (memoId != -1) {
            //取得したmemoIdと同じレコードを取得してresultsに格納
            RealmResults<Memo> results = mRealm.where(Memo.class).equalTo("id", memoId).findAll();
            //検索結果から最初のモデルを取得する
            Memo memo = results.first();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            String date = sdf.format(memo.getDate());
            mDateEdit.setText(date);
            mTitleEdit.setText(memo.getTitle());
            mDetailEdit.setText(memo.getDetail());
            mHogoCheck.setChecked(memo.getHogo());
            mDelete.setVisibility(View.VISIBLE);    //viewの表示
        } else {
            mDelete.setVisibility(View.INVISIBLE);  //viewの非表示

        }


        Button button = (Button) findViewById(R.id.onsei);

        // ボタンをクリックした時、音声認識を開始し
        // トーストに認識結果を表示する。
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    // インテント作成
                    Intent intent = new Intent(
                            RecognizerIntent.ACTION_RECOGNIZE_SPEECH);

                    intent.putExtra(
                            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

                    // メッセージを設定。
                    intent.putExtra(
                            RecognizerIntent.EXTRA_PROMPT,
                            "お話ください");

                    // インテント発行
                    startActivityForResult(intent, 0);
                } catch (ActivityNotFoundException e) {
                    // このインテントに応答できるアクティビティがインストールされていない場合
                    Toast.makeText(MemoEditActivity.this,
                            "音声認識は使用できません。", Toast.LENGTH_LONG).show();
                }
            }
        });


     }

    //保存ボタンタップ処理
    public void onSaveTapped(View view) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date dateParse = new Date();
        try {
            dateParse = sdf.parse(mDateEdit.getText().toString());  //Dateのインスタンスを取得
        } catch (ParseException e) {
            e.printStackTrace();
        }
        final Date date = dateParse;

        long memoId = getIntent().getLongExtra("memo_id", -1);
        //更新の場合
        if (memoId != -1) {
            final RealmResults<Memo> results = mRealm.where(Memo.class)
                    .equalTo("id", memoId).findAll();
            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Memo memo = results.first();
                    memo.setDate(date);
                    memo.setTitle(mTitleEdit.getText().toString());
                    memo.setDetail(mDetailEdit.getText().toString());
                    memo.setHogo(mHogoCheck.isChecked());
                }
            });

            Snackbar.make(findViewById(android.R.id.content),
                    "アップデートしました", Snackbar.LENGTH_LONG)
                    .setAction("戻る", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setActionTextColor(Color.YELLOW)   //戻るボタンを黄色にしている
                    .show();
        } else {


            mRealm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    Number maxId = realm.where(Memo.class).max("id");   //idの最大値取得
                    long nextId = 0;
                    if (maxId != null) nextId = maxId.longValue() + 1;  //nextIdの計算
                    //createObjectで、データを1行追加する
                    Memo memo = realm.createObject(Memo.class, new Long(nextId));
                    //各フィールドに値を設定する
                    memo.setDate(date);
                    memo.setTitle(mTitleEdit.getText().toString());
                    memo.setDetail(mDetailEdit.getText().toString());
                    memo.setHogo(mHogoCheck.isChecked());
                }
            });
            Toast.makeText(this, "追加しました", Toast.LENGTH_SHORT).show();
            finish();   //MainActivityに戻る
        }

    }

    //削除ボタンタップ時
    public void onDeleteTapped(View view) {
        //削除するmemoのIDを取得
        final long memoId = getIntent().getLongExtra("memo_id", -1);

        //Bundleに情報を渡す
        Bundle args = new Bundle();
        args.putLong("memoId",memoId);
        args.putBoolean("hogo",mHogoCheck.isChecked());

        //ダイアログ表示
        DialogFragment dialog = new MyDialogFragment();
        dialog.setArguments(args);
        dialog.show(getFragmentManager(),"delete");

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        // requestCode は startActivityForResult関数の第2引数の値
        if (requestCode == 0 && resultCode == RESULT_OK) {
            String msg = "";

            // 結果文字列リスト
            ArrayList<String> ret = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);

            for (int i = 0; i< ret.size(); i++) {
                // 音声認識の結果を詳細に表示
                msg += ret.get(i) + ", " ;
            }

            //mDetailEdit.setText(mDetailEdit.getText() + msg);
            if (mDetailEdit.getText().length() == 0 ) {
                mDetailEdit.setText(mDetailEdit.getText() + ret.get(0));
            }else {
                mDetailEdit.setText(mDetailEdit.getText() + " " +ret.get(0));
            }
            if (mTitleEdit.getText().length() == 0 ) {
                mTitleEdit .setText(ret.get(0) );
            }
            // トーストを使って結果を表示
           // Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onmailTapped(View view){
        //情報を渡す
        Intent intent = new Intent();
        if (mTitleEdit.getText().length() != 0) {
      //      intent.putExtra(Intent.EXTRA_SUBJECT,"タイトルです");
            String string = mTitleEdit.getText().toString();
            intent.putExtra(Intent.EXTRA_SUBJECT,string);
        }
        if (mDetailEdit.getText().length() != 0) {
            intent.putExtra(Intent.EXTRA_TEXT,mDetailEdit.getText().toString());
        }
        intent.setAction(Intent.ACTION_SENDTO);     //標準メールアプリ指定
        intent.setData(Uri.parse("mailto:"));
        startActivity(intent);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }


}
