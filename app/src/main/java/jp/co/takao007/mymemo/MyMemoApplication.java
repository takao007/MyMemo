package jp.co.takao007.mymemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyMemoApplication extends Application {
    @Override
    public void onCreate() {        //アクティビティよりも先に呼ばれる
        super.onCreate();           //superを呼ぶのは決まり事
        Realm.init(this);       //Realmの初期化
        RealmConfiguration realmConfig
                = new RealmConfiguration.Builder().build();     //RealmConfigrationクラスのインスタンスを取得
        Realm.setDefaultConfiguration(realmConfig);         //Realmの初期設定値を保存する
    }

}
