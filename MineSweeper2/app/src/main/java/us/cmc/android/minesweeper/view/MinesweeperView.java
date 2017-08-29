package us.cmc.android.minesweeper.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import us.cmc.android.minesweeper.MainActivity;
import us.cmc.android.minesweeper.R;
import us.cmc.android.minesweeper.model.MinesweeperModel;


/**
 * Created by alexclemens on 9/29/16.
 */
public class MinesweeperView extends View{
    private Paint paintNum;
    private Paint paintLine;
    private Paint paintLineB;
    private Paint paintFlag;
    int counter = 0;
    int flagCounter = 0;

    public MinesweeperView(Context context, AttributeSet attrs) {
        super(context, attrs);
//        paintBg = new Paint();
//        paintBg.setColor(Color.BLACK);
//        paintBg.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.BLACK);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintNum = new Paint();
        paintNum.setTextSize(100);

        paintLineB = new Paint();
        paintLineB.setColor(Color.RED);
        paintLineB.setStyle(Paint.Style.STROKE);
        paintLineB.setStrokeWidth(5);

        paintFlag = new Paint();
        paintFlag.setColor(Color.BLUE);
        paintFlag.setTextSize(35);


    }

    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        drawGameField(canvas);

        drawValues(canvas);


    }

    public void drawGameField(Canvas canvas){
        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // 4 horizontal lines
        canvas.drawLine(0, getHeight() / 5, getWidth(), getHeight() / 5,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 5, getWidth(),
                2 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 3 * getHeight() / 5, getWidth(),
                3 * getHeight() / 5, paintLine);
        canvas.drawLine(0, 4 * getHeight() / 5, getWidth(),
                4 * getHeight() / 5, paintLine);

        // 4 vertical lines
        canvas.drawLine(getWidth() / 5, 0, getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 5, 0, 2 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(3 * getWidth() / 5, 0, 3 * getWidth() / 5, getHeight(),
                paintLine);
        canvas.drawLine(4 * getWidth() / 5, 0, 4 * getWidth() / 5, getHeight(),
                paintLine);
    }

    public void drawValues(Canvas canvas){
        for(int i = 0; i<5; i++){
            for(int j = 0; j<5; j++){
                //Log.d("Draw", i+", "+j);
                if(MinesweeperModel.getInstance().getFieldContent(i,j)!=-1) {
                    float centerX = i * getWidth() / 5 + getWidth() / 10;
                    float centerXFlag = (i*getWidth()/5+getWidth()/10)-40;
                    float centerY = j * getHeight() / 5 + getHeight() / 10;
                    float centerYFlag = (j * getHeight() / 5 + getHeight() / 10)+10;
                    int radius = getHeight() / 10 - 20;

                    int temp = MinesweeperModel.getInstance().getFieldContent(i, j);
                    if (temp != 9 && temp!=-2) {
                        canvas.drawText(temp + " ", centerX, centerY, paintNum);
                    } else if(MinesweeperModel.getInstance().getFieldContent(i,j)==-2) {
                        canvas.drawText(getContext().getString(R.string.draw_flag), centerXFlag, centerYFlag, paintFlag);
                    }
                    else {
                        canvas.drawCircle(centerX, centerY, radius, paintLineB);
                    }
                }

            }
        }
    }


    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            //Log.d("TAG_DRAW", "x: " + event.getX() + " # y: " + event.getY());

            int tX = ((int) event.getX()) / (getWidth() / 5);
            int tY = ((int) event.getY()) / (getHeight() / 5);


            if(MinesweeperModel.getInstance().getFieldContent(tX,tY)==-1) {
                MinesweeperModel.getInstance().setFieldContent(tX,
                        tY);


                if (MinesweeperModel.getInstance().
                        getFieldContent(tX, tY) == 9 && !((MainActivity) getContext()).isToggle()) {

                    invalidate();
                    showLoseMessage();
                    loseGame();

                } else if (MinesweeperModel.getInstance().getFieldContent(tX, tY) != 9 && ((MainActivity) getContext()).isToggle()) {
                    invalidate();
                    showLoseMessage();
                    loseGame();
                } else {
                    counter++;
                    if (((MainActivity) getContext()).isToggle()) {
                        MinesweeperModel.getInstance().setBombContent(tX, tY);
                        flagCounter++;
                        //Log.d("counter ", flagCounter+" is");

                    }
                    if(counter == 25 || flagCounter == 3) {
                        checkWinner();
                    }

                    invalidate();
                    //Log.d("counter",counter+" test");
                }
            }
        }

        return true;
    }


    public void checkWinner(){
        for (int i = 0; i<5; i++){
            for (int j = 0; j<5; j++){
                if(MinesweeperModel.getInstance().getFieldContent(i,j)!=-1){
                    showWinMessage();
                }
            }
        }
    }

    public void loseGame(){
        for (int i = 0; i<5; i++){
            for (int j = 0; j<5; j++){
                MinesweeperModel.getInstance().setFieldContent(i, j);
                invalidate();
            }
        }
    }
    public void restartGame() {
        counter=0;
        flagCounter = 0;
        MinesweeperModel.getInstance().resetModel();
        invalidate();
    }


    private void showWinMessage() {
        ((MainActivity)getContext()).showSimpleSnackbarMessage(

                getContext().getString(R.string.text_win)
        );
        for (int i = 0; i<5; i++){
            for (int j = 0; j<5; j++){
                MinesweeperModel.getInstance().setFieldContent(i, j);
                invalidate();
            }
        }
    }
    private void showLoseMessage() {
        ((MainActivity)getContext()).showSimpleSnackbarMessage(
                getContext().getString(R.string.text_lose)
        );
    }
}
