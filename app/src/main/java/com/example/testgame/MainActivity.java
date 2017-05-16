package com.example.testgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

/**
 * Created by 王国晟 on 2017/4/17.
 */
public class MainActivity extends Activity{
    Game1 game;
    Button restart;
    Button button[] = new Button[7];
    RadioGroup radioGroup;
    int bid[] = {R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7};
    int gamer,ai,state,first;//玩家标记、ai标记、当前走棋者、本局先手者
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gamer = 1;
        ai = 2;
        first = gamer;
        state = gamer;//暂时设定人先下棋
        radioGroup = (RadioGroup)findViewById(R.id.rg);
        RadioButton a = (RadioButton)findViewById(R.id.gamer);
        a.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.ai){
                    ai = 1;
                    gamer = 2;
                    first = ai;
                    state = ai;
                }else{
                    gamer = 1;
                    ai = 2;
                    first = gamer;
                    state = gamer;
                }
                game.restart();
            }
        });
        game = (Game1)findViewById(R.id.game);
        for(int i = 0;i < 7;i++){
            button[i] = (Button)findViewById(bid[i]);
        }
        restart = (Button)findViewById(R.id.restart);
        button[0].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == gamer && game.pawnDown(1, gamer)) {
                    state = ai;
                    ternOfAi();
                }
            }
        });
        button[1].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == gamer && game.pawnDown(2, gamer)) {
                    state = ai;
                    ternOfAi();
                }
            }
        });
        button[2].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == gamer && game.pawnDown(3, gamer)){
                    state = ai;
                    ternOfAi();
                }
            }
        });
        button[3].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == gamer && game.pawnDown(4, gamer)){
                    state = ai;
                    ternOfAi();
                }
            }
        });
        button[4].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == gamer && game.pawnDown(5, gamer)){
                    state = ai;
                    ternOfAi();
                }
            }
        });
        button[5].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(state == gamer && game.pawnDown(6, gamer)){
                    state = ai;
                    ternOfAi();
                }
            }
        });
        button[6].setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (state == gamer && game.pawnDown(7, gamer)) {
                    state = ai;
                    ternOfAi();
                }
            }
        });
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                game.restart();
                state = first;
                if(state == ai)
                    ternOfAi();
            }
        });

    }
    private int evaluate(int box[][]){
        return 10;
    }
    private int maxmin(int deep,int box[][],int tern){
        int max = -10000,min = 10000,eva;
        if(deep == 0){return evaluate(box);}
        if(tern == gamer){
            for(int i = 1;i < 8;i++){
                int j = 6;
                if(box[i][j] != 0){continue;}
                while(j != 1 && box[i][j-1] == 0){
                    j--;
                }
                box[i][j] = gamer;
                eva = maxmin(deep-1, box,ai);
                min = min > eva?eva:min;
                box[i][j] = 0;
            }
            return min;
        }else if(tern == ai){
            for(int i = 1;i < 8;i++){
                int j = 6;
                if(box[i][j] != 0){continue;}
                while(j != 1 && box[i][j-1] == 0){
                    j--;
                }
                box[i][j] = ai;
                eva = maxmin(deep-1, box,gamer);
                max = max < eva?eva:max;
                box[i][j] = 0;
            }
        }
        return max;
    }
    private void ternOfAi(){
        int box[][] = game.getBox(),Cr = 0,eva,max = -10000;
        for(int i = 1;i < 8;i++) {
            int j = 6;
            if(box[i][j] != 0){
                continue;
            }
            if(j == 1 || box[i][j-1] != 0){
                box[i][j] = ai;
            }
            maxmin(3, box,gamer);
            eva = evaluate(box);
            if(max < eva){
                max = eva;
                Cr = i;
            }
            box[i][j] = 0;
        }
        game.pawnDown(Cr,ai);
        state = gamer;
    }
}
