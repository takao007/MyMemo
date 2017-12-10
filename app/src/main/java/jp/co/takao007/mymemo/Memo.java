package jp.co.takao007.mymemo;

import java.util.Date;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;


public class Memo extends RealmObject {
    @PrimaryKey    private long id;
    private Date date;
    private String title;
    private String detail;
    private boolean hogo;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean getHogo() {
        return hogo;
    }

    public void setHogo(boolean hogo) {
        this.hogo = hogo;
    }
}
