package gxy.shanbaytest.tools;



import android.util.Log;

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
        for (int i = 1;i<11;i++){
            englishText.setCost(" The 4th Capter");
            englishText.setInfo(" New Concept English");
            englishText.setSchool(" Nuist");
            englishText.setPicture(R.mipmap.new_english);
            items.add(englishText);
        }
    }
}
