package jp.co.takao007.mymemo;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import java.text.SimpleDateFormat;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

//Listviewの中の一つのセルを生成するクラス（アダプター）
public class MemoAdapter extends RealmBaseAdapter<Memo>{
    //ViewHolderクラス
    private static class ViewHolder {
        TextView date;
        TextView title;
        TextView hogo;
        CheckBox delCheck;    //削除チェック(デフォルトで非表示）
    }


    public MemoAdapter(@Nullable OrderedRealmCollection<Memo> data) {
        super(data);
    }

    // getView  Listviewのセルが必要な時に呼び出されて、表示するビューを戻り値として返す
    // position             : リストビューのセルの位置
    // convertView          : 既に作成済みのセルを表すビュー。NULLの時もあり。
    // parent              : 親のリストビュー
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {           //convertViewがNULLの時
//          convertView = LayoutInflater.from(parent.getContext())  //インスタンスを作成する
//                  .inflate(android.R.layout.simple_list_item_2, parent,false);
            convertView = LayoutInflater.from(parent.getContext())  //インスタンスを作成する
                    .inflate(R.layout.memo_view_list, parent,false);
            viewHolder = new ViewHolder();
            //text1、text2は生成されたビューの中のテキストビュー
            viewHolder.date =  (TextView) convertView.findViewById(R.id.text1);
            viewHolder.title = (TextView) convertView.findViewById(R.id.text2);
            viewHolder.hogo = (TextView) convertView.findViewById(R.id.text3);
            viewHolder.delCheck = (CheckBox)convertView.findViewById(R.id.delCheck);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        Memo memo = adapterData.get(position);  //positionはviewの何番目（realm内の何番目）を保持
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        String formatDate = sdf.format(memo.getDate());
        viewHolder.date.setText(formatDate);
        viewHolder.title.setText(memo.getTitle());
        viewHolder.delCheck.setChecked(false);
        if (memo.getHogo() == true){
                viewHolder.hogo.setText("保護");
        }else{
                viewHolder.hogo.setText("");
        }

        return convertView;
    }
}
