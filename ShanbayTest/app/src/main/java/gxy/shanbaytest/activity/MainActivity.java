package gxy.shanbaytest.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import java.util.ArrayList;

import gxy.shanbaytest.R;
import gxy.shanbaytest.View.StaggeredGridView;
import gxy.shanbaytest.adapter.SquareGridAdapter;
import gxy.shanbaytest.tools.EnglishText;


public class MainActivity  extends AppCompatActivity implements View.OnClickListener{

    private SquareGridAdapter adapter;
    private ArrayList<EnglishText> items = new ArrayList<>();
    private EnglishText englishText = new EnglishText();
    private StaggeredGridView sgSquare;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView tvBring,tvTake,tvMain,tvMessage,tvYanhuo;
    private FragmentManager fm;
    private FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        englishText.getData(englishText, items);
        init();
        setToolBar();
        getDrawerMenu();

    }

    private void init() {
        sgSquare = (StaggeredGridView) findViewById(R.id.sg_first_page);
        adapter = new SquareGridAdapter(this, items);
        sgSquare.setAdapter(adapter);
        sgSquare.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CapterDetailActivity.class);
                intent.putExtra("capter", position + "");
                startActivity(intent);
            }
        });
    }
    /**
     * 设置标题栏
     */
    private void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Shanbay");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar, R.string.app_name,
                R.string.app_name);
        mDrawerLayout.setScrimColor(getTitleColor());
        mDrawerToggle.syncState();
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }
    /**
     * 初始化侧边栏
     */
    private void getDrawerMenu(){
        tvBring = (TextView) findViewById(R.id.courierBring);
        tvBring.setOnClickListener(this);
        tvTake = (TextView) findViewById(R.id.courierTake);
        tvTake.setOnClickListener(this);
        tvMain = (TextView) findViewById(R.id.main);
        tvMain.setOnClickListener(this);
        tvYanhuo = (TextView) findViewById(R.id.yanHuo);
        tvYanhuo.setOnClickListener(this);
        tvMessage = (TextView) findViewById(R.id.tvMessage);
        tvMessage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main:
                mDrawerLayout.closeDrawers();
                toolbar.setTitle(getString(R.string.main));

                break;
            case R.id.courierBring:
                mDrawerLayout.closeDrawers();
                toolbar.setTitle(getString(R.string.bring));

                break;
            case R.id.courierTake:
                mDrawerLayout.closeDrawers();
                toolbar.setTitle(getString(R.string.take));

                break;
            case R.id.yanHuo:
                mDrawerLayout.closeDrawers();
                toolbar.setTitle(getString(R.string.yanhuo));

                break;
            case R.id.tvMessage:
                mDrawerLayout.closeDrawers();
                toolbar.setTitle(getString(R.string.message));
                break;
        }
    }
}
