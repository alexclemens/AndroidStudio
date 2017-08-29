package us.cmc.android.minesweeper.model;

import android.util.Log;

import java.util.Random;

import us.cmc.android.minesweeper.MainActivity;

/**
 * Created by alexclemens on 9/29/16.
 */
public class MinesweeperModel {
    private static MinesweeperModel instance = null;

    public static MinesweeperModel getInstance() {
        if (instance == null) {
            instance = new MinesweeperModel();

        }
        return instance;
    }
    private MinesweeperModel(){
        placeMines();
        getValues();
    }
    //used to hold
    private int [][] model = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
    };
    //used to
    private int [][] bombs = {
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
    };

    private int [][]gameBoard={
            {-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1},
            {-1,-1,-1,-1,-1}
    };





    public void placeMines() {
        //Log.d("Check", "here");
        Random random = new Random();
        int row;
        int col;
        for (int i = 0; i < 3; i++) {
            col = random.nextInt(5);
            row = random.nextInt(5);
            if (bombs[row][col]==9) {
                i--;
            }
            else {
                //Log.d(col + "row", row + "col");
                bombs[row][col] = 9;
            }

        }
    }
    public void getValues(){
        for(int i= 0; i<5; i++){
            for (int j = 0; j<5; j++){
                //Log.d("Check", i+", "+j);
                if (bombs[i][j] == 9) {
                    if (i < 4) {
                        //below
                        model[i + 1][j]++;
                        if (j < 4) {
                            //to the bottom right
                            model[i + 1][j + 1]++;
                            //right
                            //model[i][j + 1]++;
                        }
                        if (j > 0) {
                            //above left
                            model[i + 1][j - 1]++;
                            //left
                            //model[i][j - 1]++;

                        }
                    }

                    if (j >0) {
                        model[i][j - 1]++;
                    }

                    if (j < 4) {
                        model[i][j + 1]++;
                    }
                    if (i > 0) {
                        //above
                        model[i - 1][j]++;

                        if (j < 4) {

                            //above right
                            model[i - 1][j + 1]++;
                        }
                        if (j > 0) {

                            //above left
                            model[i - 1][j - 1]++;
                        }
                    }
                }
            }
        }
        for(int i= 0; i<5; i++) {
            for (int j = 0; j < 5; j++) {
                if(model[i][j]>=9 || bombs[i][j]>=9){
                    model[i][j]=9;
                }
            }
        }

    }


    public void resetModel(){
        for (int i = 0; i<5; i++){
            for (int j = 0; j<5; j++){
                model[i][j] = 0;
                bombs[i][j] = 0;
            }
        }
        instance = new MinesweeperModel();

    }
    public int getFieldContent(int x, int y) {
        return gameBoard[x][y];
    }
    public void setFieldContent(int x, int y){
        gameBoard[x][y] = model[x][y];
    }
    public void setBombContent(int x, int y){
        gameBoard[x][y] = -2;
    }

}
