package com.example.testgame;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by 王国晟 on 2017/4/17.
 */
public class MainActivity extends Activity{
    Game1 game;
    Button restart;
    Button button[] = new Button[7];
    RadioGroup radioGroup;
    TextView t;
    int bid[] = {R.id.b1,R.id.b2,R.id.b3,R.id.b4,R.id.b5,R.id.b6,R.id.b7};
    int gamer,ai,state,first,infinite = 1000000;//玩家标记、ai标记、当前走棋者、本局先手者
    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        gamer = 1;
        ai = 2;
        first = gamer;
        state = gamer;//暂时设定人先下棋
        t = (TextView)findViewById(R.id.score);
        radioGroup = (RadioGroup)findViewById(R.id.rg);
        RadioButton a = (RadioButton)findViewById(R.id.gamer);
        a.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                game.restart();
                if(checkedId == R.id.ai){
                    ai = 1;
                    gamer = 2;
                    first = ai;
                    state = ai;
                    ternOfAi();
                }else{
                    gamer = 1;
                    ai = 2;
                    first = gamer;
                    state = gamer;
                }
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

    /*
    * 评估函数 对一个局势给出评分
    *
    * @param box 保存当前局势的数组
    *
    * 17/5/16
    *
    *
    */
    public static int evaluate(int box[][]){
        int ret = 0;
        ArrayList<Integer> temp = new ArrayList<>();
        for(int i = 1;i < 7;i++){
            temp.add(3);
            for(int j = 1;j < 8;j++){
                temp.add(box[j][i]);
            }
            temp.add(4);
            ret += stingEvaluate(temp);
            temp.clear();
        }
        for(int j = 1;j < 8;j++){
            temp.add(3);
            for(int i = 1;i < 7;i++){
                temp.add(box[j][i]);
            }
            temp.add(4);
            ret += stingEvaluate(temp);
            temp.clear();
        }
        for(int i = 2;i < 14;i++){
            temp.add(3);
            for(int j = 1;j < 8;j++){
                if(i-j < 7 && i-j > 0)
                    temp.add(box[j][i-j]);
            }
            temp.add(4);
            ret += stingEvaluate(temp);
            temp.clear();
        }
        for(int i = -6;i < 6;i++){
            temp.add(3);
            for(int j = 1;j < 8;j++){
                if(i+j < 7 && i+j > 0)
                    temp.add(box[j][i+j]);
            }
            temp.add(4);
            ret += stingEvaluate(temp);
            temp.clear();
        }
        return ret;
    }
    //先手一方的分数永远是正数
    public static int stingEvaluate(ArrayList<Integer> input){
        int score = 0,score2 = 0,i1,i2,m,n,s,k;
        Integer temp[] = new Integer[input.size()];
        temp = input.toArray(temp);
        final int FOUR = 20000;
        final int LIVETHREE = 1000;
        final int LIVETWO = 100;
        final int LIVEONE = 10;
        final int DIEONE = 1;
        final int DIETWO = 	10;
        final int DIETHREE = 100;

        i2 = 0;
        for(i1 = 0;i1 < temp.length;i1++){
            if(temp[i1] == 2 || temp[i1] == 4){
                if(i1 - i2 >= 5){
                    m = i1-1;
                    n = i2+1;
                    s = 0;
                    while(temp[n] == 1){s++;n++;}
                    switch(s){
                        case 0:score+=0;break;
                        case 1:score+=DIEONE;break;
                        case 2:score+=DIETWO;break;
                        case 3:score+=DIETHREE;break;
                        default:score+=FOUR;break;
                    }
                    s=0;
                    while(temp[m] == 1){s++;m--;}
                    switch(s){
                        case 0:score+=0;break;
                        case 1:score+=DIEONE;break;
                        case 2:score+=DIETWO;break;
                        case 3:score+=DIETHREE;break;
                        default:score+=FOUR;break;
                    }
                    s = 0;
                    for(k = n;k <= m;k++){
                        if(temp[k] == 0){
                            switch(s){
                                case 0:score+=0;break;
                                case 1:score+=LIVEONE;break;
                                case 2:score+=LIVETWO;break;
                                case 3:score+=LIVETHREE;break;
                                default:score+=FOUR;break;
                            }
                            s = 0;
                        }else{
                            s++;
                        }
                    }
                }
                i2 = i1;
            }
        }

        i2 = 0;
        for(i1 = 0;i1 < temp.length;i1++){
            if(temp[i1] == 1 || temp[i1] == 4){
                if(i1 - i2 >= 5){
                    m = i1-1;
                    n = i2+1;
                    s = 0;
                    while(temp[n] == 2){s++;n++;}
                    switch(s){
                        case 0:score2+=0;break;
                        case 1:score2+=DIEONE;break;
                        case 2:score2+=DIETWO;break;
                        case 3:score2+=DIETHREE;break;
                        default:score2+=FOUR;break;
                    }
                    s = 0;
                    while(temp[m] == 2){s++;m--;}
                    switch(s){
                        case 0:score2+=0;break;
                        case 1:score2+=DIEONE;break;
                        case 2:score2+=DIETWO;break;
                        case 3:score2+=DIETHREE;break;
                        default:score2+=FOUR;break;
                    }
                    s = 0;
                    for(k = n;k <= m;k++){
                        if(temp[k] == 0){
                            switch(s){
                                case 0:score2+=0;break;
                                case 1:score2+=LIVEONE;break;
                                case 2:score2+=LIVETWO;break;
                                case 3:score2+=LIVETHREE;break;
                                default:score2+=FOUR;break;
                            }
                            s = 0;
                        }else{
                            s++;
                        }
                    }
                }
                i2 = i1;
            }
        }
        return score - score2;
    }

    private int maxmin(int deep,int box[][],int tern){
        int max = -infinite,min = infinite,eva;
        if(deep == 0){return (first == ai?1:-1) * evaluate(box);}
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
        int box[][] = game.getBox(),Cr = 0,eva,max = -infinite;
        for(int i = 1;i < 8;i++) {
            int j = 6;
            if(box[i][j] != 0){
                continue;
            }
            while(j != 1 && box[i][j-1] == 0){
                j--;
            }
            box[i][j] = ai;
            if(evaluate(box) > 10000){//一步杀棋四联 直接走此步
                game.pawnDown(i,ai);
                t.setText( "红色score："+evaluate(game.getBox())  );
                state = gamer;
                return;
            }
            eva = maxmin(3, box,gamer);
            if(max < eva){
                max = eva;
                Cr = i;
            }
            box[i][j] = 0;
        }
        game.pawnDown(Cr,ai);
        t.setText( "红色score："+evaluate(game.getBox())  );
        state = gamer;
    }
}
