package gxy.shanbaytest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import java.util.ArrayList;

import gxy.shanbaytest.R;
import gxy.shanbaytest.View.StaggeredGridView;
import gxy.shanbaytest.adapter.SquareGridAdapter;
import gxy.shanbaytest.tools.EnglishText;


public class MainActivity extends Activity {

    private SquareGridAdapter adapter;
    private ArrayList<EnglishText> items = new ArrayList<>();
    private EnglishText englishText = new EnglishText();
    private StaggeredGridView sgSquare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        englishText.getData(englishText, items);
        init();

    }

    private void init() {
        sgSquare = (StaggeredGridView) findViewById(R.id.sg_first_page);
        adapter = new SquareGridAdapter(this, items);
        sgSquare.setAdapter(adapter);
        sgSquare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this,CapterDetailActivity.class);
                Log.e("onItemClick", String.valueOf(position));
                intent.putExtra("capter", position+"");
                startActivity(intent);
            }
        });
    }
}
