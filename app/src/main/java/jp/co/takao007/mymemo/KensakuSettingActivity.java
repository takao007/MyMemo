package jp.co.takao007.mymemo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import static jp.co.takao007.mymemo.R.id.検索タイトル１;

public class KensakuSettingActivity extends AppCompatActivity {
    EditText m検索タイトル１;
    EditText m検索ワード１;
    EditText m検索タイトル２;
    EditText m検索ワード２;
    EditText m検索タイトル３;
    EditText m検索ワード３;
    EditText m検索タイトル４;
    EditText m検索ワード４;
    EditText m検索タイトル５;
    EditText m検索ワード５;
    SharedPreferences pref;  //共有プリファレンス
    SharedPreferences.Editor editor;  //共有プリファレンスエディター

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kensaku_setting);
        setTitle("ＷＥＢ検索キーワード設定");
        //ビューのインスタンスの取得
        m検索タイトル１ = (EditText) findViewById(検索タイトル１);
        m検索ワード１ = (EditText) findViewById(R.id.検索ワード１);
        m検索タイトル２ = (EditText) findViewById(R.id.検索タイトル２);
        m検索ワード２ = (EditText) findViewById(R.id.検索ワード２);
        m検索タイトル３ = (EditText) findViewById(R.id.検索タイトル３);
        m検索ワード３ = (EditText) findViewById(R.id.検索ワード３);
        m検索タイトル４ = (EditText) findViewById(R.id.検索タイトル４);
        m検索ワード４ = (EditText) findViewById(R.id.検索ワード４);
        m検索タイトル５ = (EditText) findViewById(R.id.検索タイトル５);
        m検索ワード５ = (EditText) findViewById(R.id.検索ワード５);

        //共有プリファレンスより読み込む
        pref = PreferenceManager .getDefaultSharedPreferences(this);
        editor = pref.edit();

        m検索タイトル１.setText(pref.getString("kentitle1","検索１"));
        m検索ワード１.setText(pref.getString("kenword1","天気"));
        m検索タイトル２.setText(pref.getString("kentitle2","検索２"));
        m検索ワード２.setText(pref.getString("kenword2","気温"));
        m検索タイトル３.setText(pref.getString("kentitle3","検索３"));
        m検索ワード３.setText(pref.getString("kenword3",""));
        m検索タイトル４.setText(pref.getString("kentitle4","検索４"));
        m検索ワード４.setText(pref.getString("kenword4",""));
        m検索タイトル５.setText(pref.getString("kentitle5","検索５"));
        m検索ワード５.setText(pref.getString("kenword5",""));
    }

    //保存ボタンタップ処理
    public void on保存ボタンTapped(View view) {
        editor.putString("kentitle1",m検索タイトル１.getText().toString() );
        editor.putString("kenword1",m検索ワード１.getText().toString() );
        editor.putString("kentitle2",m検索タイトル２.getText().toString() );
        editor.putString("kenword2",m検索ワード２.getText().toString() );
        editor.putString("kentitle3",m検索タイトル３.getText().toString() );
        editor.putString("kenword3",m検索ワード３.getText().toString() );
        editor.putString("kentitle4",m検索タイトル４.getText().toString() );
        editor.putString("kenword4",m検索ワード４.getText().toString() );
        editor.putString("kentitle5",m検索タイトル５.getText().toString() );
        editor.putString("kenword5",m検索ワード５.getText().toString() );

        editor.commit();

        /*Snackbar.make(findViewById(android.R.id.content),
                    "アップデートしました", Snackbar.LENGTH_LONG)
                    .setAction("戻る", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            finish();
                        }
                    })
                    .setActionTextColor(Color.YELLOW)   //戻るボタンを黄色にしている
                    .show();*/

        //ＭＡＩＮに戻る
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);


    }

}
