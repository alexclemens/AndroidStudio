package us.ait.android.highlowgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        Button btnOk = (Button) findViewById(R.id.btnOk);
        btnOk.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                //finish();

                //Now takes us to the main menu
                Intent intentShowMenu = new Intent();
                intentShowMenu.setClass(ResultActivity.this, MainActivity.class);
                //clear top prevents pressing back to go to win screen
                intentShowMenu.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT);
                startActivity(intentShowMenu);
            }
        });
    }
    @Override
    public void onBackPressed() {
        //we don't use the super so the screen freezes unless you press Ok
        //super.onBackPressed();
        Toast.makeText(ResultActivity.this, "Press Ok to exit", Toast.LENGTH_SHORT).show();
    }

}
