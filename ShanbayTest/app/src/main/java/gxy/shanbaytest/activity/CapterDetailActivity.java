package gxy.shanbaytest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import gxy.shanbaytest.R;
import gxy.shanbaytest.tools.Capter;
import gxy.shanbaytest.tools.WordRankTool;

/**
 * Created by gxy on 2015/9/3.
 */
public class CapterDetailActivity extends Activity {

    private TextView tvCapter;
    private TextView tvCapterDetail;
    private Spinner spType;
    private String[] types = new String[]{"notRank","zero Rank", "first Rank", "second Rank", "third Rank","forth Rank"};
    private String html;
    private SpannableString spannableString;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capter_detail);
        init();
        intent = getIntent();
        switchCapter(intent.getStringExtra("capter"));
        initSpinner();


    }

    private void init() {
        tvCapter = (TextView) findViewById(R.id.tv_capter);
        tvCapterDetail = (TextView) findViewById(R.id.tv_capter_detail);
        spType = (Spinner) findViewById(R.id.sp_type);
    }

    private void initSpinner() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(CapterDetailActivity.this, R.layout.spiner_item, R.id.tv, types);
        spType.setAdapter(adapter);
        spType.setSelection(0, true);

        spType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                WordRankTool wordRankTool = new WordRankTool(tvCapterDetail, intent.getStringExtra("capter"), position);
                wordRankTool.wordRank();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void switchCapter(String params) {
        switch (params) {
            case "0":
                html = Capter.chooseCapter(1);
                spannableString = new SpannableString(Html.fromHtml(html));
                tvCapter.setText("Why are legends handed down by storytellers useful?");
                tvCapterDetail.setText(spannableString);
                break;
            case "1":
                html = Capter.chooseCapter(2);
                spannableString = new SpannableString(Html.fromHtml(html));
                tvCapter.setText("How much of each year do spiders spend killing insects?");
                tvCapterDetail.setText(spannableString);
                break;
            default:
                tvCapter.setText("Leson else");
                break;
        }
    }
}
