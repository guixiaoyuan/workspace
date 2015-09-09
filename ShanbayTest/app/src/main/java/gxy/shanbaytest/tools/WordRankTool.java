package gxy.shanbaytest.tools;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * Created by Administrator on 2015/9/7.
 */
public class WordRankTool {
    private TextView tvCapterDetail;
    private int posion;
    public String number;
    private String[] zero = new String[]{" the ", " preservation ", " porpoise ", " scavenger ", " thermodynamics ", " chicanery ", " fatuous "};
    private String[] first = new String[]{" us ", " gifted ", " silt ", " spatial ", " turbulent ", " apparatus ", " beaver ", " spawn "};
    private String[] second = new String[]{" can "," beast ", " infant ", " mushroom ", " proposition ", " scratch ", " tariff ", " tribe "};
    private String[] third = new String[]{" of "," encounter ", " alcohol ", " abandon ", " acre ", " analysis ", " bat ", " capacity "};
    private String[] forth = new String[]{" and "," commission ", " democratic ", " dispute ", " finance ", " impose ", " increasingly "};

    public WordRankTool(TextView tvCapterDetail, String number, int position) {
        this.tvCapterDetail = tvCapterDetail;
        this.posion = position;
        this.number = number;
    }


    public void wordRank() {
        PatternTool patternTool = new PatternTool(number);
        switch (posion) {
            case 0:
                tvCapterDetail.setText(patternTool.remove(tvCapterDetail));
                break;
            case 1:
                tvCapterDetail.setText(patternTool.remove(tvCapterDetail));
                for (int i = 0; i < zero.length; i++) {
                    tvCapterDetail.setText(patternTool.match(zero[i]));
                }
                break;
            case 2:
                tvCapterDetail.setText(patternTool.remove(tvCapterDetail));
                for (int i = 0; i < zero.length; i++) {
                    tvCapterDetail.setText(patternTool.match(zero[i]));
                }
                for (int i = 0; i < first.length; i++) {
                    tvCapterDetail.setText(patternTool.match(first[i]));
                }
                break;
            case 3:
                tvCapterDetail.setText(patternTool.remove(tvCapterDetail));
                for (int i = 0; i < zero.length; i++) {
                    tvCapterDetail.setText(patternTool.match(zero[i]));
                }
                for (int i = 0; i < first.length; i++) {
                    tvCapterDetail.setText(patternTool.match(first[i]));
                }
                for (int i = 0; i < second.length; i++) {
                    tvCapterDetail.setText(patternTool.match(second[i]));
                }
                break;
            case 4:
                tvCapterDetail.setText(patternTool.remove(tvCapterDetail));
                for (int i = 0; i < zero.length; i++) {
                    tvCapterDetail.setText(patternTool.match(zero[i]));
                }
                for (int i = 0; i < first.length; i++) {
                    tvCapterDetail.setText(patternTool.match(first[i]));
                }
                for (int i = 0; i < second.length; i++) {
                    tvCapterDetail.setText(patternTool.match(second[i]));
                }
                for (int i = 0; i < third.length; i++) {
                    tvCapterDetail.setText(patternTool.match(third[i]));
                }
                break;
            case 5:
                tvCapterDetail.setText(patternTool.remove(tvCapterDetail));
                for (int i = 0; i < zero.length; i++) {
                    tvCapterDetail.setText(patternTool.match(zero[i]));
                }
                for (int i = 0; i < first.length; i++) {
                    tvCapterDetail.setText(patternTool.match(first[i]));
                }
                for (int i = 0; i < second.length; i++) {
                    tvCapterDetail.setText(patternTool.match(second[i]));
                }
                for (int i = 0; i < third.length; i++) {
                    tvCapterDetail.setText(patternTool.match(third[i]));
                }
                for (int i = 0; i < forth.length; i++) {
                    tvCapterDetail.setText(patternTool.match(forth[i]));
                }
                break;
        }

    }
}
