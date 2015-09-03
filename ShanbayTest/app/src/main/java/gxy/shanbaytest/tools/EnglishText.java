package gxy.shanbaytest.tools;


import android.widget.ImageView;

import java.io.Serializable;
import java.util.ArrayList;

import gxy.shanbaytest.R;


/**
 * Created by gxy on 2015/9/3.
 */
public class EnglishText implements Serializable {
    private int picture;
    private String cost;
    private String info;
    private String school;

    public int getPicture() {
        return picture;
    }

    public void setPicture(int picture) {
        this.picture = picture;
    }

    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getSchool() {
        return school;
    }
    public void setSchool(String school) {
        this.school = school;
    }

    public void getData(EnglishText englishText,ArrayList<EnglishText> items){
        for (int i = 0;i<10;i++){
            englishText.setCost("10");
            englishText.setInfo("info");
            englishText.setSchool("nuist");
            englishText.setPicture(R.drawable.abc_btn_check_to_on_mtrl_000);
            items.add(englishText);
        }
    }
}
