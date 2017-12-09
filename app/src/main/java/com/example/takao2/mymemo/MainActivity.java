package com.example.takao2.mymemo;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


public class MainActivity extends AppCompatActivity {       //Activity派生クラス継承（画面制御に関わる基本的な機能を提供）
    private Realm mRealm;
    private ListView mListView;     //Listview型の変数
    private CheckBox mCheckBox;

    SharedPreferences pref;  //共有プリファレンス
    SharedPreferences.Editor editor;  //共有プリファレンスエディター
    //共有プリファレンスから取得したメニュータイトル１～５
    private String w_MenuTitle1;
    private String w_MenuTitle2;
    private String w_MenuTitle3;
    private String w_MenuTitle4;
    private String w_MenuTitle5;
    private String w_KeyWord1;
    private String w_KeyWord2;
    private String w_KeyWord3;
    private String w_KeyWord4;
    private String w_KeyWord5;
    private Intent i;

    private Menu menu_g;

    @Override
    protected void onCreate(Bundle savedInstanceState) {        //onCreateメソッドはエントリーポイント
        Log.d("LIFE","onCreate");

        super.onCreate(savedInstanceState);
        //ＤＢの初期化（必要時有効にする）
        //RealmConfiguration realmConfig = new RealmConfiguration.Builder().build();
        //Realm.deleteRealm(realmConfig);


        //画面に表示すべきビューを設定
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        //フローティングアクションボタンが押されたら追加画面が出るようにListnerをセット
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        MemoEditActivity.class));
            }
        });

        //検索ボタンが押されたら検索画面が出るようにListnerをセット
        Button sB = (Button) findViewById(R.id.searchButton);
        sB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this,
                        KensakuActivity.class));
            }
        });


        //検索画面から検索値を受け取る
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String naiyo = intent.getStringExtra("naiyo");
        boolean hogo = intent.getBooleanExtra("hogo",false);


        mRealm = Realm.getDefaultInstance();    //Realmクラスのインスタンスを取得する
        mListView = (ListView) findViewById(R.id.listView); //ListViewのインスタンスを取得する
        mCheckBox= (CheckBox)findViewById(R.id.delCheck);

        //RealmResults<Memo> memos =  mRealm.where(Memo.class).findAll();
        RealmResults<Memo> memos =
                mRealm.where(Memo.class).contains("title",title).findAll()
                        .where().contains("detail",naiyo).findAll()
                        .where().beginGroup()
                            .equalTo("hogo",true)
                            .or()
                            .equalTo("hogo",hogo)
                        .endGroup()
                        .findAll().sort("id",Sort.DESCENDING ) ;
        //降順に並べ替え
        //memos = memos.sort("date", Sort.DESCENDING);

        MemoAdapter adapter = new MemoAdapter(memos);   //MemoAdapterクラスのインスタンスを生成する
        mListView.setAdapter(adapter);                  //adapterをlistviewにセット


    	//リストＶＩＥＷの項目がタップされた時の処理
        mListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                        //指定された位置（position）にあるmemoデータのインスタンスを取得する
                        Memo memo = (Memo) parent.getItemAtPosition(position);
                    	//MemoインスタンスからｇｅｔしたＩＤをMemoEditActivityに渡す
                        startActivity(new Intent(MainActivity.this,
                                MemoEditActivity.class).putExtra("memo_id", memo.getId()));
                    }
                });


    }

    /*@Override       //Menu初期表示
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d("LIFE","onCreateOptionsMenu");

        //共有プリファレンスを読み込む
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();

        w_MenuTitle1 = (pref.getString("kentitle1","検索１"));
        w_MenuTitle2 = (pref.getString("kentitle2","検索２"));
        w_MenuTitle3 = (pref.getString("kentitle3","検索３"));
        w_MenuTitle4 = (pref.getString("kentitle4","検索４"));
        w_MenuTitle5 = (pref.getString("kentitle5","検索５"));
        w_KeyWord1 = (pref.getString("kenword1","天気"));
        w_KeyWord2 = (pref.getString("kenword2","気温"));
        w_KeyWord3 = (pref.getString("kenword3",""));
        w_KeyWord4 = (pref.getString("kenword4",""));
        w_KeyWord5 = (pref.getString("kenword5",""));


        //1段目にメニュー項目を追加
        menu.add(0, 0, 0, "メモ追加").setIcon(R.drawable.menu1);
        //サブメニューを追加
        SubMenu sm1 = menu.addSubMenu(0, 1, 1, "検索")
                .setIcon(R.drawable.menu2);
        sm1.add(0, 2, 0, "メモ検索").setIcon(R.drawable.menu3);
        sm1.add(0, 3, 1, w_MenuTitle1).setIcon(R.drawable.menu6);
        sm1.add(0, 4, 2, w_MenuTitle2).setIcon(R.drawable.menu6);
        sm1.add(0, 5, 3, w_MenuTitle3).setIcon(R.drawable.menu6);
        sm1.add(0, 6, 4, w_MenuTitle4).setIcon(R.drawable.menu6);
        sm1.add(0, 7, 5, w_MenuTitle5).setIcon(R.drawable.menu6);
        //サブメニューを追加
        SubMenu sm2 = menu.addSubMenu(0, 99, 2, "設定")
                .setIcon(R.drawable.menu7);
        sm2.add(0, 8, 0, "検索ワード設定").setIcon(R.drawable.menu8);
        //sm2.add(0, 9, 1, "バージョン").setIcon(R.drawable.menu6);
        menu.add(0, 10, 2, "ヘルプ").setIcon(R.drawable.menu2);

        menu_g = menu;
        return true;
    }


    @Override       //Menu選択時
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId() ){
            case 0:     //メニュー追加
                startActivity(new Intent(MainActivity.this,MemoEditActivity.class));
                break;
            case 2:    //検索メニュークリック時
                startActivity(new Intent(MainActivity.this,KensakuActivity.class));
                break;
            case 8:    //検索ワード設定クリック時
                startActivity(new Intent(MainActivity.this,KensakuSettingActivity.class));
                break;
            case 3 :    //検索１クリック時
                i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY,w_KeyWord1);
                startActivity(i);
                break;
            case 4 :    //検索２クリック時
                i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY,w_KeyWord2);
                startActivity(i);
                break;
            case 5 :    //検索３クリック時
                i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY,w_KeyWord3);
                startActivity(i);
                break;
            case 6 :    //検索４クリック時
                i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY,w_KeyWord4);
                startActivity(i);
                break;
            case 7 :    //検索５クリック時
                i = new Intent(Intent.ACTION_WEB_SEARCH);
                i.putExtra(SearchManager.QUERY,w_KeyWord5);
                startActivity(i);
                break;
            case 9 :    //バージョン
                startActivity(new Intent(MainActivity.this,KensakuSettingActivity.class));
                break;
            case 10 :    //ヘルプクリック時
                startActivity(new Intent(MainActivity.this,DisplayHelpActivity.class));
                break;
        }
        return true;
    }


    @Override
    protected void onResume() {
        Log.d("LIFE","onResume");
        super.onResume();


        //検索画面から検索値を受け取る（OncreateとOnresumeで同じロジックを書いている）
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String naiyo = intent.getStringExtra("naiyo");
        boolean hogo = intent.getBooleanExtra("hogo",false);


        mRealm = Realm.getDefaultInstance();    //Realmクラスのインスタンスを取得する
        mListView = (ListView) findViewById(R.id.listView); //ListViewのインスタンスを取得する
        mCheckBox= (CheckBox)findViewById(R.id.delCheck);

        //RealmResults<Memo> memos =  mRealm.where(Memo.class).findAll();
        RealmResults<Memo> memos =
                mRealm.where(Memo.class).contains("title",title).findAll()
                        .where().contains("detail",naiyo).findAll()
                        .where().beginGroup()
                        .equalTo("hogo",true)
                        .or()
                        .equalTo("hogo",hogo)
                        .endGroup()
                        .findAll().sort("id",Sort.DESCENDING ) ;



        MemoAdapter adapter = new MemoAdapter(memos);   //MemoAdapterクラスのインスタンスを生成する
        mListView.setAdapter(adapter);                  //adapterをlistviewにセット


     }


    @Override
    protected void onStart() {
        Log.d("LIFE","onStart");
        super.onStart();
    }


    @Override
    protected void onPause() {
        Log.d("LIFE","onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Log.d("LIFE","onStop");
        super.onStop();
    }

    @Override
    protected void onRestart() {
        Log.d("LIFE","onRestart");
        super.onRestart();
    }

    @Override
    public void onDestroy() {
        Log.d("LIFE","onDestroy");
        super.onDestroy();
        mRealm.close();         //Realmのインスタンスを破棄する
    }

}
