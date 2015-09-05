package gxy.shanbaytest.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.widget.TextView;

import gxy.shanbaytest.R;
import gxy.shanbaytest.tools.PatternTool;

/**
 * Created by gxy on 2015/9/3.
 */
public class CapterDetailActivity extends Activity {

    private TextView tvCapter;
    private TextView tvCapterDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.capter_detail);
        init();
        Intent intent = getIntent();
        switchCapter(intent.getStringExtra("capter"));


    }

    private void init() {
        tvCapter = (TextView) findViewById(R.id.tv_capter);
        tvCapterDetail = (TextView) findViewById(R.id.tv_capter_detail);
    }

    private void switchCapter(String params) {
        switch (params) {
            case "0":
                String html = "<html> \n" +
                        "<head> \n" +
                        "</head> \n" +
                        "<body>  \n" +
                        "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;We can read of things that happened 5,000 years ago in the Near East, where people first learned to write. But there are some parts of the word where even now people cannot write. The only way that they can preserve their history is to recount it as sagas -- legends handed down from one generation of another. These legends are useful because they can tell us something about migrations of people who lived long ago, but none could write down what they did. Anthropologists wondered where the remote ancestors of the Polynesian peoples now living in the Pacific Islands came from. The sagas of these people explain that some of them came from Indonesia about 2,000 years ago.</p>\n" +
                        "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp;But the first people who were like ourselves lived so long ago that even their sagas, if they had any, are forgotten. So archaeologists have neither history nor legends to help them to find out where the first 'modern men' came from.</p> \n" +
                        "<p align=\"left\">&nbsp;&nbsp;&nbsp;&nbsp; Fortunately, however, ancient men made tools of stone, especially flint, because this is easier to shape than other kinds. They may also have used wood and skins, but these have rotted away. Stone does not decay, and so the tools of long ago have remained when even the bones of the men who made them have disappeared without trace.</p> \n" +
                        "</body> \n" +
                        "</html> ";
                SpannableString spannableString = new SpannableString(Html.fromHtml(html));
                tvCapter.setText("Why are legends handed down by storytellers useful?");
                tvCapterDetail.setText( PatternTool.match(spannableString, " beast "));
                tvCapterDetail.setText( PatternTool.match(spannableString, " encounter "));
                break;
            default:
                tvCapter.setText("Leson else");
                break;
        }
    }
}
