package gxy.shanbaytest.tools;

import android.graphics.Color;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import gxy.shanbaytest.R;

/**
 * Created by Administrator on 2015/9/4.
 */
public class PatternTool {

    private SpannableString spannableString;
    private String number;
    private String html;

    public PatternTool(String number) {
        this.number = number;
        chooseCapter(number);
    }

    public void chooseCapter(String number) {
        switch (number) {
            case "0":
                html = Capter.chooseCapter(1);
                spannableString = new SpannableString(Html.fromHtml(html));
                break;
            case "1":
                html = Capter.chooseCapter(2);
                spannableString = new SpannableString(Html.fromHtml(html));
                break;
            default:
                html = "else";
                spannableString = new SpannableString(html);
                break;
        }
    }

    public  SpannableString match(String str) {
        Log.e("PatternTool",str);
        Pattern pattern = java.util.regex.Pattern.compile(str);
        Matcher m = pattern.matcher(spannableString);
        while (m.find()) {
            int start = m.start();
            int end = m.end();
            spannableString.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return spannableString;
    }

    public  SpannableString remove( TextView tvCapterDetail) {
        spannableString = new SpannableString(tvCapterDetail.getText());
        ForegroundColorSpan[] spans = spannableString.getSpans(0, tvCapterDetail.getText().length(), ForegroundColorSpan.class);
        for (int i = 0; i < spans.length; i++) {
            spannableString.removeSpan(spans[i]);
        }
        return spannableString;
    }

}
