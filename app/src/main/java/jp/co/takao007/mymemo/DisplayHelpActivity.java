package jp.co.takao007.mymemo;

import android.app.SearchManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;

public class DisplayHelpActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_help);
        setTitle("ＭｙＭｅｍｏヘルプ");
        invalidateOptionsMenu();
    }





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
        menu.add(0, 0, 0, "ホーム").setIcon(R.drawable.menu1);
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

        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu){
        return true;
    }

    @Override       //Menu選択時
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId() ){
            case 2:    //検索メニュークリック時
                startActivity(new Intent(DisplayHelpActivity.this,KensakuActivity.class));
                break;
            case 8:    //検索ワード設定クリック時
                startActivity(new Intent(DisplayHelpActivity.this,KensakuSettingActivity.class));
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
                startActivity(new Intent(DisplayHelpActivity.this,KensakuSettingActivity.class));
                break;
            case 10 :    //ヘルプクリック時
                startActivity(new Intent(DisplayHelpActivity.this,DisplayHelpActivity.class));
                break;
        }
        return true;
    }

}
