package us.cmc.android.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Animation pushAnim = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.push_anim);
        final Animation sendAnim = AnimationUtils.loadAnimation(MainActivity.this,
                R.anim.send_anim);

        final Button btnSend = (Button) findViewById(R.id.btnSend);
        final TextView tvMessage = (TextView) findViewById(R.id.tvMessage);
        btnSend.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){

                btnSend.startAnimation(pushAnim);
                tvMessage.startAnimation(sendAnim);
            }
        });
    }
}
