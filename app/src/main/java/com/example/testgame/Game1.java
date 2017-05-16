package com.example.testgame;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;

/**
 * Created by HIT 王国晟 on 2017/4/17.
 */

public class Game1 extends View{
    private int box[][],height[];
    private int oneBoxSize = 50;
    private int times = 0;
    private char winner;
    private state currentState = state.START;
    private Paint paint;
    private RectF rectF;
    public Game1(Context context,AttributeSet attr){
        super(context,attr);
        paint = new Paint();
        rectF = new RectF();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        oneBoxSize = dm.widthPixels/7;
        box = new int[8][7];
        height = new int[8];
    }
    private enum state{
        START,
        OVER
    }
    public boolean pawnDown(int row,int gamer){
        if(height[row] >= 6 || currentState == state.OVER){
            return false;
        }
        times++;
        height[row]++;
        box[row][height[row]] = gamer;
        if(testWin(row)){
            currentState = state.OVER;
            winner = gamer == 1 ? '红':'蓝';
        }
        if(testFull()){
            currentState = state.OVER;
            winner = '0';
        }
        invalidate();
        return true;
    }

    public int[][] getBox(){
        return box;
    }

    private boolean testFull(){
        return times >= 42?true:false;
    }

    private boolean testWin(int row){
        int line = height[row];
        int pawn = box[row][line];
        int testA = 0,testB = 0;
        //四次判断 左右 上下 左下到右上 左上到右下

        //第一次
        for(int i = row;i < 8 && box[i][line] == pawn;i++){testA++;}
        for(int i = row;i > 0 && box[i][line] == pawn;i--){testB++;}
        if(testA + testB >= 5){return true;}

        //第二次
        testA = testB = 0;
        for(int j = line;j < 7 && box[row][j] == pawn;j++){testA++;}
        for (int j = line;j > 0 && box[row][j] == pawn;j--){testB++;}
        if(testA + testB >= 5){return true;}

        //第三次
        testA = testB = 0;
        for(int i = row,j = line;i < 8 && j < 7 && box[i][j] == pawn;i++,j++){testA++;}
        for(int i = row,j = line;i > 0 && j > 0 && box[i][j] == pawn;i--,j--){testB++;}
        if(testA + testB >= 5){return true;}

        //第四次
        testA = testB = 0;
        for(int i = row,j = line;i < 8 && j > 0 && box[i][j] == pawn;i++,j--){testA++;}
        for(int i = row,j = line;i > 0 && j < 7 && box[i][j] == pawn;i--,j++){testB++;}
        return testA + testB >= 5;
    }
    public void restart(){
        box = new int[8][7];
        height = new int[8];
        currentState = state.START;
        times = 0;
        invalidate();
    }

    @Override
    public void onDraw(Canvas canvas){
        super.onDraw(canvas);
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 7; j++) {
                if (box[i][j] == 1) {
                    paint.setColor(Color.RED);
                    rectF.set((i-1) * oneBoxSize, (6 - j) * oneBoxSize, i * oneBoxSize, (7 - j) * oneBoxSize);
                    canvas.drawOval(rectF, paint);
                } else if (box[i][j] == 2) {
                    paint.setColor(Color.BLUE);
                    rectF.set((i-1) * oneBoxSize, (6 - j) * oneBoxSize, i * oneBoxSize, (7 - j) * oneBoxSize);
                    canvas.drawOval(rectF, paint);
                }
            }
        }
        if(currentState == state.OVER){
            paint.setTextSize(50);
            paint.setColor(Color.BLACK);
            if(winner == '0'){canvas.drawText("游戏结束 平局",50,100,paint);}
            else{canvas.drawText("游戏结束 "+winner+"方获胜",50,100,paint);}
        }
    }
}
