package us.cmc.android.minesweeper;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import us.cmc.android.minesweeper.view.MinesweeperView;

public class MainActivity extends AppCompatActivity {

    private MinesweeperView gameView;
    private LinearLayout layoutContent;
    private ToggleButton btntoggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameView =
                (MinesweeperView) findViewById(R.id.gameView);
        layoutContent = (LinearLayout) findViewById(R.id.layoutContent);

        btntoggle = (ToggleButton) findViewById(R.id.btnFlag);

        Button btnReset = (Button) findViewById(R.id.btnReset);
        btnReset.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                gameView.restartGame();
            }
        });
        Button btnHelp = (Button) findViewById(R.id.btnHelp);
        btnHelp.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                showHelp();
            }
        });



    }
    public boolean isToggle(){
        return btntoggle.isChecked();
    }

    public void showSimpleSnackbarMessage(String message) {
        Snackbar.make(layoutContent, message, Snackbar.LENGTH_LONG).show();
    }
    private void showHelp(){
        showSimpleSnackbarMessage("To start the game, press anywhere on the game board \n" +
                "To use flags, press the \"on/off\" toggle button");
    }

}
