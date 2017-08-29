package us.cmc.android.butterknifedemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tvData)
    TextView tvData;
    @BindView(R.id.layoutContent)
    LinearLayout layoutContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //so the comiler knows to find the find view by id's and the onclick listeners
        ButterKnife.bind(this);

    }
    @OnClick(R.id.btnAddData)
    public void addClick(Button btn){
        tvData.setText(new Date(System.currentTimeMillis()).toString());

        layoutContent.setBackgroundColor(Color.RED);
    }
}
